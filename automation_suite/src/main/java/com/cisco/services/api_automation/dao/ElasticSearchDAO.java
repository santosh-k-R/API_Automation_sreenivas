package com.cisco.services.api_automation.dao;

import com.cisco.services.models.ElasticSearchResults;
import com.cisco.services.util.HasId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Refer https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.2/java-rest-high-supported-apis.html
 */
@Repository
public class ElasticSearchDAO {

	private final static Logger LOG = LoggerFactory.getLogger(ElasticSearchDAO.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private RestHighLevelClient elasticRestClient;

	public CompletableFuture<Boolean> isElasticSearchRunning() throws Exception {
		return CompletableFuture.completedFuture(elasticRestClient.ping(RequestOptions.DEFAULT));
	}

	/**
	 * doesIndexExist - self explanatory :)
	 * @param index - the index name
	 * @return - true or false
	 * @throws Exception - IOException if there is a network error
	 */
	public boolean doesIndexExist(String index) throws IOException {
		GetIndexRequest request = new GetIndexRequest(index);
		return elasticRestClient.indices().exists(request, RequestOptions.DEFAULT);
	}

	/**
	 * doesDocumentExist - self explanatory :)
	 * @param index - the index name
	 * @param documentId - the document id
	 * @return - true or false
	 * @throws Exception - IOException if there is a network error
	 */
	public boolean doesDocumentExist(String index, String documentId) throws IOException {
		GetRequest request = new GetRequest(index, documentId);
		return elasticRestClient.exists(request, RequestOptions.DEFAULT);
	}

	/**
	 * saveEntry - saves a document to ES index. upserts the document. creates the document if it doesnt exist, or updates existing document
	 * @param index - the name of index
	 * @param entity - the object to be saved
	 * @param type - the Class type of the object (E.g Employee.class)
	 * @return - the saved object
	 * @throws Exception
	 */
	public <T extends HasId> T saveEntry(String index, T entity, Class<T> type) throws IOException {
		long queryStartTime = System.currentTimeMillis();

		if (StringUtils.isBlank(entity.getDocId())) {
			// if entity id is blank, set a UUID as the id
			entity.setDocId(UUID.randomUUID().toString());
		}

		try {
			UpdateRequest request = new UpdateRequest(index, entity.getDocId())
					.doc(objectMapper.writeValueAsString(entity), XContentType.JSON)
					.fetchSource(true)
					.retryOnConflict(3) // because .. "third time's the charm"
					.docAsUpsert(true);

			return objectMapper.convertValue(elasticRestClient.update(request, RequestOptions.DEFAULT).getGetResult().getSource(), type);
		} finally {
			LOG.info("PERF_TIME_TAKEN ELASTICSEARCH SAVE | " + index + " | " + (System.currentTimeMillis() - queryStartTime));
		}
	}

	/**
	 * saveBulk - Saves a List of documents to ES using sync Bulk Request.
	 * @param index - the name of index
	 * @param entityList - the list of objects to be saved
	 * @param type - the Class type of the object (E.g Employee.class)
	 * @return - the list of saved objects
	 * @throws IOException
	 */
	public <T extends HasId> List<T> saveBulk(String index, List<T> entityList, Class<T> type) throws IOException {
		List<T> savedItems = new ArrayList<>();
		long startTime = System.currentTimeMillis();

		try {
			if (entityList != null && !entityList.isEmpty()) {
				BulkRequest request = new BulkRequest();

				entityList.forEach(entity -> {
					try {
						request.add(
								new UpdateRequest(index, entity.getDocId())
										.doc(objectMapper.writeValueAsString(entity), XContentType.JSON)
										.fetchSource(true)
										.docAsUpsert(true)
						);
					} catch (IOException e) {
						LOG.error("Could not Save " + entity, e);
					}
				});

				BulkResponse bulkResponse = elasticRestClient.bulk(request, RequestOptions.DEFAULT);
				for (BulkItemResponse bulkItemResponse : bulkResponse) {
					if (!bulkItemResponse.isFailed()) {
						savedItems.add(objectMapper.convertValue(((UpdateResponse) bulkItemResponse.getResponse()).getGetResult().getSource(), type));
					} else {
						BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
						LOG.error("Failed to save record: " + failure.getMessage(), failure.getCause());
					}
				}
			}
		} finally {
			LOG.info("PERF_TIME_TAKEN ELASTICSEARCH_BULK_SYNC_SAVE | " + index + " | " + (System.currentTimeMillis() - startTime));
		}

		return savedItems;
	}

	/**
	 * saveBulkAsync - Saves a List of documents to ES using async Bulk Request. Prints the Async responses to Sysout.
	 * @param index - the name of index
	 * @param entityList - the list of objects to be saved
	 */
	public <T extends HasId> void saveBulkAsync(String index, List<T> entityList) {
		this.saveBulkAsync(index, entityList, new ActionListener<BulkResponse>() {
			long startTime = System.currentTimeMillis();

			@Override
			public void onResponse(BulkResponse bulkResponse) {
				try {
					for (BulkItemResponse bulkItemResponse : bulkResponse) {
						if (!bulkItemResponse.isFailed()) {
							LOG.info("Saved entry to ES: " + ((UpdateResponse) bulkItemResponse.getResponse()).getGetResult().sourceAsString());
						} else {
							BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
							LOG.error("Failed to save entry to ES: " + failure.getMessage(), failure.getCause());
						}
					}
				} finally {
					LOG.info("PERF_TIME_TAKEN ELASTICSEARCH_BULK_ASYNC_SAVE | " + index + " | " + (System.currentTimeMillis() - startTime));
				}
			}

			@Override
			public void onFailure(Exception e) {
				LOG.error("Error during Bulk Save", e);
			}
		});
	}

	/**
	 * saveBulkAsync - Saves a List of documents to ES using async Bulk Request.
	 * @param index - the name of index
	 * @param entityList - the list of objects to be saved
	 * @param listener - the Async listener interface implementation
	 */
	public <T extends HasId> void saveBulkAsync(String index, List<T> entityList, ActionListener<BulkResponse> listener) {
		if (entityList != null && !entityList.isEmpty()) {
			BulkRequest request = new BulkRequest();

			entityList.forEach(entity -> {
				try {
					request.add(
							new UpdateRequest(index, entity.getDocId())
							.doc(objectMapper.writeValueAsString(entity), XContentType.JSON)
							.docAsUpsert(true)
					);
				} catch (IOException e) {
					LOG.error("Could not Save " + entity, e);
				}
			});

			elasticRestClient.bulkAsync(request, RequestOptions.DEFAULT, listener);
		}
	}

	/**
	 * getDocument - fetches a single document from index by its document id
	 * @param index - the index name
	 * @param documentId - the document id
	 * @param type - a model class of the document structure
	 * @return - an instance of the model class as the document
	 * @throws IOException - if there is a network error
	 */
	public <T extends HasId> T getDocument(String index, String documentId, Class<T> type) throws IOException {
		T document = null;
		if (this.doesIndexExist(index)) {
			GetResponse getResponse = elasticRestClient.get(new GetRequest(index, documentId), RequestOptions.DEFAULT);
			if (getResponse.isExists()) {
				document = objectMapper.convertValue(getResponse.getSourceAsMap(), type);
				document.setDocId(getResponse.getId());
			} else {
				LOG.warn("Document with Id: {} does not exist in {}", documentId, index);
			}
		} else {
			LOG.warn("Index {} does not exist. No documents to retrieve.", index);
		}

		return document;
	}

	/**
	 * query - queries the ES index using the SourceBuilder passed to it
	 * @param index - the name of the index to query
	 * @param sourceBuilder - the sourcebuilder object prepared before calling this method with all query params and options
	 * @param type - the Class type of the object (E.g Employee.class)
	 * @return - a list of objects matching the query, empty list if no records found
	 * @throws IOException
	 */
	public <T extends HasId> ElasticSearchResults<T> query(String index, SearchSourceBuilder sourceBuilder, Class<T> type) throws IOException {
		ElasticSearchResults<T> esQueryResponse = null;

		long queryStartTime = System.currentTimeMillis();
		try {
			if (this.doesIndexExist(index)) {
				SearchRequest searchRequest = new SearchRequest();
				searchRequest.indices(index);
				searchRequest.source(sourceBuilder);

				SearchResponse response = elasticRestClient.search(searchRequest, RequestOptions.DEFAULT);

				SearchHits hits = response.getHits();
				SearchHit[] searchHit = hits.getHits();
				final ElasticSearchResults<T> resultsCollector = new ElasticSearchResults<T>(searchHit.length, hits.getTotalHits().value);

				if (searchHit.length > 0) {
					LOG.debug("Hits:{}", searchHit.length);
					Arrays.stream(searchHit).forEach(hit -> {
						T curObj = objectMapper.convertValue(hit.getSourceAsMap(), type);
						// set the document id from ES if its not set on the object
						if (StringUtils.isBlank(curObj.getDocId())) {
							curObj.setDocId(hit.getId());
						}

						resultsCollector.addDocument(curObj);
					});

					esQueryResponse = resultsCollector;
				} else {
					LOG.debug("No Hits!!");
				}
			} else {
				LOG.warn("Index {} does not exist. Nothing to query. No Results.", index);
			}
		} finally {
			// return empty response object if no search results
			esQueryResponse = (esQueryResponse == null) ? new ElasticSearchResults<T>(0,0) : esQueryResponse;
			LOG.info("PERF_TIME_TAKEN ELASTICSEARCH QUERY | " + index + " | " + (System.currentTimeMillis() - queryStartTime));
		}

		return esQueryResponse;
	}

	/**
	 * delete - deletes a document from the index
	 * @param index - the name of the index
	 * @param documentId - the id of the document to delete
	 * @return - a Result object with the status of the operation (Result.DELETED or Result.NOT_FOUND)
	 * @throws Exception
	 */
	public DocWriteResponse.Result deleteOne(String index, String documentId) throws IOException {
		if (this.doesIndexExist(index)) {
			DeleteRequest request = new DeleteRequest(index, documentId);
			return elasticRestClient.delete(request, RequestOptions.DEFAULT).getResult();
		} else {
			LOG.warn("Index {} does not exist. Nothing to delete.", index);
			return DocWriteResponse.Result.NOOP;
		}
	}

	/**
	 * deleteByQuery - deletes multiple documents from an index given a query
	 * @param index - the name of the index to delete documents from
	 * @param deleteQuery - the QueryBuilder object with query conditions
	 * @return - number of records deleted
	 * @throws Exception
	 */
	public long deleteByQuery(String index, QueryBuilder deleteQuery) throws IOException {
		if (this.doesIndexExist(index)) {
			DeleteByQueryRequest request = new DeleteByQueryRequest(index).setQuery(deleteQuery);
			return elasticRestClient.deleteByQuery(request, RequestOptions.DEFAULT).getDeleted();
		} else {
			LOG.warn("Index {} does not exist. No documents to delete.", index);
			return 0L;
		}
	}
}

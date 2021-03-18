package com.cisco.services.api_automation.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

   @Autowired
   private PropertyConfiguration config;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {

        logger.info("ES Url connecting to " + config.getElasticsearchHost());

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(config.getElasticsearchUsername(), config.getElasticsearchPassword()));

        RestClientBuilder builder = RestClient.builder(new HttpHost(config.getElasticsearchHost(), config.getElasticsearchPort(), config.getElasticsearchScheme()))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(5000))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        RestHighLevelClient client = new RestHighLevelClient(builder);

        logger.info("Got a connection, returning client.. " + config.getElasticsearchHost());

        return client;
    }
}

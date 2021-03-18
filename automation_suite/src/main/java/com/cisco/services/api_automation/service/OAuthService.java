package com.cisco.services.api_automation.service;

import com.cisco.services.api_automation.config.PropertyConfiguration;
import com.cisco.services.models.OAuthBearerToken;
import com.cisco.services.util.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {
	private final Logger LOG = LoggerFactory.getLogger(OAuthService.class);

	@Autowired
	private PropertyConfiguration config;

	@Autowired
	private HttpService httpService;

	public OAuthBearerToken getCiscoOAuthToken(String client_id, String client_secret) throws RestClientException {
		return this.getCiscoOAuthToken(config.getCiscoOauthTokenUrl(), client_id, client_secret);
	}

	private OAuthBearerToken getCiscoOAuthToken(String cisco_token_url, String client_id, String client_secret) throws RestClientException {
		OAuthBearerToken token = (OAuthBearerToken) EhCacheManager.fetchFromCache(cisco_token_url, client_id);

		if (token == null) {
			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
			requestBody.add("grant_type", "client_credentials");
			requestBody.add("client_id", client_id);
			requestBody.add("client_secret", client_secret);
			token = this.getOAuthToken(cisco_token_url, requestBody, null);

			EhCacheManager.addToCache(cisco_token_url, client_id, token, token.getExpiresIn() * 1000);
		}

		return token;
	}

	public OAuthBearerToken getOAuthToken(String tokenUrl, MultiValueMap<String, String> requestBody, Map<String, Object> urlParams)
			throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		return httpService.makeHttpPostCallUrlEncoded(tokenUrl, requestBody, headers, OAuthBearerToken.class, (urlParams == null) ? new HashMap<>() : urlParams);
	}
}

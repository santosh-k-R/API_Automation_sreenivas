package com.cisco.services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthBearerToken {
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private long expiresIn;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("refresh_expires_in")
	private long refreshExpiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	public void setRefreshExpiresIn(long refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}

	public String getAuthorizationHeader() {
		if (StringUtils.isBlank(this.accessToken)) {
			throw new IllegalStateException("Missing access_token in OAuth Token response");
		}
		return (StringUtils.isBlank(this.tokenType) ? "Bearer" : this.tokenType) + " " + this.accessToken;
	}
}

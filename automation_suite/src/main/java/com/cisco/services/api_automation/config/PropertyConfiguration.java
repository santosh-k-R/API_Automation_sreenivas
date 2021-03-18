package com.cisco.services.api_automation.config;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:environment.properties")
})


public class PropertyConfiguration   {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port}")
    private int elasticsearchPort;

    @Value("${elasticsearch.scheme}")
    private String elasticsearchScheme;

    @Value("${elasticsearch.username}")
    private String elasticsearchUsername;

    @Value("${elasticsearch.password}")
    private String elasticsearchPassword;

    @Value("${cxp.basicauth.username}")
    private String cxpBasicAuthUserName;

    @Value("${cxp.basicauth.password}")
    private String cxpBasicAuthPassword;

    @Value("${oneid.offeringadmin.token.url}")
    private String oneIdOfferingAdminTokenUrl;

    @Value("${oneid.token.url}")
    private String oneIdTokenUrl;

    @Value("${oneid.tenancy.subject.issuer}")
    private String oneIdTenancySubjectIssuer;

    @Value("${oneid.serviceAccount.name}")
    private String oneIdServiceAccountName;

    @Value("${oneid.serviceAccount.password}")
    private String oneIdServiceAccountPassword;

    @Value("${oneid.client_id}")
    private String oneIdClientId;

    @Value("${oneid.client_secret}")
    private String oneIdClientSecret;

    @Value("${oneid.ccouser.url}")
    private String oneIdCcoUserUrl;

    @Value("${cisco.ldap.external.url}")
    private String ldapUrl;

    @Value("${cisco.ldap.userid}")
    private String ldapUserId;

    @Value("${cisco.ldap.password}")
    private String ldapPassword;

    @Value("${cisco.oauth.token.url}")
    private String ciscoOauthTokenUrl;

    @Value("${entitlement.user.party.affiliation.url}")
    private String entitlementUserPartyAffiliationUrl;

    @Value("${cxp.emailapi.context.url}")
    private String cxpEmailApiContextUrl;

    @Value("${cxp.emailapi.readiness.url}")
    private String cxpEmailReadinessUrl;

    public String getApplicationName() {
        return applicationName;
    }

    public String getElasticsearchHost() {
        return elasticsearchHost;
    }

    public int getElasticsearchPort() {
        return elasticsearchPort;
    }

    public String getElasticsearchScheme() {
        return elasticsearchScheme;
    }

    public String getElasticsearchUsername() {
        if (StringUtils.isBlank(elasticsearchUsername)) {
            throw new IllegalStateException("Elastic Search Username not present in ENV. Please set elasticsearch_username");
        }

        return elasticsearchUsername;
    }

    public String getElasticsearchPassword() {
        if (StringUtils.isBlank(elasticsearchUsername)) {
            throw new IllegalStateException("Elastic Search Password not present in ENV. Please set elasticsearch_password");
        }

        return elasticsearchPassword;
    }

    public String getCxpBasicAuthUserName() {
        if (StringUtils.isBlank(cxpBasicAuthUserName)) {
            throw new IllegalStateException("CXP Basic Auth Username not present in ENV. Please set cxp_basicauth_username");
        }

        return cxpBasicAuthUserName;
    }

    public String getCxpBasicAuthPassword() {
        if (StringUtils.isBlank(cxpBasicAuthPassword)) {
            throw new IllegalStateException("CXP Basic Auth Password not present in ENV. Please set cxp_basicauth_password");
        }

        return cxpBasicAuthPassword;
    }

    public String createCxpBasicAuthToken() {
        return new String(Base64.encodeBase64((this.getCxpBasicAuthUserName() + ":" + this.getCxpBasicAuthPassword()).getBytes()));
    }

    public String getOneIdOfferingAdminTokenUrl() {
        return oneIdOfferingAdminTokenUrl;
    }

    public String getOneIdTokenUrl() {
        return oneIdTokenUrl;
    }

    public String getOneIdTenancySubjectIssuer() {
        return oneIdTenancySubjectIssuer;
    }

    public String getOneIdServiceAccountName() {
        if (StringUtils.isBlank(oneIdServiceAccountName)) {
            throw new IllegalStateException("OneID Service Account Name not present in ENV. Please set oneid_serviceaccount_name");
        }

        return oneIdServiceAccountName;
    }

    public String getOneIdServiceAccountPassword() {
        if (StringUtils.isBlank(oneIdServiceAccountPassword)) {
            throw new IllegalStateException("OneID Service Account Password not present in ENV. Please set oneid_serviceaccount_password");
        }

        return oneIdServiceAccountPassword;
    }

    public String getOneIdClientId() {
        if (StringUtils.isBlank(oneIdClientId)) {
            throw new IllegalStateException("OneID Client ID not present in ENV. Please set oneid_client_id");
        }

        return oneIdClientId;
    }

    public String getOneIdClientSecret() {
        if (StringUtils.isBlank(oneIdClientSecret)) {
            throw new IllegalStateException("OneID Client Secret not present in ENV. Please set oneid_client_secret");
        }

        return oneIdClientSecret;
    }

    public String getOneIdCcoUserUrl() {
        return oneIdCcoUserUrl;
    }

    public String getLdapUrl() {
        return ldapUrl;
    }

    public String getLdapUserId() {
        return ldapUserId;
    }

    public String getLdapPassword() {
        return ldapPassword;
    }

    public String getCiscoOauthTokenUrl() {
        return ciscoOauthTokenUrl;
    }

    public String getEntitlementUserPartyAffiliationUrl() {
        return entitlementUserPartyAffiliationUrl;
    }

    public String getCxpEmailApiContextUrl() {
        return cxpEmailApiContextUrl;
    }

    public String getCxpEmailReadinessUrl() {
        return cxpEmailReadinessUrl;
    }


}

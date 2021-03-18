package com.cisco.services.api_automation.service;

import com.cisco.services.api_automation.config.PropertyConfiguration;
import com.cisco.services.models.CiscoUserProfileSchema;
import com.cisco.services.models.OAuthBearerToken;
import com.cisco.services.models.OneIDUsersResponse;
import com.cisco.services.util.EhCacheManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class CiscoProfileService {
    private static final Logger LOG = LoggerFactory.getLogger(CiscoProfileService.class);

    @Autowired
    private PropertyConfiguration config;

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private HttpService httpService;

    // hide constructor
    private CiscoProfileService() {
    }

    /**
     * getUserProfile - fetch user's cco profile from various sources
     * @param userid - the cco id of the user
     * @return
     */
    public CiscoUserProfileSchema getUserProfile(String userid) {
        CiscoUserProfileSchema ciscoProfile = null;

        String cacheName = "cache-user-cco-profile";
        Long cacheExpiryMs = 12 * 60 * 60 * 1000L; // 12 hours expiry
        String cacheKey = userid;

        if (!EhCacheManager.isElementInCacheExpired(cacheName, cacheKey, cacheExpiryMs)) {
            ciscoProfile = (CiscoUserProfileSchema) EhCacheManager.fetchFromCache(cacheName, cacheKey);
            LOG.info("Retrieved User Profile from cache.");
        } else {
            LOG.info("Fetching User Profile.");
            try {
                ciscoProfile = (ciscoProfile != null && ciscoProfile.isValid()) ? ciscoProfile :
                        this.convertOneIdToCiscoProfile(userid, this.getOneIdUserProfile(userid));
            } catch (Exception oneIdE) {
                LOG.error("Error fetching CCO user profile from OneID Users API.", oneIdE);
            }

            try {
                ciscoProfile = (ciscoProfile != null && ciscoProfile.isValid()) ? ciscoProfile :
                        this.getLDAPUserProfile(userid);
            } catch (Exception ldapE) {
                LOG.error("Error fetching CCO user profile from Cisco LDAP.", ldapE);
            }

            if (ciscoProfile != null && ciscoProfile.isValid()) {
                EhCacheManager.addToCache(cacheName, cacheKey, ciscoProfile, true, null);
            } else {
                LOG.info("Could not fetch CCO User Profile from any sources.");
                ciscoProfile = (CiscoUserProfileSchema) EhCacheManager.fetchFromCache(cacheName, cacheKey);

                if (ciscoProfile != null) {
                    LOG.info("Last known CCO User Profile from cache will be used.");
                }
            }
        }

        return ciscoProfile;
    }

    /**
     * getOneIdUserProfile - gets the user's profile from OneID system
     * @param userid - the ccoid of the user
     * @owner apix-support(mailer list) <apix-support@cisco.com>, Dilip Mandadi (dmandadi) <dmandadi@cisco.com>
     * @return
     */
    public OneIDUsersResponse getOneIdUserProfile(String userid) {
        OneIDUsersResponse oneIDUsersResponse = this.getOneIdUserProfile(userid, "global");
        oneIDUsersResponse = (oneIDUsersResponse == null || oneIDUsersResponse.getTotalResults() < 1) ? this.getOneIdUserProfile(userid, "cisco") : oneIDUsersResponse;

        LOG.info("Retrieved User Profile from OneID for {}: {}", userid, oneIDUsersResponse);
        return oneIDUsersResponse;
    }

    public OneIDUsersResponse getOneIdUserProfile(String userid, String tenant) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/scim+json");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        OAuthBearerToken OAuthBearerToken = this.generateOneIdToken(tenant);
        headers.add("Authorization", OAuthBearerToken.getAuthorizationHeader());

        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("ccoId", userid);
        reqParams.put("tenant", tenant);

        return httpService.makeHttpGetCall(config.getOneIdCcoUserUrl(), reqParams, headers, OneIDUsersResponse.class);
    }

    public OAuthBearerToken generateOneIdToken(String tenant) {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.set("tenant", tenant);
        bodyParams.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
        bodyParams.add("client_id", config.getOneIdClientId());
        bodyParams.add("client_secret", config.getOneIdClientSecret());
        bodyParams.add("subject_token_type", "urn:ietf:params:oauth:token-type:access_token");
        bodyParams.add("requested_token_type", "urn:ietf:params:oauth:token-type:access_token");
        bodyParams.add("subject_token", this.generateOneIdOfferingAdminToken().getAccessToken());
        bodyParams.add("audience", config.getOneIdClientId());
        bodyParams.add("subject_issuer", config.getOneIdTenancySubjectIssuer());
        bodyParams.add("scope", "/users.search /users.read");

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("tenant", tenant);

        return oAuthService.getOAuthToken(config.getOneIdTokenUrl(), bodyParams, urlParams);
    }

    private OAuthBearerToken generateOneIdOfferingAdminToken() {
        String cacheName = "cache-oneid";
        String cacheKey = "offeringadmin-token";

        OAuthBearerToken oneIdOfferingAdminToken = (OAuthBearerToken) EhCacheManager.fetchFromCache(cacheName, cacheKey);
        MultiValueMap<String, String> urlEncodedParams = new LinkedMultiValueMap<>();

        // TODO use renew token logic once refresh token expiry is extended to a week (currently 30 mins) (contact: Dilip Mandadi (dmandadi) <dmandadi@cisco.com>)
        if (oneIdOfferingAdminToken == null || EhCacheManager.isElementInCacheExpired(cacheName, cacheKey, oneIdOfferingAdminToken.getExpiresIn() * 1000)) {
            // add the url encoded params required to generate access_token using credentials
            urlEncodedParams.add("grant_type", "password");
            urlEncodedParams.add("client_id", config.getOneIdClientId());
            urlEncodedParams.add("client_secret", config.getOneIdClientSecret());
            urlEncodedParams.add("username", config.getOneIdServiceAccountName());
            urlEncodedParams.add("password", config.getOneIdServiceAccountPassword());
            urlEncodedParams.add("scope", "/users.search /users.read");
        }

        // do not remove - need to renew token in future
        //		else if (PbcCacheManager.isElementInCacheExpired(cacheName, cacheKey, oneIdOfferingAdminToken.getExpires_in() * 1000)) {
        //			// add the url encoded params required to generate access_token using refresh_token from previous bearer token object
        //			urlEncodedParams.add("grant_type", "refresh_token");
        //			urlEncodedParams.add("refresh_token", oneIdOfferingAdminToken.getRefresh_token());
        //		}

        // if params are not empty, generate new token
        if (!urlEncodedParams.isEmpty()) {
            oneIdOfferingAdminToken = oAuthService.getOAuthToken(config.getOneIdOfferingAdminTokenUrl(), urlEncodedParams, null);
            EhCacheManager.addToCache(cacheName, cacheKey, oneIdOfferingAdminToken, true, oneIdOfferingAdminToken.getExpiresIn() * 1000);
        }

        return oneIdOfferingAdminToken;
    }

    public CiscoUserProfileSchema convertOneIdToCiscoProfile(String userid, OneIDUsersResponse oneIdResponse) {
        CiscoUserProfileSchema profile = new CiscoUserProfileSchema();

        if (oneIdResponse != null && oneIdResponse.getTotalResults() > 0) {
            profile.setCcoId(userid);
            profile.setAccessLevel(oneIdResponse.getAccessLevel());
            profile.setCiscoContact(oneIdResponse.getCiscoContact());
            profile.setCompanyName(oneIdResponse.getCompanyName());
            profile.setJobTitle(oneIdResponse.getJobTitle());
            profile.setCountry(oneIdResponse.getCountry());
            profile.setUserFullName(oneIdResponse.getUserFullName());
            profile.setUserPhoneNumber(oneIdResponse.getUserPhoneNumber());
            profile.setUserEmail(oneIdResponse.getUserEmail());
        }

        return profile;
    }

    /**
     * getLDAPUserProfile - fetch user cco profile from cisco ldap (dsx)
     * @param userid - the cco id of the user
     * @return
     */
    public CiscoUserProfileSchema getLDAPUserProfile(String userid) {
        CiscoUserProfileSchema myUser = new CiscoUserProfileSchema();
        Map<String, String> myUserData = this.getLDAPInfo(userid);

        // Setting the User attributes to the UserBean
        if (myUserData != null && !myUserData.isEmpty()) {
            myUser.setUserPhoneNumber(myUserData.get("telephoneNumber"));
            myUser.setUserEmail(myUserData.get("mail"));
            myUser.setUserFullName(myUserData.get("cn"));
            myUser.setJobTitle(myUserData.get("title"));
            myUser.setCountry(myUserData.get("co"));
            myUser.setCompanyName(myUserData.get("company"));
            myUser.setCcoId(myUserData.get("uid"));

            //TODO Figure out how Cisco contact can be retrieved
            myUser.setCiscoContact("NA");
        }

        LOG.info("Retrieved User Profile from LDAP for {}: {}", userid, myUser);
        return myUser;
    }

    // Fetching the User data from the LDAP server
    public Map<String, String> getLDAPInfo(String userId) {
        HashMap<String, String> record = null;

        String ldapUserId = config.getLdapUserId();
        String ldapPassword = config.getLdapPassword();

        if (StringUtils.isNotBlank(ldapUserId) && StringUtils.isNotBlank(ldapPassword)) {
            // Retrieve a JNDI Context
            DirContext ctx = null;

            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, config.getLdapUrl());
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "uid=" + ldapUserId + ",OU=Generics,O=cco.cisco.com");
            env.put(Context.SECURITY_CREDENTIALS, ldapPassword);

            try {
                ctx = new InitialDirContext(env);
            } catch (NamingException e) {
                LOG.error("CiscoProfileService.getLDAPInfo - Problem connecting to LDAP server: " + e, e);
            }

            // Query
            NamingEnumeration<SearchResult> entries = null;

            if (ctx != null) {
                String searchFilter = "(uid=" + userId + ")";
                SearchControls ctls = new SearchControls();
                ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                try {
                    entries = ctx.search("ou=ccoentities,o=cco.cisco.com", searchFilter, ctls);
                } catch (NamingException e) {
                    LOG.error("CiscoProfileService.getLDAPInfo - Problem in LDAP search: " + e, e);
                }

                try {
                    ctx.close();
                } catch (NamingException e) {
                    LOG.error("CiscoProfileService.getLDAPInfo - Disconnect failed: " + e, e);
                }
            }

            try {
                if (entries != null && entries.hasMore()) {
                    record = new HashMap<String, String>();
                    do {
                        SearchResult entry = (SearchResult) entries.next();
                        Attributes srchAttrs = entry.getAttributes();
                        NamingEnumeration<String> ne = srchAttrs.getIDs();

                        while (ne.hasMore()) {
                            String neStr = (String) ne.next();
                            Attribute at = srchAttrs.get(neStr);
                            record.put(neStr, at.get().toString());
                        }
                    } while (entries.hasMore());
                }
            } catch (NamingException nex) {
                LOG.error("CiscoProfileService.getLDAPInfo - Error searching LDAP - " + nex, nex);
            }
        }

        return record;
    }

    public static String resolveUserType(String accessLevel) {
        String response = null;

        switch (accessLevel) {
        case "0":
            response = "Guest";
            break;
        case "1":
            response = "Registered User";
            break;
        case "2":
            response = "Customer";
            break;
        case "3":
            response = "Partner";
            break;
        case "4":
            response = "Employee";
            break;
        default:
            response = "Guest";
            break;
        }

        return response;
    }
}

package com.cisco.services.api_automation.utils.auth;


import io.restassured.http.Header;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;

public class TokenGenerator {


    //Pick the values from Human User/SPA/Web Applications of RBAC Confluence URLs

    private static String rbacAuthURL = System.getenv("rbac_auth_URL");
    private static String rbacAuthTokenId = System.getenv("rbac_auth_token_id");
    private static String clientID = System.getenv("rbac_client_id");
    private static String bearerToken = null;
    private static long tokenCreatedTime;
    private static Map<String,String> tokens = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException { //Self sufficient run
        init(System.getenv("niagara_username"), System.getenv("niagara_password"));
    }

    public static Header getToken(String userId, String password) {
        try {
            if (isTokenExpired()) {
                tokenCreatedTime = System.currentTimeMillis();
                System.out.println("RBAC JWT Token Created at ::: " + tokenCreatedTime);
                bearerToken = init(userId, password);
            }
            return new Header("Authorization", "Bearer " + bearerToken);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception observed. Please check for more details");
            return null;
        }
    }

    public static Header getToken(User user) {
        String tokenCreatedTime = user.getUser() + "_tokenGenerationTime";
        String token = user.getUser() + "+_token";
        try {
            if (isTokenExpired(tokens.get(tokenCreatedTime))) {
                if (user.getUser().equals("MACHINE")) {
                    tokens.put(token, initMachineToken());
                } else if (user.getUser().equals("INVALID")) {
                    tokens.put(token, RandomStringUtils.randomAlphanumeric(40));
                } else {
                        tokens.put(token, init(user.getUser(), user.getPassword()));
                }
                tokens.put(user.getUser() + "_tokenGenerationTime", String.valueOf(System.currentTimeMillis()));
                System.out.println("RBAC JWT Token Created at ::: " + tokens.get(tokenCreatedTime));
            }
            return new Header("Authorization", "Bearer " + tokens.get(token));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception observed. Please check for more details");
            return null;
        }
    }

    private static HttpsURLConnection setupHttpUrlConnection(URL url) {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }

        // Uncomment this line to send requests to a proxy
//	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.82.227.23", 8080));

        HttpsURLConnection conn = null;
        try {

            // Use the call with proxy in the arguments if using proxy
//		conn = (HttpsURLConnection) url.openConnection(proxy);
            conn = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;

    }

    private synchronized static String init(String userId, String password) throws IOException {
        System.out.println("** Inside Token Generator... Generating token for the user **" + userId);
        // TODO Auto-generated method stub
        CookieManager cookieManager = new CookieManager();
        // use custom cookie policy to deal with the "sid" cookie being double-set by okta
        myCookiePolicy mcp = new myCookiePolicy();
        cookieManager.setCookiePolicy(mcp);
        CookieHandler.setDefault(cookieManager);

        // FIX: Hardcoded here for POC
        //String userId = "customerportal24035";
        //Generate token for RMA also
//		String userId = "customerportal83444";

        String applicationUrl = "cx-cp-qa.cisco.com";

        //SecureRandom sr = new SecureRandom();
        //byte[] code = new byte[32];
        //sr.nextBytes(code);
        // -- TODO convert the random bytes to a hex string for use as the codeVerifier
        // -- needed only if we wish to generate new random PKCE for each request

        //in real use the code should be different for each token request, but
        // for testing we can use the same one repeatedly
        String codestr = "5a56745ff9a47496e78a8815d242b7a391e21fd71dd";
        byte[] codechars = codestr.getBytes();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(codechars, 0, codechars.length);
        byte[] digest = md.digest();
        String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);

        // Seems weird, but looks like we need to get a discovery string by calling the API
        String urlString = "https://api-test.cisco.com/api/v3/endpoint/discover/" + userId + "?apikey=rkhbnpx25wvd42enu4terbzq";
        URL url = new URL(urlString);
        HttpsURLConnection myConn = setupHttpUrlConnection(url);
        myConn.setDoOutput(true);
        myConn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));
        String inputLine;
        String discoveryId = "";
        while ((inputLine = in.readLine()) != null) {
            int idOffset = inputLine.indexOf("\"t\":");
            int closeBraceOffset = inputLine.indexOf("\"}", idOffset);

            if (idOffset >= 0) {
                discoveryId = inputLine.substring(idOffset + 5, closeBraceOffset);
                break;
            }
        }
        in.close();

        // set the discovery cookie
        String cookieString = "Set-Cookie: discovery=" + discoveryId + "; path=/; HttpOnly; Secure";
        HttpCookie cookieList = HttpCookie.parse(cookieString).get(0);
        cookieList.setDomain(".cisco.com");
        cookieManager.getCookieStore().add(null, cookieList);

        // Start the token request sequence
        // note: we can use the same nonce every time for testing
        urlString = "https://" + rbacAuthURL + "/oauth2/" + rbacAuthTokenId + "/v1/authorize"
                + "?client_id=" + clientID + "&code_challenge=" + challenge +
                "&code_challenge_method=S256&nonce=LiRIueMwPvIojKh4IdJHbst6vCX3wz08zidpESivD58U0FzDnF9PTegEX4vDrLkj&redirect_uri=https%3A%2F%2F"
                + applicationUrl
                + "%2Fimplicit%2Fcallback&response_type=code&state=Jr42wjNzUWsM2n5YvZvDu0f9XxMoCrUUobTsnoU6c70rQrVDX8A5yHAVtLWzTFA7&scope=openid%20email%20profile";
        url = new URL(urlString);
        myConn = setupHttpUrlConnection(url);
        myConn.getResponseCode();

        in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));
        // Ping embeds a unique single-use code into the url path for each request.
        // Here we'll parse it out and save it in ephemeralId.
        String ephemeralId = "";
        while ((inputLine = in.readLine()) != null) {
            int idOffset = inputLine.indexOf("/resume/as/authorization.ping");// may need better search, but this works
            if (idOffset >= 0) {
                ephemeralId = inputLine.substring(idOffset - 5, idOffset);
                break;
            }

        }
        in.close();
        if (ephemeralId.equals("")) {
            System.out.println("ERROR - could not find Ping ephemeral id");
        }
        // OK now we have the per-request ID from ping
        urlString = "https://cloudsso-test.cisco.com//as/"
                + ephemeralId

                + "/resume/as/authorization.ping";
        url = new URL(urlString);
        // FIXME - probably can re-use the connection but for now just making a new one
        myConn = setupHttpUrlConnection(url);
        myConn.setRequestMethod("POST");
        myConn.setDoInput(true);
        myConn.setDoOutput(true);
        myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStream outputStream = new BufferedOutputStream(myConn.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        String bodyString = "pf.username=" + userId + "&pf.pass=&target=&pf.userType=federated&pf.idpId=identity.cisco.com&pf.isUserStale=&pf.idpUrl=&pf.TargetResource=&pf.cancel=clicked";
        writer.write(bodyString);
        writer.flush();
        writer.close();
        outputStream.close();

        myConn.getResponseCode();
        // OK now we do the next post
        // HACK - probably should parse the response but for POC will hardcode the request
        // -- it should work until/unless ping changes its form format
        url = new URL("https://cloudsso-test.cisco.com//sp/startSSO.ping");
        bodyString = "login_hint=" + userId + "&username=" + userId + "&pf.idpId=identity.cisco.com&pf.userType=federated&pf.submit=clicked&SpSessionAuthnAdapterId=iftoken&TargetResource=https%3A%2F%2Fcloudsso-test.cisco.com%2F%2Fas%2F"
                + ephemeralId
                + "%2Fresume%2Fas%2Fauthorization.ping%3Fpf.continue%3Dclicked%26pf.idpId%3Didentity.cisco.com%26pf.userType%3Dfederated";
        // FIXME - probably can re-use the connection but for now just making a new one
        myConn = setupHttpUrlConnection(url);
        myConn.setRequestMethod("POST");
        myConn.setDoInput(true);
        myConn.setDoOutput(true);
        myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        outputStream = new BufferedOutputStream(myConn.getOutputStream());
        writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        writer.write(bodyString);
        writer.flush();
        writer.close();
        outputStream.close();
        myConn.getResponseCode();


        // Response will contain the SAML request + relay State
        // Extract that and post it back
        in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));
        String SAMLrequest = "";
        String RelayState = "";
        while ((inputLine = in.readLine()) != null) {
            int SAMLOffset = inputLine.indexOf("SAMLRequest");
            int RelayOffset = inputLine.indexOf("RelayState");
            int ParamValueOffset;
            int closeBraceOffset;
            if (SAMLOffset >= 0) {
                ParamValueOffset = inputLine.indexOf("value=", SAMLOffset);
                closeBraceOffset = inputLine.indexOf("/>");
                // FIXME - bail out here if either of the 2 above strings are not found
                SAMLrequest = inputLine.substring(ParamValueOffset + 7, closeBraceOffset - 1);
            } else if (RelayOffset >= 0) {
                ParamValueOffset = inputLine.indexOf("value=", RelayOffset);
                closeBraceOffset = inputLine.indexOf("/>");
                // FIXME - bail out here if either of the 2 above strings are not found
                RelayState = inputLine.substring(ParamValueOffset + 7, closeBraceOffset - 1);
                // FIXME could break here
            }
        }
        in.close();
        //FIXME - make sure we got everything we needed before continuing

        // URLencode the SAML request and relay state
        SAMLrequest = URLEncoder.encode(SAMLrequest, StandardCharsets.UTF_8.toString());
        RelayState = URLEncoder.encode(RelayState, StandardCharsets.UTF_8.toString());
        // Post the SAML request
        /// FIXME, HARDCODED discovery, will need to do discovery properly to cover .gen and other types of accounts
        url = new URL("https://int-identity.cisco.com/api/v1/idp-discovery/discovery");
        bodyString = "SAMLRequest=" + SAMLrequest + "&RelayState=" + RelayState;
        // FIXME - probably can re-use the connection but for now just making a new one
        myConn = setupHttpUrlConnection(url);
        myConn.setRequestMethod("POST");
        myConn.setDoInput(true);
        myConn.setDoOutput(true);
        myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        outputStream = new BufferedOutputStream(myConn.getOutputStream());
        writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        writer.write(bodyString);
        writer.flush();
        writer.close();
        outputStream.close();
        myConn.getResponseCode();
        // now we need to get the loginForm url from the response.

        in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));

        while ((inputLine = in.readLine()) != null) {
            int LoginFormOffset = inputLine.indexOf("<form name=\"loginForm");
            int ParamValueOffset;
            int closeBraceOffset;
            if (LoginFormOffset >= 0) {
                ParamValueOffset = inputLine.indexOf("action=", LoginFormOffset);
                closeBraceOffset = inputLine.indexOf(" method=");
                // FIXME - bail out here if either of the 2 above strings are not found
                urlString = inputLine.substring(ParamValueOffset + 8, closeBraceOffset - 1);
                break;
            }
        }

        // Post the next one
        if (urlString.equals("checkUser")) {
            // need to do an extra round
            url = new URL("https://int-identity.cisco.com/api/v1/idp-discovery/checkUser");
            bodyString = "discoveryEmail=" + userId + "&navigationState=&SAMLRequest=" + SAMLrequest
                    + "&RelayState=" + RelayState + "&emailmutable=false";
            // FIXME - probably can re-use the connection but for now just making a new one
            myConn = setupHttpUrlConnection(url);
            myConn.setRequestMethod("POST");
            myConn.setDoInput(true);
            myConn.setDoOutput(true);
            myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            outputStream = new BufferedOutputStream(myConn.getOutputStream());
            writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(bodyString);
            writer.flush();
            writer.close();
            outputStream.close();
            myConn.getResponseCode();
            // now we need to get the loginForm url from the response.

            in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));

            while ((inputLine = in.readLine()) != null) {
                int LoginFormOffset = inputLine.indexOf("loginForm");
                int ParamValueOffset;
                int closeBraceOffset;
                if (LoginFormOffset >= 0) {
                    ParamValueOffset = inputLine.indexOf("action=", LoginFormOffset);
                    closeBraceOffset = inputLine.indexOf(" method=");
                    // FIXME - bail out here if either of the 2 above strings are not found
                    urlString = inputLine.substring(ParamValueOffset + 8, closeBraceOffset - 1);
                }
            }
        }
        // OK now we should have a good url.
        url = new URL(urlString);
        bodyString = "discoveryEmail=" + userId + "&navigationState=&SAMLRequest=" + SAMLrequest
                + "&RelayState=" + RelayState + "&emailmutable=false";
        // FIXME - probably can re-use the connection but for now just making a new one
        myConn = setupHttpUrlConnection(url);
        myConn.setRequestMethod("POST");
        myConn.setDoInput(true);
        myConn.setDoOutput(true);
        myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        outputStream = new BufferedOutputStream(myConn.getOutputStream());
        writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        writer.write(bodyString);
        writer.flush();
        writer.close();
        outputStream.close();
        myConn.getResponseCode();


        // Now we pull out the next POST from the response
        in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));

        while ((inputLine = in.readLine()) != null) {
            int LoginFormOffset = inputLine.indexOf("loginAction:");
            int ParamValueOffset;
            int closeBraceOffset;
            if (LoginFormOffset >= 0) {
                //ParamValueOffset = inputLine.indexOf("action=",LoginFormOffset);
                closeBraceOffset = inputLine.indexOf(".replace");
                // FIXME - bail out here if either of the 2 above strings are not found
                urlString = inputLine.substring(LoginFormOffset + 14, closeBraceOffset - 1);
                break;
            }
        }

        urlString = "https://int-identity.cisco.com" + urlString;

        // Jsoup un-escapes some special characters
        urlString = Jsoup.parse(urlString).text();
        url = new URL(urlString);
        bodyString = "username=" + userId + "&password=" + password + "&cliData=";
        myConn = setupHttpUrlConnection(url);
        myConn.setRequestMethod("POST");
        myConn.setDoInput(true);
        myConn.setDoOutput(true);
        myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        outputStream = new BufferedOutputStream(myConn.getOutputStream());
        writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        writer.write(bodyString);
        writer.flush();
        writer.close();
        outputStream.close();
        myConn.getResponseCode();

        in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));
        String SAMLresponse = "";
        RelayState = "";
        // Extract the SAML response
        while ((inputLine = in.readLine()) != null) {
            int SAMLResponseOffset = inputLine.indexOf("name=\"samlValue\" value=\"");
            int RelayStateOffset = inputLine.indexOf("name=\"RelayState\" value=\"");

            int ParamValueOffset;
            int closeBraceOffset;
            if (SAMLResponseOffset >= 0) {
                closeBraceOffset = inputLine.indexOf("\"/>", SAMLResponseOffset);
                // FIXME - bail out here if either of the 2 above strings are not found
                SAMLresponse = inputLine.substring(SAMLResponseOffset + 24, closeBraceOffset);
            }
            if (RelayStateOffset >= 0) {
                closeBraceOffset = inputLine.indexOf("\"/>", RelayStateOffset);
                // FIXME - bail out here if either of the 2 above strings are not found
                RelayState = inputLine.substring(RelayStateOffset + 25, closeBraceOffset);
            }
        }
        SAMLresponse = URLEncoder.encode(SAMLresponse, StandardCharsets.UTF_8.toString());
        bodyString = "SAMLResponse=" + SAMLresponse + "&RelayState=" + RelayState;
        url = new URL("https://cloudsso-test.cisco.com/sp/ACS.saml2");
        myConn = setupHttpUrlConnection(url);
        myConn.setRequestMethod("POST");
        myConn.setDoInput(true);
        myConn.setDoOutput(true);
//        myConn.setInstanceFollowRedirects(false);
        myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        outputStream = new BufferedOutputStream(myConn.getOutputStream());
        writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        writer.write(bodyString);
        writer.flush();
        writer.close();
        outputStream.close();
        myConn.getResponseCode();
        URL lastUrl = myConn.getURL();
        String query = lastUrl.getQuery();
        int codeEnd = query.indexOf('&');
        String respCode = query.substring(0, codeEnd);

        // delete the cookies.
        // In the browser, the okta code seems to send this command from
        // a 2nd user agent, with no cookies.  We don't need the cookies after this
        // so we can just allocate a new empty cookie manager
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(mcp);
        CookieHandler.setDefault(cookieManager);

        urlString = "https://" + rbacAuthURL + "/oauth2/" + rbacAuthTokenId + "/v1/token";
        url = new URL(urlString);
        myConn = setupHttpUrlConnection(url);
        myConn.setRequestMethod("POST");
        myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        myConn.setRequestProperty("Referer", "https://" + applicationUrl + "/implicit/callback");
        myConn.setRequestProperty("Accept", "application/json");
        myConn.setDoInput(true);
        myConn.setDoOutput(true);
        myConn.setInstanceFollowRedirects(false);
        bodyString = "client_id=" + clientID + "&redirect_uri=https%3A%2F%2F" + applicationUrl
                + "%2Fimplicit%2Fcallback&grant_type=authorization_code&" + respCode + "&code_verifier=" + codestr;
        outputStream = new BufferedOutputStream(myConn.getOutputStream());
        writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        writer.write(bodyString);
        writer.flush();
        writer.close();
        outputStream.close();
        myConn.getResponseCode();

        // OK now we should have a token in the response
        in = new BufferedReader(new InputStreamReader(myConn.getInputStream(), Charset.forName("UTF-8")));
        String accessToken = "";
        while ((inputLine = in.readLine()) != null) {
            int accessTokenOffset = inputLine.indexOf("\"access_token\":");

            int closeBraceOffset;
            if (accessTokenOffset >= 0) {
                closeBraceOffset = inputLine.indexOf("\",", accessTokenOffset);
                // FIXME - bail out here if either of the 2 above strings are not found
                accessToken = inputLine.substring(accessTokenOffset + 16, closeBraceOffset);
                break;
            }

        }
        return accessToken;
    }

    private static boolean isTokenExpired() {
        if (bearerToken == null) return true;
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - tokenCreatedTime > 1200000;
    }

    private static boolean isTokenExpired(String tokenGeneratedTime) {
        if (tokenGeneratedTime == null) return true;
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - Long.valueOf(tokenGeneratedTime) > 1200000;
    }

    private static class myCookiePolicy implements CookiePolicy {

        @Override
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            // TODO Auto-generated method stub
            if ((cookie.getName().equals("sid"))) {
                if (cookie.getDomain().equals(rbacAuthURL)
                        && (cookie.getValue().equals("\"\""))) {
                    return false;
                }
            }
            return true;
        }

    }

    private synchronized static String initMachineToken() {
        System.out.println("Generating Machine Token");
        String authURL = "https://" + rbacAuthURL + "/oauth2/" + rbacAuthTokenId + "/v1/token";
        String rbacMachineClientID = System.getenv("rbac_Machine_ClientId");
        String rbacGrantType = System.getenv("rbac_grant_type");
        String rbacScope = System.getenv("rbac_scope");
        String rbacClientSecret = System.getenv("rbac_client_secret");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.add("Accept", "application/json");
        headers.add("Cache-Control", "no-cache");

        return given()
                .param("grant_type", rbacGrantType).param("client_id", rbacMachineClientID)
                .param("client_secret", rbacClientSecret)
                .param("scope", rbacScope).headers(headers)
                .when()
                .post(authURL)
                .then()
                .statusCode(200).extract().path("access_token");
    }
}
package com.cisco.services.models;

import com.cisco.services.api_automation.exception.BadRequestException;
import com.cisco.services.api_automation.service.CiscoProfileService;
import com.cisco.services.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MasheryUser {
    private static final Logger LOG = LoggerFactory.getLogger(MasheryUser.class);
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //{"client_cco_id":"krsubbu","party_id":"52428","access_token_uid":"karbala2","access_token_accesslevel":"4"}
    @JsonProperty("party_id")
    private String partyId;

    @JsonProperty("access_token_uid")
    private String ccoId;

    @JsonProperty("access_token_accesslevel")
    private String accessLevel;

    public String getPartyId() {
        return this.partyId;
    }

    public String getCcoId() {
        return this.ccoId;
    }

    public String getAccessLevel() {
        return this.accessLevel;
    }

    public String getType() {
        return StringUtils.isNotBlank(this.accessLevel) ? CiscoProfileService.resolveUserType(this.accessLevel) : "Guest";
    }

    private MasheryUser() {}

    public static MasheryUser getInstance(String requestHeader) {
        MasheryUser masheryUser = null;

        if (StringUtils.isNotBlank(requestHeader)) {
            String decodeRequestHeader = new String(Base64.decodeBase64(requestHeader));
            try {
                masheryUser = OBJECT_MAPPER.readValue(decodeRequestHeader, MasheryUser.class);
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not decode Mashery header, User unknown");
            }

            if (masheryUser == null || StringUtils.isBlank(masheryUser.getCcoId())) {
                throw new BadRequestException("Missing CCO Id (access_token_uid) in " + Constants.MASHERY_HANDSHAKE_HEADER_NAME + " header");
            }
        } else {
            throw new IllegalArgumentException("Missing Mashery header, User unknown");
        }

        return masheryUser;
    }

    public String createXMasheryHandshakeHeader() {
        try {
            return new String(Base64.encodeBase64(new ObjectMapper().writeValueAsString(this).getBytes()));
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Invalid Mashery User value" , e);
        }
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return "MasheryUser {" + "partyId=" + partyId + ", ccoId=" + ccoId + ", accessLevel=" + accessLevel + '}';
        }
    }
}

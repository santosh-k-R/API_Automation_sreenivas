package com.cisco.services.api_automation.utils.auth;

public class User {

    private String userName;
    private String partyId;
    private String accessLevel;
    private String user;
    private String password;
    public User(String userName, String partyId, String accessLevel) {
        this.userName = userName;
        this.partyId = partyId;
        this.accessLevel = accessLevel;
    }

    public User(String userName, String partyId) {
        this.userName = userName;
        this.partyId = partyId;
    }

    public User(String userRole) {

        if ("MACHINE".equals(userRole.toUpperCase())) {
            this.user = "MACHINE";
        } else if ("INVALID".equals(userRole.toUpperCase())) {
            this.user = "INVALID";
            this.password = "INVALID";
        } else {
            this.userName = userRole;
            this.user = System.getenv("niagara_" + userRole + "_username");
            this.password = System.getenv("niagara_" + userRole + "_password");
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPartyId() {
        return partyId;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

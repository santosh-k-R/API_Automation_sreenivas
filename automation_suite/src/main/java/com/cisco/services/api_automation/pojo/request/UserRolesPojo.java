package com.cisco.services.api_automation.pojo.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRolesPojo {
    private String name;
    private ArrayList<UserRolesPojo.data> data;

  
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class data {
        public data() {
        }

        
        private String companyAccountId;
        private String saRoles;
        private String role;
        private String roleDisplayName;
        private String roleDescription;
		public String getCompanyAccountId() {
			return companyAccountId;
		}
		public void setCompanyAccountId(String companyAccountId) {
			this.companyAccountId = companyAccountId;
		}
		public String getSaRoles() {
			return saRoles;
		}
		public void setSaRoles(String saRoles) {
			this.saRoles = saRoles;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getRoleDisplayName() {
			return roleDisplayName;
		}
		public void setRoleDisplayName(String roleDisplayName) {
			this.roleDisplayName = roleDisplayName;
		}
		public String getRoleDescription() {
			return roleDescription;
		}
		public void setRoleDescription(String roleDescription) {
			this.roleDescription = roleDescription;
		}

    
    }
}

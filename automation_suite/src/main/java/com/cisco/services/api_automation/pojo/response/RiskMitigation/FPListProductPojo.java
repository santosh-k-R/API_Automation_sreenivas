package com.cisco.services.api_automation.pojo.response.RiskMitigation;

import com.google.gson.annotations.Expose;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FPListProductPojo {
	@SerializedName("customerId")
	@Expose
	private String customerId;

	@SerializedName("crashPredicted")
	@Expose
	private boolean crashPredicted;

	@SerializedName("productFamilies")
	@Expose
	private List<ProductFamilies> productFamilies;

	@SerializedName("devices")
	@Expose
	private List<DevicesPojo> devices;

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCrashPredicted(boolean crashPredicted) {
		this.crashPredicted = crashPredicted;
	}

	public boolean getCrashPredicted() {
		return this.crashPredicted;
	}

	public void setProductFamilies(List<ProductFamilies> productFamilies) {
		this.productFamilies = productFamilies;
	}

	public List<ProductFamilies> getProductFamilies() {
		return this.productFamilies;
	}

	public void setDevices(List<DevicesPojo> devices) {
		this.devices = devices;
	}

	public List<DevicesPojo> getDevices() {
		return this.devices;
	}

	public class ProductIds {
		@SerializedName("productId")
		@Expose
		private String productId;

		@SerializedName("count")
		@Expose
		private int count;

		@SerializedName("devices")
		@Expose
		private List<DevicesPojo> devices;

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getProductId() {
			return this.productId;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getCount() {
			return this.count;
		}

		public void setDevices(List<DevicesPojo> devices) {
			this.devices = devices;
		}

		public List<DevicesPojo> getDevices() {
			return this.devices;
		}
	}

	public class ProductFamilies {
		@SerializedName("productFamily")
		@Expose
		private String productFamily;

		@SerializedName("productIds")
		@Expose
		private List<ProductIds> productIds;

		public void setProductFamily(String productFamily) {
			this.productFamily = productFamily;
		}

		public String getProductFamily() {
			return this.productFamily;
		}

		public void setProductIds(List<ProductIds> productIds) {
			this.productIds = productIds;
		}

		public List<ProductIds> getProductIds() {
			return this.productIds;
		}
	}

}

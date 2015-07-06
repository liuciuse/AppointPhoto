package com.appointphoto.model;

import java.io.Serializable;

import org.json.JSONObject;

public class SimpleInfo implements Serializable {
	private String createdAt;
	private String id;
	private String imageBaseUrl;
	private String updatedAt;

	public SimpleInfo(JSONObject paramJSONObject) {
		if (paramJSONObject == null)
			return;
		this.id = paramJSONObject.optString("id", "");
		this.imageBaseUrl = paramJSONObject.optString("image_base_url");
		this.createdAt = paramJSONObject.optString("created_at");
		this.updatedAt = paramJSONObject.optString("updated_at");
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getId() {
		return this.id;
	}

	public String getImageBaseUrl() {
		return this.imageBaseUrl;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageBaseUrl(String imageBaseUrl) {
		this.imageBaseUrl = imageBaseUrl;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
}

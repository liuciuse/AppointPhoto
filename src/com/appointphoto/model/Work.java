package com.appointphoto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appointphoto.activity.util.MyURI;

public class Work implements Serializable {
	private String category;
	private String createdAt;
	private String description;//����
	private String id;//��Ʒid
	private String imageBaseUrl;//ͼ���ַ
	private List<String> labels;//��Ʒ��ǩ
	private int numLikes;//ϲ����
	private int numReviews;//������
	private String updatedAt;
	private String imgheight;//ͼ��߶�
	private String imgwidth;//ͼ����

	// ��Ʒ
	public Work(JSONObject paramJSONObject) {
		if (paramJSONObject == null)
			return;
		this.id = paramJSONObject.optString("id", "");
		this.description = paramJSONObject.optString("description",
				"TA��û�������Ʒ����");
		this.createdAt = paramJSONObject.optString("created_at", "");
		this.updatedAt = paramJSONObject.optString("updated_at", "");
		this.category = paramJSONObject.optString("item_category", "");
		this.imageBaseUrl = paramJSONObject.optString("image_base_url",
				MyURI.testPtURI);
		this.numLikes = paramJSONObject.optInt("num_likes", 0);
		this.numReviews = paramJSONObject.optInt("num_reviews", 0);
		this.imgheight = paramJSONObject.optString("imgheight", "1024");
		this.imgwidth = paramJSONObject.optString("imgwidth", "1024");
		
		
		JSONArray localJSONArray = paramJSONObject.optJSONArray("labels");
		ArrayList localArrayList = new ArrayList();
		int i = 0;
		this.labels = localArrayList;
		if (localJSONArray == null) {
			try {
				localJSONArray = new JSONArray("[]");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		while (true) {
			if (i >= localJSONArray.length()) {
				return;
			}
			try {
				localArrayList.add(localJSONArray.getString(i));
				i++;
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
		
	}

	public String getImgheight() {
		return imgheight;
	}

	public void setImgheight(String imgheight) {
		this.imgheight = imgheight;
	}

	public String getImgwidth() {
		return imgwidth;
	}

	public void setImgwidth(String imgwidth) {
		this.imgwidth = imgwidth;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageBaseUrl() {
		return imageBaseUrl;
	}

	public void setImageBaseUrl(String imageBaseUrl) {
		this.imageBaseUrl = imageBaseUrl;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public int getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}

	public int getNumReviews() {
		return numReviews;
	}

	public void setNumReviews(int numReviews) {
		this.numReviews = numReviews;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}

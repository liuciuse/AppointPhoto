package com.appointphoto.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appointphoto.activity.util.JsonUtil;

public class Photographer extends User {
	private int credit;//
	private String description;// 用户描述
	private String gender;// 性别
	private String inviteCode;// 邀请码
	private boolean isFollow;//
	private boolean isShowRealname;// 显示真实姓名
	private List<String> labelList;// 标签
	private int numItems;//
	private int numLikes;// 被喜欢数
	private int numReviews;// 消息数
	private int numWorks;// 作品数
	private int numfollowers;//
	private String price;// 价格
	private String priceUnit;// 价格单位
	private String realname;// 真实姓名
	private List<XItem> serviceList;// 提供的服务
	private String telephone;// 电话号码
	private List<Work> workList;// 作品数目

	public Photographer(JSONObject paramJSONObject) {
		super(paramJSONObject);
		this.realname = paramJSONObject.optString("realname", "");
		this.description = paramJSONObject.optString("description",
				"这个人很懒，暂无简介");
		this.gender = paramJSONObject.optString("gender", "U");
		this.telephone = paramJSONObject.optString("telephone", "");
		this.isShowRealname = paramJSONObject
				.optBoolean("show_realname", false);
		this.isFollow = paramJSONObject.optBoolean("is_follow", false);
		this.inviteCode = paramJSONObject.optString("invite_code", "");
		this.credit = paramJSONObject.optInt("credit", 0);
		this.price = paramJSONObject.optString("price", "-1");
		this.priceUnit = paramJSONObject.optString("price_unit", "天");
		this.numWorks = paramJSONObject.optInt("num_works", 0);
		this.numItems = paramJSONObject.optInt("num_items", 0);
		this.numfollowers = paramJSONObject.optInt("num_followers", 0);
		this.numLikes = paramJSONObject.optInt("num_liked", 0);
		this.numReviews = paramJSONObject.optInt("num_reviewed", 0);
		this.workList = new ArrayList();
		this.serviceList = new ArrayList<XItem>();
		if (paramJSONObject.optJSONArray("works") != null)
			this.workList.addAll(JsonUtil.jsonToProtfolioList(paramJSONObject
					.optJSONArray("works")));
		if (paramJSONObject.optJSONArray("items") != null)
			this.serviceList = JsonUtil.jsonToServiceList(paramJSONObject
					.optJSONArray("items"));
		ArrayList localArrayList = new ArrayList();
		JSONArray localJSONArray = paramJSONObject.optJSONArray("labels");
		int i = 0;
		while (true) {
			if (localJSONArray == null) {
				break;
			}
			if (i >= localJSONArray.length()) {
				this.labelList = localArrayList;
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

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public boolean isFollow() {
		return isFollow;
	}

	public void setFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}

	public boolean isShowRealname() {
		return isShowRealname;
	}

	public void setShowRealname(boolean isShowRealname) {
		this.isShowRealname = isShowRealname;
	}

	public List<String> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<String> labelList) {
		this.labelList = labelList;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
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

	public int getNumWorks() {
		return numWorks;
	}

	public void setNumWorks(int numWorks) {
		this.numWorks = numWorks;
	}

	public int getNumfollowers() {
		return numfollowers;
	}

	public void setNumfollowers(int numfollowers) {
		this.numfollowers = numfollowers;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public List<XItem> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<XItem> serviceList) {
		this.serviceList = serviceList;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Work> getWorkList() {
		return workList;
	}

	public void setWorkList(List<Work> workList) {
		this.workList = workList;
	}
	
	
}

package com.appointphoto.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.appointphoto.activity.util.MyURI;

/**
 * 用户
 * 
 * @author 凯
 * 
 */
public class User implements Serializable {
	protected String avatar;// 头像
	protected String city;// 所在城市
	protected String createdAt;//
	protected String id;// 用户id
	protected String inviteCode;// 用户邀请码
	protected boolean isFirstLogin;// 是否是第一次登录
	protected String nickname;// 用户昵称
	protected int numUnReads;// 用户未读消息数
	protected String primaryAccount;// 用户账号
	protected String profileImageUrl;// 用户认证图片地址
	// private QqUser qq;
	protected int score;// 用户等级
	// private SinaUser sina;
	protected String state;// 用户状态
	protected String updatedAt;
	protected String userType;// 用户类型

	public User() {
	}

	public User(JSONObject paramJSONObject) {
		if (paramJSONObject == null)
			return;
		this.avatar = paramJSONObject.optString("avatar_url", MyURI.testavaterURI);
		this.id = paramJSONObject.optString("id", "");
		this.nickname = paramJSONObject.optString("nickname", "小明");
		this.primaryAccount = paramJSONObject.optString("primary_account", "");
		this.profileImageUrl = paramJSONObject.optString("profile_image_url",
				"");
		this.city = paramJSONObject.optString("location", "");
		this.numUnReads = paramJSONObject.optInt("num_unreads", 0);
		this.userType = paramJSONObject.optString("user_type", "User");
		this.isFirstLogin = paramJSONObject.optBoolean("first_login", false);
		this.createdAt = paramJSONObject.optString("created_at", "");
		this.updatedAt = paramJSONObject.optString("updated_at", "");
		this.state = paramJSONObject.optString("state", "");
		this.score = paramJSONObject.optInt("credit", 0);
		this.inviteCode = paramJSONObject.optString("invite_code", "");
		/*
		 * JSONArray localJSONArray = paramJSONObject.optJSONArray("oauths"); if
		 * (localJSONArray == null) continue; for (int i = 0; i <
		 * localJSONArray.length(); i++) try { String str =
		 * localJSONArray.getJSONObject(i).optString( "type", "qq"); if
		 * (str.equals("qq")) { this.qq = new QqUser(
		 * paramJSONObject.optJSONObject("qq")); } else { if
		 * (!str.equals("sina")) continue; this.sina = new SinaUser(
		 * paramJSONObject.optJSONObject("sina")); } } catch (JSONException
		 * localJSONException) { localJSONException.printStackTrace(); }
		 */

	}

	public String getAvatar() {
		return this.avatar;
	}

	public String getCity() {
		return this.city;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getId() {
		return this.id;
	}

	public String getInviteCode() {
		return this.inviteCode;
	}

	public String getNickname() {
		return this.nickname;
	}

	public int getNumUnReads() {
		return this.numUnReads;
	}

	public String getPrimaryAccount() {
		return this.primaryAccount;
	}

	public String getProfileImageUrl() {
		return this.profileImageUrl;
	}

	// public QqUser getQq() {
	// return this.qq;
	// }

	public int getScore() {
		return this.score;
	}

	// public SinaUser getSina() {
	// return this.sina;
	// }

	public String getState() {
		return this.state;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getUserType() {
		return this.userType;
	}

	public boolean isFirstLogin() {
		return this.isFirstLogin;
	}

	public void setCity(String paramString) {
		this.city = paramString;
	}

	public void setInviteCode(String paramString) {
		this.inviteCode = paramString;
	}

	public void setNickname(String paramString) {
		this.nickname = paramString;
	}

	public void setNumUnReads(int paramInt) {
		this.numUnReads = paramInt;
	}

	public void setPrimaryAccount(String paramString) {
		this.primaryAccount = paramString;
	}

	// public void setQq(QqUser paramQqUser) {
	// this.qq = paramQqUser;
	// }

	public void setScore(int paramInt) {
		this.score = paramInt;
	}

	// public void setSina(SinaUser paramSinaUser) {
	// this.sina = paramSinaUser;
	// }

	public void setState(String paramString) {
		this.state = paramString;
	}

	public void setUserType(String paramString) {
		this.userType = paramString;
	}
}

package com.appointphoto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appointphoto.activity.util.MyURI;

//提供的服务
public class XItem implements Serializable {
	private String albumInfo;
	private String category;
	private String costumeInfo;
	private String createdAt;
	private int daysPerOrder;//预定天数
	private String description;
	private String frameInfo;
	private String imageUrl;//图片地址
	private int isGivingOriginals;
	private int isProvidingAlbum;
	private int isProvidingCostume;
	private int isProvidingFrame;
	private int isProvidingMakeup;
	private boolean isRecommended;
	private List<SimpleInfo> listSimples;
	private String location;
	private int numCurrentInventory;
	private int numInventory;
	private int numProcessedPhotos;
	private int numSold;
	private int numTakenPhotos;
	private int numTakenPositons;
	private int price;//价格
	private String priceUnit;
	private String serviceId;
	private String serviceOn;
	private String serviceTime;
	private String serviceTo;
	private List<String> styles;//风格
	private String title;//标题
	private String updatedAt;
	private Photographer user;

	public XItem(JSONObject paramJSONObject) {
		if (paramJSONObject == null)
			return;
		this.serviceId = paramJSONObject.optString("id", "");
		this.category = paramJSONObject.optString("category", "");
		this.price = paramJSONObject.optInt("price", 100);
		this.priceUnit = paramJSONObject.optString("price_unit", "");
		this.description = paramJSONObject.optString("description", "");
		this.createdAt = paramJSONObject.optString("created_at", "");
		this.updatedAt = paramJSONObject.optString("updated_at", "");
		this.title = paramJSONObject.optString("title", "我是小苹果");
		this.imageUrl = paramJSONObject.optString("image_base_url", MyURI.testavaterURI);
		this.location = paramJSONObject.optString("location", "");
		if (paramJSONObject.optJSONObject("user") != null)
			this.user = new Photographer(paramJSONObject.optJSONObject("user"));
		this.numTakenPhotos = paramJSONObject.optInt("num_taken_photos", 1);
		this.numProcessedPhotos = paramJSONObject.optInt(
				"num_processed_photos", 1);
		this.serviceTime = paramJSONObject.optString("service_time");
		this.styles = new ArrayList();
		this.listSimples = new ArrayList();
		this.isGivingOriginals = paramJSONObject.optInt(
				"is_providing_original", -1);
		this.isProvidingFrame = paramJSONObject
				.optInt("is_providing_frame", -1);
		this.frameInfo = paramJSONObject.optString("frame_info", "");
		this.isProvidingAlbum = paramJSONObject
				.optInt("is_providing_album", -1);
		this.albumInfo = paramJSONObject.optString("album_info", "");
		this.isProvidingMakeup = paramJSONObject.optInt("is_providing_makeup",
				-1);
		this.isProvidingCostume = paramJSONObject.optInt(
				"is_providing_costume", -1);
		this.costumeInfo = paramJSONObject.optString("costume_info", "");
		this.serviceOn = paramJSONObject.optString("service_on", "任意日期");
		this.serviceTo = paramJSONObject.optString("service_to", "");
		this.numTakenPositons = paramJSONObject
				.optInt("num_taken_positions", 1);
		this.daysPerOrder = paramJSONObject.optInt("days_pre_order", 1);
		this.numInventory = paramJSONObject.optInt("num_inventory", -1);
		this.numCurrentInventory = paramJSONObject.optInt(
				"num_current_inventory", 0);
		this.numSold = paramJSONObject.optInt("num_sold", 0);
		this.isRecommended = paramJSONObject
				.optBoolean("is_recommended", false);
		JSONArray localJSONArray1 = paramJSONObject.optJSONArray("samples");
		
		//sample和style没有初始化
	}

	public String getAlbumInfo() {
		return albumInfo;
	}

	public void setAlbumInfo(String albumInfo) {
		this.albumInfo = albumInfo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCostumeInfo() {
		return costumeInfo;
	}

	public void setCostumeInfo(String costumeInfo) {
		this.costumeInfo = costumeInfo;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public int getDaysPerOrder() {
		return daysPerOrder;
	}

	public void setDaysPerOrder(int daysPerOrder) {
		this.daysPerOrder = daysPerOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrameInfo() {
		return frameInfo;
	}

	public void setFrameInfo(String frameInfo) {
		this.frameInfo = frameInfo;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getIsGivingOriginals() {
		return isGivingOriginals;
	}

	public void setIsGivingOriginals(int isGivingOriginals) {
		this.isGivingOriginals = isGivingOriginals;
	}

	public int getIsProvidingAlbum() {
		return isProvidingAlbum;
	}

	public void setIsProvidingAlbum(int isProvidingAlbum) {
		this.isProvidingAlbum = isProvidingAlbum;
	}

	public int getIsProvidingCostume() {
		return isProvidingCostume;
	}

	public void setIsProvidingCostume(int isProvidingCostume) {
		this.isProvidingCostume = isProvidingCostume;
	}

	public int getIsProvidingFrame() {
		return isProvidingFrame;
	}

	public void setIsProvidingFrame(int isProvidingFrame) {
		this.isProvidingFrame = isProvidingFrame;
	}

	public int getIsProvidingMakeup() {
		return isProvidingMakeup;
	}

	public void setIsProvidingMakeup(int isProvidingMakeup) {
		this.isProvidingMakeup = isProvidingMakeup;
	}

	public boolean isRecommended() {
		return isRecommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	public List<SimpleInfo> getListSimples() {
		return listSimples;
	}

	public void setListSimples(List<SimpleInfo> listSimples) {
		this.listSimples = listSimples;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNumCurrentInventory() {
		return numCurrentInventory;
	}

	public void setNumCurrentInventory(int numCurrentInventory) {
		this.numCurrentInventory = numCurrentInventory;
	}

	public int getNumInventory() {
		return numInventory;
	}

	public void setNumInventory(int numInventory) {
		this.numInventory = numInventory;
	}

	public int getNumProcessedPhotos() {
		return numProcessedPhotos;
	}

	public void setNumProcessedPhotos(int numProcessedPhotos) {
		this.numProcessedPhotos = numProcessedPhotos;
	}

	public int getNumSold() {
		return numSold;
	}

	public void setNumSold(int numSold) {
		this.numSold = numSold;
	}

	public int getNumTakenPhotos() {
		return numTakenPhotos;
	}

	public void setNumTakenPhotos(int numTakenPhotos) {
		this.numTakenPhotos = numTakenPhotos;
	}

	public int getNumTakenPositons() {
		return numTakenPositons;
	}

	public void setNumTakenPositons(int numTakenPositons) {
		this.numTakenPositons = numTakenPositons;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceOn() {
		return serviceOn;
	}

	public void setServiceOn(String serviceOn) {
		this.serviceOn = serviceOn;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceTo() {
		return serviceTo;
	}

	public void setServiceTo(String serviceTo) {
		this.serviceTo = serviceTo;
	}

	public List<String> getStyles() {
		return styles;
	}

	public void setStyles(List<String> styles) {
		this.styles = styles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Photographer getUser() {
		return user;
	}

	public void setUser(Photographer user) {
		this.user = user;
	}
	
	

}

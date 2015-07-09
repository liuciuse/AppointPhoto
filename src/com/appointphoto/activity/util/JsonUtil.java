package com.appointphoto.activity.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appointphoto.model.Photographer;
import com.appointphoto.model.Work;
import com.appointphoto.model.XItem;

public class JsonUtil {

	// 获取作品列表
	public static List<Work> jsonToProtfolioList(JSONArray paramJSONArray) {
		ArrayList localArrayList = new ArrayList();
		int i = paramJSONArray.length();
		int j = 0;
		while (true) {
			if (j >= i)
				return localArrayList;
			try {
				localArrayList
						.add(new Work((JSONObject) paramJSONArray.get(j)));
				j++;
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	}

	// 获取服务列表
	public static List<XItem> jsonToServiceList(JSONArray paramJSONArray) {
		ArrayList localArrayList = new ArrayList();
		int i = paramJSONArray.length();
		int j = 0;
		while (true) {
			if (j >= i)
				return localArrayList;
			try {
				localArrayList
						.add(new XItem((JSONObject) paramJSONArray.get(j)));
				j++;
			} catch (JSONException localJSONException) {
				while (true)
					localJSONException.printStackTrace();
			}
		}
	}

	// 获取摄影师列表
	public static List<Photographer> jsonToPhotographerList(
			JSONArray paramJSONArray) {
		ArrayList localArrayList = new ArrayList();
		int i = paramJSONArray.length();
		int j = 0;
		while (true) {
			if (j >= i)
				return localArrayList;
			try {
				localArrayList.add(new Photographer((JSONObject) paramJSONArray
						.get(j)));
				j++;
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	}

	//获取作品列表
	public static List<Work> jsonToWorklist(JSONArray paramJSONArray) {
		ArrayList localArrayList = new ArrayList();
	    int i = paramJSONArray.length();
	    int j = 0;
	    while (true)
	    {
	      if (j >= i)
	        return localArrayList;
	      try
	      {
	        localArrayList.add(new Work((JSONObject)paramJSONArray.get(j)));
	        j++;
	      }
	      catch (JSONException localJSONException)
	      {
	          localJSONException.printStackTrace();
	      }
	    }
	}
}

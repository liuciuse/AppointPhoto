package com.appointphoto.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.model.Work;

public class MyWorkList {
	// 当前进入摄影师空间，摄影师作品
	private List<Work> workList = new ArrayList<Work>();

	public List<Work> getWorkList() {
		return workList;
	}

	public void setWorkList(List<Work> workList) {
		this.workList = workList;
	}
	
	//刷新界面
	public void refresh() throws JSONException, Exception{
		int[] statusCode = new int[1];
		setWorkList((JsonUtil
				.jsonToWorklist(new JSONArray(MyURI.uri2Str(
						MyURI.RefreshWorkURI, MyURI
								.refreshWorks().toString(),
						statusCode )))));
	}
	//获取更多数据
	public void getmore() throws JSONException, Exception{
		int[] statusCode = new int[1];
		getWorkList().addAll(
				((JsonUtil.jsonToWorklist(new JSONArray(MyURI.uri2Str(
						MyURI.getmoreWorkURI, MyURI.getmoreWorks()
								.toString(), statusCode))))));
	}
}

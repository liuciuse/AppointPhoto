package com.appointphoto.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.model.Work;

public class MyWorkList {
	// ��ǰ������Ӱʦ�ռ䣬��Ӱʦ��Ʒ
	private List<Work> workList = new ArrayList<Work>();

	public List<Work> getWorkList() {
		return workList;
	}

	public void setWorkList(List<Work> workList) {
		this.workList = workList;
	}
	
	//ˢ�½���
	public void refresh() throws JSONException, Exception{
		int[] statusCode = new int[1];
		setWorkList((JsonUtil
				.jsonToWorklist(new JSONArray(MyURI.uri2Str(
						MyURI.RefreshWorkURI, MyURI
								.refreshWorks().toString(),
						statusCode )))));
	}
	//��ȡ��������
	public void getmore() throws JSONException, Exception{
		int[] statusCode = new int[1];
		getWorkList().addAll(
				((JsonUtil.jsonToWorklist(new JSONArray(MyURI.uri2Str(
						MyURI.getmoreWorkURI, MyURI.getmoreWorks()
								.toString(), statusCode))))));
	}
}

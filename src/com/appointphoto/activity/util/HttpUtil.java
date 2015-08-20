package com.appointphoto.activity.util;

import com.loopj.android.http.AsyncHttpClient;

public class HttpUtil {
	public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	static {
		asyncHttpClient.setConnectTimeout(2000);
	}
	
}

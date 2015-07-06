package com.appointphoto.activity.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class MyURI {
	public static final String loginURI = "http://172.16.157.12:8080/AppointPhotoServer/userLoginAction";
	public static final String photographersURI = "http://172.16.157.12:8080/AppointPhotoServer/userLoginAction";
	public static final String RefreshPsURI = "http://liuciuse:8080/mytest/jsontest";
	public static final String getmorePsURI = "http://liuciuse:8080/mytest/jsontest";
	public static final String testPtURI = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	public static final String testavaterURI = "http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg";
	
	//访问URI获取json字符串
	public static String uri2Str(String urlStr,String jsonStr,int []statusCode) throws Exception {
		StringBuffer sb = null;
		HttpURLConnection connection = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(30000);
			
			connection.setRequestProperty("Content-Type",
					"application/json");
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.writeBytes(jsonObject.toString());
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String lines;
			sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb);
			reader.close();
			statusCode[0] = connection.getResponseCode();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			connection.disconnect();
		}
		return sb.toString();
	}
	
	//获取最新Photographers的json请求
	public static JSONObject refreshPts(){
		JSONObject request = new JSONObject();
		try {
			request.put("cmd", "refeshPs");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return request;
	}
	
	//获取更多Photographers的请求
	public static JSONObject morePts(){
		JSONObject request = new JSONObject();
		try {
			request.put("cmd", "getmorePs");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return request;
	}
	
	
	
}

package com.appointphoto.activity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.appointphoto.activity.util.HttpUtil;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginDetailActivity extends Activity {
	Button login_normol_btn;
	EditText user_name;
	EditText password;
	private MyHandler myHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.logindetail);
		initItems();
	}

	private void initItems() {
		// 登录
		login_normol_btn = (Button) findViewById(R.id.login_normol_btn);
		login_normol_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(new MyThread()).start();
			}
		});
		user_name = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
		myHandler = new MyHandler();

	}

	// 处理异步回调
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法，接受数据
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Bundle b = msg.getData();
				String reslut = b.getString("result");
			} else if (msg.what == 404) {
				// 获取数据失败
			}

		}
	}

	// http get请求
	class MyThread implements Runnable {
		public void run() {

			String urlStr = "http://172.16.157.12:8080/AppointPhotoServer/userLoginAction";
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("name", user_name.getText().toString());
				jsonObject.put("password", password.getText().toString());

				URL url = new URL(urlStr);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);

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
				StringBuffer sb = new StringBuffer("");
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sb.append(lines);
				}
				System.out.println(sb);
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Message msg = new Message();
			int statusCode = 0;
			if (statusCode == 200) {
				msg.what = 1;
				Bundle b = new Bundle();
				b.putString("result", "");
				msg.setData(b);
			} else {
				msg.what = 404;
			}

			LoginDetailActivity.this.myHandler.sendMessage(msg); // 向Handler发送消息，更新UI

		}

	}

	// 登录传送参数
	private String getLoginJson() {
		JSONObject sendJson = new JSONObject();
		try {
			sendJson.put("name", user_name.getText().toString());
			sendJson.put("password", password.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sendJson.toString();
	}
}

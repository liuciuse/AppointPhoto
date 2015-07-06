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
import com.appointphoto.activity.util.MyURI;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �Ƴ�ActionBar
		setContentView(R.layout.logindetail);
		initItems();
	}

	private void initItems() {
		// ��¼
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

	// �����첽�ص�
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// ���������д�˷�������������
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Bundle b = msg.getData();
				String reslut = b.getString("result");
			} else if (msg.what == 404) {
				// ��ȡ����ʧ��
			}

		}
	}

	// http post����
	class MyThread implements Runnable {
		public void run() {

			String urlStr = MyURI.loginURI;
			JSONObject jsonObject = new JSONObject();
			String result = null;
			int []statusCode = new int[1];
			try {
				jsonObject.put("name", user_name.getText().toString());
				jsonObject.put("password", password.getText().toString());
				result = MyURI.uri2Str(urlStr, jsonObject.toString(),statusCode);
			} catch (JSONException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			Message msg = new Message();
			
			if (statusCode[0] == 200) {
				msg.what = 1;
				Bundle b = new Bundle();
				b.putString("result",result);
				msg.setData(b);
			} else {
				msg.what = 404;
			}

			LoginDetailActivity.this.myHandler.sendMessage(msg); // ��Handler������Ϣ������UI

		}

	}

	// ��¼���Ͳ���
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

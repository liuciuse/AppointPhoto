package com.appointphoto.activity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import com.appointphoto.activity.util.MD5;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	EditText user_name;//用户名
	EditText password;//密码
	private MyHandler myHandler;
	ProgressDialog mypDialog;
	public String passEx;//签名后密码

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
				mypDialog.show();
			}
		});
		user_name = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
		myHandler = new MyHandler();

		// 注册对话框
		initDialog();
		// 初始化设置账户密码
		inituserwpassword();

	}

	private void inituserwpassword() {
		SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		String usrStr = sharedPreferences.getString("user", "");
		JSONObject user = null;
		String userName = "";
		String userPassword = "";
		try {
			user = new JSONObject(usrStr);
			userName = user.optString("name","");
			userPassword = user.optString("psssword", "");
			System.out.println(userPassword);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		user_name.setText(userName);
		password.setText(userPassword);
		
	}

	// 注册对话框
	private void initDialog() {
		mypDialog = new ProgressDialog(this);
		// 设置进度条风格，风格为圆形，旋转的
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置ProgressDialog 标题
		mypDialog.setTitle("登录");
		mypDialog.setMessage("正在登录...");
		// 设置ProgressDialog 的进度条是否不明确
		mypDialog.setIndeterminate(false);
		// 设置ProgressDialog 是否可以按退回按键取消
		mypDialog.setCancelable(false);
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
				String result = b.getString("result");
				if ("success".equals(result)) {
					try {
						passEx = MD5.getMD5(password.getText().toString());
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
					
					SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
					Editor editor =  sharedPreferences.edit();
					editor.clear();
					editor.putString("user", getUserJson());//持久化对象user
					editor.commit();
					
					//返回MainActivity
					backtoMain();
				}
			} else if (msg.what == 404) {
				// 获取数据失败
			}
			
			mypDialog.dismiss();

		}
	}

	// http post请求
	class MyThread implements Runnable {
		public void run() {

			String urlStr = MyURI.loginURI;
			JSONObject jsonObject = new JSONObject();
			String result = null;
			int[] statusCode = new int[1];
			try {
				jsonObject.put("name", user_name.getText().toString());
				jsonObject.put("password", password.getText().toString());
				result = MyURI.uri2Str(urlStr, jsonObject.toString(),
						statusCode);
			} catch (JSONException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();

			if (statusCode[0] == 200) {
				msg.what = 1;
				Bundle b = new Bundle();
				b.putString("result", result);
				msg.setData(b);
			} else {
				msg.what = 404;
			}

			LoginDetailActivity.this.myHandler.sendMessage(msg); // 向Handler发送消息，更新UI

		}

	}

	//登录成功，保存用户信息
	public String getUserJson() {
		JSONObject user = new JSONObject();
		try {
			user.put("name", user_name.getText().toString());
			user.put("psssword", password.getText().toString());
			user.put("passEx", passEx);
			user.put("loginstate", "logined");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user.toString();
	}

	//返回MainActivity
	public void backtoMain() {
		MyService.finishActivities(new String[]{"LoginDetailActivity","ChangeRoleActivity","LoginActivity"});
	}

}

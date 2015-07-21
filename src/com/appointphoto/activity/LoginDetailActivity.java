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
import com.special.ResideMenu.ResideMenu;

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
import android.widget.TextView;

public class LoginDetailActivity extends Activity {
	Button login_normol_btn;
	EditText user_name;// �û���
	EditText password;// ����
	private MyHandler myHandler;
	ProgressDialog mypDialog;
	public String passEx;// ǩ��������

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
				mypDialog.show();
			}
		});
		user_name = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
		myHandler = new MyHandler();

		// ע��Ի���
		initDialog();
		// ��ʼ�������˻�����
		inituserwpassword();

	}

	private void inituserwpassword() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				getResources().getString(R.string.user_pref),
				Context.MODE_PRIVATE);
		String usrStr = sharedPreferences.getString(
				getResources().getString(R.string.user_pref), "");
		JSONObject user = null;
		String userName = "";
		String userPassword = "";
		try {
			user = new JSONObject(usrStr);
			userName = user.optString(
					getResources().getString(R.string.user_name), "");
			userPassword = user.optString(
					getResources().getString(R.string.user_password), "");
			System.out.println(userPassword);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		user_name.setText(userName);
		password.setText(userPassword);

	}

	// ע��Ի���
	private void initDialog() {
		mypDialog = new ProgressDialog(this);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ����ProgressDialog ����
		mypDialog.setTitle("��¼");
		mypDialog.setMessage("���ڵ�¼...");
		// ����ProgressDialog �Ľ������Ƿ���ȷ
		mypDialog.setIndeterminate(false);
		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
		mypDialog.setCancelable(false);
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
				String result = b.getString("result");
				if ("success".equals(result)) {

					SharedPreferences sharedPreferences = getSharedPreferences(
							"user", Context.MODE_PRIVATE);
					Editor editor = sharedPreferences.edit();
					editor.putString("user", getUserJson());// �־û�����user
					editor.commit();

					// ����MainActivity
					backtoMain();
				}
			} else if (msg.what == 404) {
				// ��ȡ����ʧ��
			}

			mypDialog.dismiss();

		}
	}

	// http post����
	class MyThread implements Runnable {
		public void run() {

			String urlStr = MyURI.loginURI;
			JSONObject jsonObject = new JSONObject();
			String result = null;
			int[] statusCode = new int[1];
			try {
				passEx = MD5.getMD5(password.getText().toString());
				jsonObject.put(getResources().getString(R.string.user_name),
						user_name.getText().toString());
				jsonObject.put(
						getResources().getString(R.string.user_password),
						passEx);
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

			LoginDetailActivity.this.myHandler.sendMessage(msg); // ��Handler������Ϣ������UI

		}

	}

	// ��¼�ɹ��������û���Ϣ
	public String getUserJson() {
		JSONObject user = new JSONObject();
		try {
			user.put(getResources().getString(R.string.user_name), user_name
					.getText().toString());
			user.put(getResources().getString(R.string.user_password), password
					.getText().toString());
			user.put(getResources().getString(R.string.user_passwordMD5),
					passEx);
			user.put(getResources().getString(R.string.user_loginstate),
					"logined");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user.toString();
	}

	// ����MainActivity
	public void backtoMain() {

		MainActivity mainact = (MainActivity) MyService
				.getActivityByName("MainActivity");
		initMainResideMenu(mainact.getResideMenu());
		MyService.finishActivities(new String[] { "LoginDetailActivity",
				"ChangeRoleActivity", "LoginActivity" });
	}

	// ��ʼ��mainactivity�Ĳ��residemenu
	private void initMainResideMenu(ResideMenu resideMenu) {
		TextView user_nickname_text_view = (TextView) resideMenu
				.findViewById(R.id.user_nickname_text_view);
		user_nickname_text_view.setText(user_name.getText().toString());
	}

}

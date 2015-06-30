package com.appointphoto.activity;

import com.example.appointphoto.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class LoginActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ÒÆ³ýActionBar
		setContentView(R.layout.login);
	}

}

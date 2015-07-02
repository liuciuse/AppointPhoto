package com.appointphoto.activity;

import com.example.appointphoto.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends Activity {
	Button rigester_normol_btn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ÒÆ³ýActionBar
		setContentView(R.layout.login);
		initItems();
	}

	private void initItems() {
		rigester_normol_btn = (Button) findViewById(R.id.rigester_normol_btn);
		rigester_normol_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				LoginActivity.this.startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
			}
		});
	}

}

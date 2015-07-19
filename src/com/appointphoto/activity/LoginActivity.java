package com.appointphoto.activity;

import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends Activity {
	Button rigester_normol_btn;
	Button left_btn;
	Button login_normol_btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ÒÆ³ýActionBar
		setContentView(R.layout.login);
		initItems();
	}

	private void initItems() {
		rigester_normol_btn = (Button) findViewById(R.id.rigester_normol_btn);
		rigester_normol_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LoginActivity.this.startActivity(new Intent(LoginActivity.this,
						RegisterActivity.class));
			}
		});
		left_btn = (Button) findViewById(R.id.left_btn);
		left_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MyService.finishActivity(LoginActivity.this);
			}
		});
		login_normol_btn = (Button) findViewById(R.id.login_normol_btn);
		login_normol_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.startActivity(new Intent(LoginActivity.this,
						LoginDetailActivity.class));
			}
		});
	}

}

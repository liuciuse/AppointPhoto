package com.appointphoto.activity;

import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SettingActivity extends Activity {
	private Button navibackbtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ÒÆ³ýActionBar
		setContentView(R.layout.settings);

		init();

	}

	private void init() {
		navibackbtn = (Button) findViewById(R.id.left_btn);
		navibackbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyService.finishActivity(SettingActivity.this);
			}
		});

	}
}

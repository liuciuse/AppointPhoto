package com.appointphoto.activity;

import com.appointphoto.activity.util.HeadViewUtil;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BookActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ÒÆ³ýActionBar
		setContentView(R.layout.book);
		initItems();
	}

	private void initItems() {
		HeadViewUtil.back(this);
	}

}

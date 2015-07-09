package com.appointphoto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.appointphoto.adapter.PhotoViewPagerAdapter;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

public class ImageBrowActivity extends Activity {
	private ViewPager mViewPager;
	private PagerAdapter adapter;

	public PagerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.brower_image);

		initview();
	}

	// 初始化界面
	private void initview() {
		mViewPager = (ViewPager) findViewById(R.id.brower_pager);
		adapter = new PhotoViewPagerAdapter(ImageBrowActivity.this);
		mViewPager.setAdapter(adapter);
	}


}

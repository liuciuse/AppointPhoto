package com.appointphoto.activity;

import java.util.ArrayList;

import com.appointphoto.service.MyService;
import com.appointphoto.widget.PageControl;
import com.example.appointphoto.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class ServiceDetailActivity extends Activity {

	private Button order_btn;// 预约按钮
	private ViewPager activity_viewpager;// 上方的图片
	private PageControl pagecontrol;
	private ArrayList views;// 保存了可以滚动的view
	private LayoutInflater inflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.item_detail);
		initItems();
	}

	private void initItems() {
		inflater = getLayoutInflater();
		order_btn = (Button) findViewById(R.id.item_order_btn);
		order_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ServiceDetailActivity.this.startActivity(new Intent(
						ServiceDetailActivity.this, BookActivity.class));
			}
		});

		// 设置上方图片
		// 初始化中间的活动项
		activity_viewpager = (ViewPager) findViewById(R.id.activity_viewpager);

		// 初始化viewpager所用views
		views = new ArrayList();
		for (int i = 0; i < 3; i++) {
			View tempView = inflater.inflate(R.layout.list_activity_item,
					activity_viewpager, false);
			views.add(tempView);
			// 点击进入服务
		}
		// 初始化pagecontrol
		pagecontrol = (PageControl) findViewById(R.id.activity_pagecontrol);
		pagecontrol.setCount(3);

		// 设置Adapter
		activity_viewpager.setAdapter(new MyPagerAdapter());
		// 设置监听状态变化，用来表达其他美化表示
		activity_viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pagecontrol.setSelected(position);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	
	// 设置中间部分滑动内容 数据源
		private class MyPagerAdapter extends PagerAdapter {
			@Override
			public int getCount() {
				return views.size();
			}
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) views.get(position));
			}
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView((View) views.get(position));
				return views.get(position);
			}
		}

}

package com.appointphoto.adapter;

import java.util.ArrayList;

import com.example.appointphoto.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {

	private ArrayList views = new ArrayList<View>();
	private Context context;
	private LayoutInflater inflater;

	public MyPagerAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		// 初始化viewpager所用views
		views = new ArrayList<View>();
		for (int i = 0; i < 3; i++) {
			View tempView = inflater.inflate(R.layout.list_activity_item, null,
					false);
			views.add(tempView);
			// 点击进入服务
			tempView.setOnClickListener(new MyOnClickListener());
		}
	}

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

	class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {

		}

	}

}

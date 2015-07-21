package com.appointphoto.adapter;

import java.util.ArrayList;

import com.appointphoto.activity.PhotographerActivityEx;
import com.appointphoto.activity.ServiceDetailActivity;
import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.adapter.ServicePagerAdapter.MyOnClickListener;
import com.example.appointphoto.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ServiceImgAdapter extends PagerAdapter {

	ArrayList<ImageView> views = new ArrayList<ImageView>();
	Context context;
	private ImageLoadingListener animateFirstListener;

	public ServiceImgAdapter(Context context) {
		this.context = context;
		animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener(
				context);
		initViews();
	}

	public void initViews() {
		// 初始化viewpager所用views
		views = new ArrayList<ImageView>();
		for (int i = 0; i < 3; i++) {
			ImageView tempView = new ImageView(context);
			initImageView(tempView);
			views.add(tempView);
			// 点击进入服务
			tempView.setOnClickListener(new MyOnClickListener());
		}
	}

	private void initImageView(ImageView tempView) {
		ImageLoader.getInstance().displayImage(MyURI.testavaterURI,
				tempView, ImageLoaderUtil.options,
				animateFirstListener);
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
			context.startActivity(new Intent(context,
					ServiceDetailActivity.class));
		}

	}
}

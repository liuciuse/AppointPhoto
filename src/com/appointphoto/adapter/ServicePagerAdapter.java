package com.appointphoto.adapter;

import java.util.ArrayList;
import java.util.List;

import com.appointphoto.activity.PhotographerActivityEx;
import com.appointphoto.activity.ServiceDetailActivity;
import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.model.XItem;
import com.example.appointphoto.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ServicePagerAdapter extends PagerAdapter {

	private ArrayList views = new ArrayList<View>();
	private List<XItem> services = new ArrayList<XItem>();
	private ImageLoadingListener animateFirstListener;

	public List<XItem> getServices() {
		return services;
	}

	public void setServices(List<XItem> services) {
		this.services = services;
	}

	private Context context;
	private LayoutInflater inflater;

	public ServicePagerAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener(
				context);
		initViews();
	}

	public void initViews() {
		// 初始化viewpager所用views
		views = new ArrayList<View>();
		for (int i = 0; i < services.size(); i++) {
			View tempView = inflater.inflate(R.layout.list_activity_item, null,
					false);
			inittempView(tempView, services.get(i));
			views.add(tempView);
			// 点击进入服务
			tempView.setOnClickListener(new MyOnClickListener());
		}
		if (services.size() != 0) {
			PhotographerActivityEx parentActivity = (PhotographerActivityEx) context;
			parentActivity.getPagecontrol().setCount(services.size());
		}
	}

	// 初始化view
	private void inittempView(View tempView, XItem xItem) {
		ImageView item_simple_imageV = (ImageView) tempView
				.findViewById(R.id.item_simple_imageV);
		TextView item_title_textV = (TextView) tempView
				.findViewById(R.id.item_title_textV);
		TextView item_service_time_textV = (TextView) tempView
				.findViewById(R.id.item_service_time_textV);
		TextView item_num_photos_textV = (TextView) tempView
				.findViewById(R.id.item_num_photos_textV);
		TextView item_price_textV = (TextView) tempView.findViewById(R.id.item_price_textV);
		

		// 初始化tempView
		ImageLoader.getInstance().displayImage(xItem.getImageUrl(),
				item_simple_imageV, ImageLoaderUtil.options,
				animateFirstListener);
		item_title_textV.setText(xItem.getTitle() + "");
		item_service_time_textV.setText(xItem.getDaysPerOrder() + "");
		item_num_photos_textV.setText(xItem.getNumTakenPhotos() + "");
		item_price_textV.setText(xItem.getPrice()+"");
	}

	class ViewHolder {
		public ImageView item_simple_imageV;
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

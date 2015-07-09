package com.appointphoto.adapter;

import java.util.ArrayList;

import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.MyURI;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PhotoViewPagerAdapter extends PagerAdapter {

	ArrayList<ImageView> views = new ArrayList<ImageView>();
	Context context;
	private ImageLoadingListener animateFirstListener;

	public PhotoViewPagerAdapter(Context context) {
		this.context = context;
		animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener(
				context);
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		PhotoView photoView = new PhotoView(container.getContext());

		// Now just add PhotoView to ViewPager and return it
		container.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		
		ImageLoader.getInstance().displayImage(MyURI.testPhotoBroURI,
				photoView, ImageLoaderUtil.options, animateFirstListener);
		return photoView;
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}

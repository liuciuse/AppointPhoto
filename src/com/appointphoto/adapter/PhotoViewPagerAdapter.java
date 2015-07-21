package com.appointphoto.adapter;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.model.Work;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class PhotoViewPagerAdapter extends PagerAdapter {

	// 当前进入摄影师空间，摄影师作品
	private MyWorkList myworklist;
	Context context;
	private ImageLoadingListener animateFirstListener;

	public PhotoViewPagerAdapter(Context context,MyWorkList myworklist) {
		this.myworklist = myworklist;
		this.context = context;
		animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener(
				context);
	}

	@Override
	public int getCount() {
		return myworklist.getWorkList().size();
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		PhotoView photoView = new PhotoView(container.getContext());

		// Now just add PhotoView to ViewPager and return it
		container.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		Work tempWork = myworklist.getWorkList().get(position);
		ImageLoader.getInstance().displayImage(tempWork.getImageBaseUrl(),
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

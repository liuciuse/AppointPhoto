package com.appointphoto.activity.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.appointphoto.adapter.WorkAdapter;
import com.appointphoto.adapter.WorkAdapter.ViewHolder;
import com.appointphoto.nouse.PhotographerActivity;
import com.example.appointphoto.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageLoaderUtil {

	// º‡Ã˝Õº∆¨º”‘ÿ
	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {
		Context context;

		public AnimateFirstDisplayListener(Context context) {
			this.context = context;
		}

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.home_photographer_work_default_icon)
			.showImageForEmptyUri(
					R.drawable.home_photographer_work_default_icon)
			.showImageOnFail(R.drawable.home_photographer_work_default_icon)
			.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(2)).build();;

}

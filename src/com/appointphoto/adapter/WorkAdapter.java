package com.appointphoto.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.activity.util.Util;
import com.appointphoto.model.Work;
import com.example.appointphoto.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

//摄影师空间数据
public class WorkAdapter extends BaseAdapter {
	private Context mContext;
	// 当前进入摄影师空间，摄影师作品
	private MyWorkList myworklist;
	private LayoutInflater inflater;
	private ImageLoadingListener animateFirstListener;

	public WorkAdapter(Context paramContext,MyWorkList mywork) {
		this.mContext = paramContext;
		myworklist = mywork;
		animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener(
				mContext);
		inflater = LayoutInflater.from(mContext);
		
	}

	@Override
	public int getCount() {
		return this.myworklist.getWorkList().size();
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}


	public LayoutInflater getInflater() {
		return inflater;
	}

	public void setInflater(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_work_item, parent,
					false);
			holder = new ViewHolder();
			// 得到各控件的对象
			holder.desTextView = (TextView) convertView
					.findViewById(R.id.work_description_textView);
			holder.numLikeTextView = (TextView) convertView
					.findViewById(R.id.work_num_like_textV);
			holder.numReviewTextView = (TextView) convertView
					.findViewById(R.id.work_num_review_textV);
			holder.tagTxtView = (TextView) convertView
					.findViewById(R.id.work_tag_textView);
			holder.workImageView = (ImageView) convertView
					.findViewById(R.id.work_image_view);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();// 获得暂存的引用
		}
		Work tempWrok = myworklist.getWorkList().get(position);
		// 设置数据
		holder.desTextView.setText(tempWrok.getDescription());
		holder.numLikeTextView.setText(tempWrok.getNumLikes() + "");
		holder.numReviewTextView.setText(tempWrok.getNumReviews() + "");
		// 标签
		String tag = "";
		for (String temptag : tempWrok.getLabels()) {
			tag = tag + temptag + " ";
		}
		holder.tagTxtView.setText(tag);
		// 修改图像的大小
		LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) holder.workImageView
				.getLayoutParams();
		String imgwidth = tempWrok.getImgwidth();// 图像宽度
		String imgHeight = tempWrok.getImgheight();// 图像高度
		float f = Float.parseFloat(imgwidth)
				/ (Util.getDeviceWidth((Activity) this.mContext) - 2 * this.mContext
						.getResources().getDimensionPixelSize(
								R.dimen.general_space));
		int m = (int) (Float.parseFloat(imgHeight) / f);// 图像高度
		localLayoutParams.height = m;
		holder.workImageView.setLayoutParams(localLayoutParams);
		ImageLoader.getInstance().displayImage(tempWrok.getImageBaseUrl(),
				holder.workImageView, ImageLoaderUtil.options,
				animateFirstListener);
		return convertView;
	}

	public static class ViewHolder {
		TextView desTextView;// 作品描述
		TextView numLikeTextView;// 作品被几个用户喜欢
		TextView numReviewTextView;// 作品评论数
		TextView tagTxtView;// 作品标签
		ImageView workImageView;// 作品图片

		ViewHolder() {
		}
	}
}

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

//��Ӱʦ�ռ�����
public class WorkAdapter extends BaseAdapter {
	private Context mContext;
	// ��ǰ������Ӱʦ�ռ䣬��Ӱʦ��Ʒ
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
			// �õ����ؼ��Ķ���
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
			holder = (ViewHolder) convertView.getTag();// ����ݴ������
		}
		Work tempWrok = myworklist.getWorkList().get(position);
		// ��������
		holder.desTextView.setText(tempWrok.getDescription());
		holder.numLikeTextView.setText(tempWrok.getNumLikes() + "");
		holder.numReviewTextView.setText(tempWrok.getNumReviews() + "");
		// ��ǩ
		String tag = "";
		for (String temptag : tempWrok.getLabels()) {
			tag = tag + temptag + " ";
		}
		holder.tagTxtView.setText(tag);
		// �޸�ͼ��Ĵ�С
		LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) holder.workImageView
				.getLayoutParams();
		String imgwidth = tempWrok.getImgwidth();// ͼ����
		String imgHeight = tempWrok.getImgheight();// ͼ��߶�
		float f = Float.parseFloat(imgwidth)
				/ (Util.getDeviceWidth((Activity) this.mContext) - 2 * this.mContext
						.getResources().getDimensionPixelSize(
								R.dimen.general_space));
		int m = (int) (Float.parseFloat(imgHeight) / f);// ͼ��߶�
		localLayoutParams.height = m;
		holder.workImageView.setLayoutParams(localLayoutParams);
		ImageLoader.getInstance().displayImage(tempWrok.getImageBaseUrl(),
				holder.workImageView, ImageLoaderUtil.options,
				animateFirstListener);
		return convertView;
	}

	public static class ViewHolder {
		TextView desTextView;// ��Ʒ����
		TextView numLikeTextView;// ��Ʒ�������û�ϲ��
		TextView numReviewTextView;// ��Ʒ������
		TextView tagTxtView;// ��Ʒ��ǩ
		ImageView workImageView;// ��ƷͼƬ

		ViewHolder() {
		}
	}
}

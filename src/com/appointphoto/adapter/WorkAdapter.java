package com.appointphoto.adapter;

import java.util.ArrayList;
import java.util.List;

import com.appointphoto.model.Work;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkAdapter extends BaseAdapter {
	private Context mContext;
	// 当前进入摄影师空间，摄影师作品
	private List<Work> workList = new ArrayList<Work>();

	public WorkAdapter(Context paramContext) {
		this.mContext = paramContext;
	}

	@Override
	public int getCount() {
		return this.workList.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder localViewHolder;
		Work localWork;
		return null;
	}

	private class ViewHolder {
		TextView desTextView;//作品描述
		TextView numLikeTextView;//作品被几个用户喜欢
		TextView numReviewTextView;//作品评论数
		TextView tagTxtView;//作品标签
		ImageView workImageView;//作品图片

		private ViewHolder() {
		}
	}

}

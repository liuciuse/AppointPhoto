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
	// ��ǰ������Ӱʦ�ռ䣬��Ӱʦ��Ʒ
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
		TextView desTextView;//��Ʒ����
		TextView numLikeTextView;//��Ʒ�������û�ϲ��
		TextView numReviewTextView;//��Ʒ������
		TextView tagTxtView;//��Ʒ��ǩ
		ImageView workImageView;//��ƷͼƬ

		private ViewHolder() {
		}
	}

}

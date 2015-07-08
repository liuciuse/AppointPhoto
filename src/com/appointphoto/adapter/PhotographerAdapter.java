package com.appointphoto.adapter;

import java.util.ArrayList;
import java.util.List;

import com.appointphoto.activity.util.ImageLoaderUtil;
import com.appointphoto.model.Photographer;
import com.example.appointphoto.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotographerAdapter extends BaseAdapter {

	private Context context;
	private ImageLoadingListener animateFirstListener = new ImageLoaderUtil.AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	// 摄影师集合
	private List<Photographer> photographers = new ArrayList<Photographer>();
	LayoutInflater inflater ;

	public PhotographerAdapter(Context context) {
		this.context = context;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(
						R.drawable.home_photographer_work_default_icon)
				.showImageForEmptyUri(
						R.drawable.home_photographer_work_default_icon)
				.showImageOnFail(R.drawable.home_photographer_work_default_icon)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(2)).build();

		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return photographers.size();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ImageLoadingListener getAnimateFirstListener() {
		return animateFirstListener;
	}

	public void setAnimateFirstListener(ImageLoadingListener animateFirstListener) {
		this.animateFirstListener = animateFirstListener;
	}

	public DisplayImageOptions getOptions() {
		return options;
	}

	public void setOptions(DisplayImageOptions options) {
		this.options = options;
	}

	public List<Photographer> getPhotographers() {
		return photographers;
	}

	public void setPhotographers(List<Photographer> photographers) {
		this.photographers = photographers;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	public void setInflater(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_photographer_item,
					container, false);
			holder = new ViewHolder();
			// 得到各控件的对象
			holder.avatarImgView = ((ImageView) convertView
					.findViewById(R.id.user_avatar_image_view));
			holder.userNameTxtView = ((TextView) convertView
					.findViewById(R.id.user_nickname_text_view));
			holder.desTextView = ((TextView) convertView
					.findViewById(R.id.user_description_text_view));
			holder.priceTxtView = ((TextView) convertView
					.findViewById(R.id.price_text_view));
			holder.priceUnitTxtView = ((TextView) convertView
					.findViewById(R.id.price_unit_text_view));
			holder.tagTxtView = ((TextView) convertView
					.findViewById(R.id.photographer_tags_text_view));
			holder.workImageView1 = ((ImageView) convertView
					.findViewById(R.id.work_image_view_1));
			holder.workImageView2 = ((ImageView) convertView
					.findViewById(R.id.work_image_view_2));
			holder.numWorksTxtView = ((TextView) convertView
					.findViewById(R.id.home_num_work_textV));
			holder.numLikesTxtView = ((TextView) convertView
					.findViewById(R.id.home_num_like_textV));
			holder.numReviewsTxtView = ((TextView) convertView
					.findViewById(R.id.home_num_review_textV));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();// 获得暂存的引用
		}
		// 设置数据
		Photographer pg = photographers.get(position);
		ImageLoader.getInstance().displayImage(pg.getAvatar(),
				holder.avatarImgView, options, animateFirstListener);
		holder.numLikesTxtView.setText(pg.getNumLikes() + "");
		holder.numReviewsTxtView.setText(pg.getNumReviews() + "");
		holder.numWorksTxtView.setText(pg.getNumWorks() + "");
		holder.priceTxtView.setText(pg.getPrice() + "");
		holder.priceUnitTxtView.setText(pg.getPriceUnit() + "");
		holder.userNameTxtView.setText(pg.getNickname() + "");
		String tagTxt = "";
		for (String str : pg.getLabelList()) {
			tagTxt = tagTxt + str + " ";
		}
		holder.tagTxtView.setText(tagTxt);
		ImageLoader.getInstance().displayImage(
				pg.getWorkList().get(0).getImageBaseUrl(),
				holder.workImageView1, options, animateFirstListener);
		ImageLoader.getInstance().displayImage(
				pg.getWorkList().get(1).getImageBaseUrl(),
				holder.workImageView2, options, animateFirstListener);
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	// 暂存item view中的控件引用
	public class ViewHolder {
		ImageView avatarImgView;
		TextView desTextView;
		TextView numLikesTxtView;
		TextView numReviewsTxtView;
		TextView numWorksTxtView;
		TextView priceTxtView;
		TextView priceUnitTxtView;
		TextView tagTxtView;
		TextView userNameTxtView;
		ImageView workImageView1;
		ImageView workImageView2;

		private ViewHolder() {
		}
	}

}

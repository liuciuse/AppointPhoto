package com.appointphoto.widget;

import com.example.appointphoto.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageControl extends LinearLayout {
	private Context context;
	private int count = 0;
	private int foucsed_icon_id = R.drawable.pagecontro_icon_foucsed;
	private int nomal_icon_id = R.drawable.pagecontro_icon_normal;
	private int selected = 0;

	public PageControl(Context paramContext) {
		super(paramContext);
		this.context = paramContext;
	}

	public PageControl(Context paramContext, int paramInt) {
		super(paramContext);
		this.count = paramInt;
		this.context = paramContext;
		generateView();
	}

	public PageControl(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.context = paramContext;
		generateView();
	}

	private void generateView() {
		removeAllViews();
		LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
				this.context.getResources().getDimensionPixelSize(
						R.dimen.home_banner_pagecontrol_shape), this.context
						.getResources().getDimensionPixelSize(
								R.dimen.home_banner_pagecontrol_shape));
		localLayoutParams.setMargins(10, 0, 0, 10);
		int i = 0;
		while (true) {
			if (i >= this.count)
				return;
			ImageView localImageView = new ImageView(this.context);
			if (i == this.selected)
				localImageView.setImageResource(this.foucsed_icon_id);
			else
				localImageView.setImageResource(this.nomal_icon_id);
			addView(localImageView, localLayoutParams);
			i++;
		}
	}

	public void setCount(int paramInt) {
		this.count = paramInt;
		generateView();
	}

	public void setIconId(int paramInt1, int paramInt2) {
		this.nomal_icon_id = paramInt1;
		this.foucsed_icon_id = paramInt2;
	}

	public void setSelected(int paramInt) {
		this.selected = paramInt;
		generateView();
	}
}

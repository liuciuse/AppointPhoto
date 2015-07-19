package com.appointphoto.activity;

import com.example.appointphoto.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * User: special Date: 13-12-22 Time: 涓嬪崍1:31 Mail: specialcyci@gmail.com
 */
public class MineFragment extends Fragment {
	Button uploadimgs;
	View parent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parent = inflater.inflate(R.layout.my_layout, container, false);
		inititems(parent);
		return parent;
	}

	// 初始化
	private void inititems(View parent) {
		uploadimgs = (Button) parent.findViewById(
				R.id.item_uploadwork_btn);
		uploadimgs.setOnClickListener(new MyViewOnClickListener());
	}

	class MyViewOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//拍照上传
			case R.id.item_uploadwork_btn: {
				Intent intent = new Intent(getActivity(),
						TakePhotoActivity.class);
				getActivity().startActivity(intent);
			}
				break;

			default:
				break;
			}

		}

	}

}

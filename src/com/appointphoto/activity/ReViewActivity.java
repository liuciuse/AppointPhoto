package com.appointphoto.activity;

import com.appointphoto.activity.ImageBrowActivity.MyHandler;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReViewActivity extends Activity {
	
	private Button left_btn;
	private Button right_btn;
	private TextView title_text_view;
	private EditText add_review_editText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.add_review);
		initItems();
	}
	
	//初始化
	private void initItems() {
		left_btn = (Button) findViewById(R.id.left_btn);
		left_btn.setOnClickListener(new MyOnclick());
		right_btn = (Button) findViewById(R.id.right_btn);
		right_btn.setText("完成");
		right_btn.setOnClickListener(new MyOnclick());
		title_text_view = (TextView) findViewById(R.id.title_text_view);
		title_text_view.setText("填写评论");
		add_review_editText = (EditText) findViewById(R.id.add_review_editText);
	}
	
	class MyOnclick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.right_btn:
				uploadReview();
				break;
			case R.id.left_btn:
				turnback();
				break;

			default:
				break;
			}
		}
		
	}

	//提交评论
	public void uploadReview() {
		String content = add_review_editText.getText().toString();
		
	}

	//返回
	public void turnback() {
		MyService.finishActivity(this);
	}
}

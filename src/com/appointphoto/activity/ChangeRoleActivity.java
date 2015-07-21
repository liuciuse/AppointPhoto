package com.appointphoto.activity;

import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ChangeRoleActivity extends Activity {
	
	View item_model;//模特
	View item_pg;//摄影师
	View item_dresser;//化妆师
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.changerole);
		inititems();

	}

	//初始化界面
	private void inititems() {
		item_model =  findViewById(R.id.item_model);
		item_model.setOnClickListener(new MyViewOnClick());
		item_pg =  findViewById(R.id.item_pg);
		item_pg.setOnClickListener(new MyViewOnClick());
		item_dresser =  findViewById(R.id.item_dresser);
		item_dresser.setOnClickListener(new MyViewOnClick());
	}
	
	class MyViewOnClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.item_model:
				
				break;
			case R.id.item_pg:
				ChangeRoleActivity.this.startActivity(new Intent(ChangeRoleActivity.this, LoginActivity.class));
				break;
			case R.id.item_dresser:
				
				break;

			default:
				break;
			}
		}
		
	}
}

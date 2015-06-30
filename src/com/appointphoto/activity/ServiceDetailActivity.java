package com.appointphoto.activity;

import com.example.appointphoto.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ServiceDetailActivity extends Activity{
	
	private Button order_btn;//Ô¤Ô¼°´Å¥
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //ÒÆ³ýActionBar
        setContentView(R.layout.item_detail);
        initItems();
    }

	private void initItems() {
		order_btn = (Button) findViewById(R.id.item_order_btn);
		order_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ServiceDetailActivity.this.startActivity(new Intent(ServiceDetailActivity.this, BookActivity.class));
			}
		});
	}
    
    
    

}

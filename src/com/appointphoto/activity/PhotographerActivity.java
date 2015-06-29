package com.appointphoto.activity;

import com.example.appointphoto.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PhotographerActivity extends Activity {
	private Button navibackbtn;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE); //ÒÆ³ýActionBar
	        setContentView(R.layout.photographer_main);
	        
	        init();
	    }

	private void init() {
		navibackbtn = (Button) findViewById(R.id.title_bar_left_menu);
		navibackbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotographerActivity.this.finish();
			}
		});
		
		
	}
	 
	 
	 
	 
	 
}

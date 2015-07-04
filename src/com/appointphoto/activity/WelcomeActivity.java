package com.appointphoto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

public class WelcomeActivity extends Activity {
//ע��
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	startService(new Intent(this, MyService.class));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //�Ƴ�ActionBar
        //����ȫ������
        int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //��õ�ǰ�������
        Window window=WelcomeActivity.this.getWindow();
        //���õ�ǰ����Ϊȫ����ʾ
        window.setFlags(flag, flag);
        setContentView(R.layout.welcome);
        new Handler().postDelayed(new Runnable(){  
            @Override
			public void run() {  
            //execute the task
            	Intent mIntent = new Intent(WelcomeActivity.this,MainActivity.class);  
                startActivity(mIntent);
                WelcomeActivity.this.finish();
            }  
         }, 1000);  
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  

}

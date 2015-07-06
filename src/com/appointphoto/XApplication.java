package com.appointphoto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.appointphoto.model.Photographer;
import com.appointphoto.model.User;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Intent;

//全局的
public class XApplication extends Application {
	//用户
	private User user = new User();
	//摄影师集合
	private List<Photographer> photographers = new ArrayList<Photographer>();

	@Override
	public void onCreate() {
		super.onCreate();
		makeconfig();
	}

	private void makeconfig() {
		//配置ImageLoader
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()  
        .cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(  
                this).defaultDisplayImageOptions(defaultOptions)  
                .threadPriority(Thread.NORM_PRIORITY - 2)  
                .denyCacheImageMultipleSizesInMemory()  
                .discCacheFileNameGenerator(new Md5FileNameGenerator())  
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();  
        ImageLoader.getInstance().init(config); 
	}

	public List<Photographer> getPhotographers() {
		return photographers;
	}

	public void setPhotographers(List<Photographer> photographers) {
		this.photographers = photographers;
	}

	// 应用终止时，关闭服务
	public void onTerminate() {
		stopService(new Intent("MyService.class"));
		super.onTerminate();
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User paramUser) {
		this.user = paramUser;
	}
}

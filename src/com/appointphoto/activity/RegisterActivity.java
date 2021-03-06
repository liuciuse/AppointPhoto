package com.appointphoto.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.appointphoto.activity.util.HeadViewUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.activity.util.Util;
import com.appointphoto.activity.util.fileupload.FormFile;
import com.appointphoto.activity.util.fileupload.SocketHttpRequester;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

public class RegisterActivity extends Activity {
	private LayoutInflater inflater;
	private Button genderbtn;// 设置性别
	private ImageView photoImageV;// 个人认证图片
	private ImageView workImageV1;// 个人作品
	private ImageView workImageV2;
	private ImageView workImageV3;
	private TextView title_text_view;// 导航条标题
	private Button finishbtn;// 导航条完成
	private Bitmap conformBitmap;// 个人认证图片
	private EditText contactEditText;
	private EditText infoEditText;
	private EditText inviteEditText;
	private Button locationBtn;
	private EditText nameEditText;
	// private Bitmap workBitmap1;
	// private Bitmap workBitmap2;
	// private Bitmap workBitmap3;
	private String filePath0;
	private String filePath1;
	private String filePath2;

	PopupWindow popupWindow;
	ProgressDialog mypDialog;
	Handler mHandler;

	// 注册信息持久化
	SharedPreferences reginster_info;
	JSONObject registerJson = new JSONObject();// 注册

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 移除ActionBar
		setContentView(R.layout.register);
		reginster_info = getSharedPreferences(
				getResources().getString(R.string.reginster_pref),
				Context.MODE_PRIVATE);
		inflater = LayoutInflater.from(this);
		initItems();
		mHandler = new MyHandler();
	}

	private void initItems() {

		HeadViewUtil.back(this);
		// 选择性别
		genderbtn = (Button) findViewById(R.id.gender_btn);
		genderbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changeSex(view);
			}
		});
		// 身份验证
		photoImageV = (ImageView) findViewById(R.id.upload_photo_imageV);
		photoImageV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				RegisterActivity.this.takePersonInfo();
			}
		});
		// 上次个人作品
		workImageV1 = (ImageView) findViewById(R.id.upload_work_imageV1);
		workImageV1.setOnClickListener(new MyPhotoOnClickListener());
		workImageV2 = (ImageView) findViewById(R.id.upload_work_imageV2);
		workImageV2.setOnClickListener(new MyPhotoOnClickListener());
		workImageV3 = (ImageView) findViewById(R.id.upload_work_imageV3);
		workImageV3.setOnClickListener(new MyPhotoOnClickListener());

		// 设置导航条
		title_text_view = (TextView) findViewById(R.id.title_text_view);
		title_text_view.setText("注册成为摄影师");
		finishbtn = (Button) findViewById(R.id.right_btn);
		finishbtn.setText("完成");
		// 上传信息到服务器，进行注册
		finishbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				register();// 注册
			}

		});
		locationBtn = (Button) findViewById(R.id.loaction_btn);
		nameEditText = (EditText) findViewById(R.id.name_editText);
		contactEditText = (EditText) findViewById(R.id.contact_editText);
		infoEditText = (EditText) findViewById(R.id.info_editText);
		inviteEditText = (EditText) findViewById(R.id.invite_editText);
		// 网络请求时显示进度
		initDialog();
	}

	// 注册对话框
	private void initDialog() {
		mypDialog = new ProgressDialog(this);
		// 设置进度条风格，风格为圆形，旋转的
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置ProgressDialog 标题
		mypDialog.setTitle("注册");
		mypDialog.setMessage("正在注册...");
		// 设置ProgressDialog 的进度条是否不明确
		mypDialog.setIndeterminate(false);
		// 设置ProgressDialog 是否可以按退回按键取消
		mypDialog.setCancelable(false);
	}

	// 注册
	private void register() {
		// if (this.nameEditText.getText().toString().replace(" ", "").length()
		// <= 0) {
		// Util.showShortToast(getApplicationContext(), "姓名不能为空！");
		// return;
		// }
		// if (this.genderbtn.getText().equals("性别")) {
		// Util.showShortToast(getApplicationContext(), "请选择性别！");
		// return;
		// }
		// // if (this.locationBtn.getText().equals("所在地"))
		// // {
		// // Util.showShortToast(getApplicationContext(), "请选择所在地！");
		// // return;
		// // }
		// if (this.contactEditText.getText().toString().replace(" ",
		// "").length() <= 0) {
		// Util.showShortToast(getApplicationContext(), "联系电话不能为空！");
		// return;
		// }
		// if (!Util.isValidMobilePhoneNum(this.contactEditText.getText()
		// .toString())) {
		// Util.showShortToast(getApplicationContext(), "联系电话格式不对！");
		// return;
		// }
		// if (this.conformBitmap == null) {
		// Util.showShortToast(getApplicationContext(), "认证照片不能为空！");
		// return;
		// }
		// if ((this.workBitmap1 == null) || (this.workBitmap2 == null)
		// || (this.workBitmap3 == null)) {
		// Util.showShortToast(getApplicationContext(), "必须上传三幅作品！");
		// return;
		// }
		// if (!Util.isNetWorkAvailable(this)) {
		// Util.showShortToast(this, "当前网络不能用！");
		// return;
		// }
		// 注册

		new RegisterRequest().execute();
		mypDialog.show();
	}

	// 拍摄个人身份证信息
	private void takePersonInfo() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}

	// 选择性别，用弹出窗口显示
	private void changeSex(View view) {

		// 一个自定义的布局，作为显示的内容
		View contentView = inflater.inflate(R.layout.sexchoosen, null);
		// 选择性别
		Button man_textV = (Button) contentView.findViewById(R.id.man_textV);
		man_textV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genderbtn.setText("男");
				popupWindow.dismiss();
			}
		});
		Button woman_textV = (Button) contentView
				.findViewById(R.id.woman_textV);
		woman_textV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genderbtn.setText("女");
				popupWindow.dismiss();
			}
		});
		popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.dialog_bg_normal));
		// 设置好参数之后再show
		popupWindow.showAsDropDown(view, 100, 0);
	}

	// 获取文件名
	private String getFileName(String filePath) {
		int lastIndex = filePath.lastIndexOf("/");
		String filename = filePath.substring(lastIndex + 1, filePath.length());
		return filename;
	}

	// 从Uri获取文件路径
	private String getFilePath(Uri uri) {
		// 获取图片路径
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		return path;
	}

	// 选择图片后
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		if (resultCode == Activity.RESULT_OK) {
			Uri oriUri = data.getData();
			switch (requestCode) {
			case 1:// 获取身份信息
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				photoImageV.setImageBitmap(bitmap);// 将图片显示在ImageView里
				conformBitmap = bitmap;
				break;
			case 11:// 获取所拍摄过作品
				// 获取图片
				// Bitmap bm = MediaStore.Images.Media.getBitmap(
				// getContentResolver(), oriUri);
				workImageV1.setImageURI(oriUri);
				// 获取图片路径
				filePath0 = getFilePath(oriUri);
				break;
			case 12:// 获取所拍摄过作品
				workImageV2.setImageURI(oriUri);
				// 获取图片路径
				filePath1 = getFilePath(oriUri);
				break;
			case 13:// 获取所拍摄过作品
				workImageV3.setImageURI(oriUri);
				// 获取图片路径
				filePath2 = getFilePath(oriUri);
				break;

			default:
				break;
			}

		}
	}

	// 上传个人作品
	public class MyPhotoOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			/* 开启Pictures画面Type设定为image */
			intent.setType("image/*");
			/* 使用Intent.ACTION_GET_CONTENT这个Action */
			intent.setAction(Intent.ACTION_GET_CONTENT);
			int vid = v.getId();
			switch (vid) {
			case R.id.upload_work_imageV1:
				/* 取得相片后返回本画面 */
				startActivityForResult(intent, 11);
				break;
			case R.id.upload_work_imageV2:
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 12);
				break;
			case R.id.upload_work_imageV3:
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 13);
				break;

			default:
				break;
			}
		}

	}

	// 注册请求
	@SuppressLint("NewApi")
	private class RegisterRequest extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			int statusCode[] = new int[1];
			try {
				// 发送注册请求
				String requestUrl = MyURI.registerURI;
				String fileName1 = getFileName(filePath0);
				String fileName2 = getFileName(filePath1);
				String fileName3 = getFileName(filePath2);

				// 请求普通信息
				Map<String, String> params2 = new HashMap<String, String>();
				params2.put("name", "张三");
				params2.put("fileName1", fileName1);
				params2.put("fileName2", fileName2);
				FormFile formfile1 = new FormFile(fileName1,
						new File(filePath0), fileName1,
						"application/octet-stream");
				FormFile formfile2 = new FormFile(fileName2,
						new File(filePath1), fileName2,
						"application/octet-stream");
				FormFile[] formfiles = new FormFile[2];
				formfiles[0] = formfile1;
				formfiles[1] = formfile2;

				try {
					SocketHttpRequester.post(requestUrl, params2, formfiles);
				} catch (Exception e) {
					e.printStackTrace();
					this.cancel(true);
				}
			} catch (Exception e) {
				this.cancel(true);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled(Void result) {
			if (!Util.isNetWorkAvailable(RegisterActivity.this)) {
				Util.showShortToast(RegisterActivity.this, "网络未连接");
			}
			mypDialog.cancel();
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mypDialog.cancel();
		}
	}

	/*// 设置图片时，有点慢
	class LoadPic implements Runnable {
		Intent data;
		int what;

		public LoadPic(Intent data, int what) {
			this.data = data;
			this.what = what;
		}

		@Override
		public void run() {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			ContentResolver cr = RegisterActivity.this.getContentResolver();
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

				// 将bitmap转化为base编码保存到jsonstr中
				// String jsonStr = Util.bitmapToBase64(bitmap);
				// try {
				// registerJson.put("image1", jsonStr);
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }

				Message message = new Message();
				Bundle data = new Bundle();
				data.putParcelable("workBitmap", bitmap);
				message.setData(data);
				message.what = this.what;// 图片
				mHandler.sendMessage(message);

			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}

		}

	}
*/
	// 主线程处理
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法，接受数据
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			Bitmap bitmap = data.getParcelable("workBitmap");
			switch (msg.what) {
			case 1111:

				break;

			default:
				break;
			}

		}
	}

	@Deprecated
	// bitmap转换为Json后回调，此类没有使用
	class GetJson implements Runnable {

		String jsonName;
		Bitmap bitmap;
		int callId;

		public GetJson(String jsonName, Bitmap bitmap, int callId) {
			this.jsonName = jsonName;
			this.bitmap = bitmap;
			this.callId = callId;
		}

		@Override
		public void run() {
			String jsonStr = Util.bitmapToBase64(bitmap);
			try {
				registerJson.put(jsonName, jsonStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.what = callId;
			mHandler.sendMessage(msg);

		}

	}

}

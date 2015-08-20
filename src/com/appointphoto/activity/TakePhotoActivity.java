package com.appointphoto.activity;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appointphoto.R;

public class TakePhotoActivity extends Activity {
	Button uploadimgs;// �ϴ�ͼƬ
	Bitmap[] bitmaps = new Bitmap[6];// ͼƬ
	ImageView upload_work_imageV1;
	ImageView upload_work_imageV2;
	ImageView upload_work_imageV3;
	ImageView upload_work_imageV4;
	ImageView upload_work_imageV5;
	ImageView upload_work_imageV6;

	Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mytakephotos);

		inititems();
	}

	private void inititems() {
		uploadimgs = (Button) findViewById(R.id.right_btn);
		uploadimgs.setText("�ϴ�");
		uploadimgs.setOnClickListener(new MyOnClickListener());
		upload_work_imageV1 = (ImageView) findViewById(R.id.upload_work_imageV1);
		upload_work_imageV1.setOnClickListener(new MyOnClickListener());
		upload_work_imageV2 = (ImageView) findViewById(R.id.upload_work_imageV2);
		upload_work_imageV2.setOnClickListener(new MyOnClickListener());
		upload_work_imageV3 = (ImageView) findViewById(R.id.upload_work_imageV3);
		upload_work_imageV3.setOnClickListener(new MyOnClickListener());
		upload_work_imageV4 = (ImageView) findViewById(R.id.upload_work_imageV4);
		upload_work_imageV4.setOnClickListener(new MyOnClickListener());
		upload_work_imageV5 = (ImageView) findViewById(R.id.upload_work_imageV5);
		upload_work_imageV5.setOnClickListener(new MyOnClickListener());
		upload_work_imageV6 = (ImageView) findViewById(R.id.upload_work_imageV6);
		upload_work_imageV6.setOnClickListener(new MyOnClickListener());

		mHandler = new MyHandler();
	}

	// ����¼�
	class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			/* ����Pictures����Type�趨Ϊimage */
			intent.setType("image/*");
			/* ʹ��Intent.ACTION_GET_CONTENT���Action */
			intent.setAction(Intent.ACTION_GET_CONTENT);
			switch (v.getId()) {
			case R.id.upload_work_imageV1:
				/* ȡ����Ƭ�󷵻ر����� */
				startActivityForResult(intent, 11);
				break;
			case R.id.upload_work_imageV2:
				startActivityForResult(intent, 12);
				break;
			case R.id.upload_work_imageV3:
				startActivityForResult(intent, 13);
				break;
			case R.id.upload_work_imageV4:
				startActivityForResult(intent, 14);
				break;
			case R.id.upload_work_imageV5:
				startActivityForResult(intent, 15);
				break;
			case R.id.upload_work_imageV6:
				startActivityForResult(intent, 16);
				break;
			case R.id.right_btn:
				//�ύͼƬ��Ʒ
				break;

			default:
				break;
			}
		}

	}

	// ������պ�
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 11:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				// Uri uri = data.getData();
				// Log.e("uri", uri.toString());
				// ContentResolver cr = this.getContentResolver();
				// try {
				// Bitmap bitmap = BitmapFactory.decodeStream(cr
				// .openInputStream(uri));
				// workBitmap1 = bitmap;
				// workImageV1.setImageBitmap(bitmap);
				// } catch (FileNotFoundException e) {
				// Log.e("Exception", e.getMessage(), e);
				// }
				new Thread(new LoadPic(data, 2001)).start();
			}
			break;
		case 12:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				// Uri uri = data.getData();
				// Log.e("uri", uri.toString());
				// ContentResolver cr = this.getContentResolver();
				// try {
				// Bitmap bitmap = BitmapFactory.decodeStream(cr
				// .openInputStream(uri));
				// workBitmap2 = bitmap;
				// workImageV2.setImageBitmap(bitmap);
				// } catch (FileNotFoundException e) {
				// Log.e("Exception", e.getMessage(), e);
				// }
			}
			new Thread(new LoadPic(data, 2002)).start();
			break;
		case 13:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				// Uri uri = data.getData();
				// Log.e("uri", uri.toString());
				// ContentResolver cr = this.getContentResolver();
				// try {
				// Bitmap bitmap = BitmapFactory.decodeStream(cr
				// .openInputStream(uri));
				// workBitmap3 = bitmap;
				// workImageV3.setImageBitmap(bitmap);
				// } catch (FileNotFoundException e) {
				// Log.e("Exception", e.getMessage(), e);
				// }
				new Thread(new LoadPic(data, 2003)).start();
			}
			break;

		case 14:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				new Thread(new LoadPic(data, 2004)).start();
			}
			break;
		case 15:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				new Thread(new LoadPic(data, 2005)).start();
			}
			break;
		case 16:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				new Thread(new LoadPic(data, 2006)).start();
			}
			break;

		default:
			break;
		}

	}

	// ����ͼƬʱ���е���
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
			ContentResolver cr = TakePhotoActivity.this.getContentResolver();
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				Message message = new Message();
				Bundle data = new Bundle();
				data.putParcelable("workBitmap", bitmap);
				message.setData(data);
				message.what = this.what;// ͼƬ
				mHandler.sendMessage(message);

			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}

		}

	}

	// ���̴߳���
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// ���������д�˷�������������
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			Bitmap bitmap = data.getParcelable("workBitmap");
			switch (msg.what) {
			case 2001:
				bitmaps[0] = bitmap;
				upload_work_imageV1.setImageBitmap(bitmap);
				upload_work_imageV2.setVisibility(View.VISIBLE);
				break;
			case 2002:
				bitmaps[1] = bitmap;
				upload_work_imageV2.setImageBitmap(bitmap);
				upload_work_imageV3.setVisibility(View.VISIBLE);
				break;
			case 2003:
				bitmaps[2] = bitmap;
				upload_work_imageV3.setImageBitmap(bitmap);
				upload_work_imageV4.setVisibility(View.VISIBLE);
				break;
			case 2004:
				bitmaps[3] = bitmap;
				upload_work_imageV4.setImageBitmap(bitmap);
				upload_work_imageV5.setVisibility(View.VISIBLE);
				break;
			case 2005:
				bitmaps[4] = bitmap;
				upload_work_imageV5.setImageBitmap(bitmap);
				upload_work_imageV6.setVisibility(View.VISIBLE);
				break;
			case 2006:
				bitmaps[5] = bitmap;
				upload_work_imageV6.setImageBitmap(bitmap);
				break;

			default:
				break;
			}

		}
	}

}

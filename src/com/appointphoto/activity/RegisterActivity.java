package com.appointphoto.activity;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.appointphoto.activity.util.JsonUtil;
import com.appointphoto.activity.util.MyURI;
import com.appointphoto.activity.util.Util;
import com.appointphoto.activity.util.fileupload.FormFile;
import com.appointphoto.activity.util.fileupload.SocketHttpRequester;
import com.appointphoto.service.MyService;
import com.example.appointphoto.R;

public class RegisterActivity extends Activity {
	private LayoutInflater inflater;
	private Button genderbtn;// �����Ա�
	private ImageView photoImageV;// ������֤ͼƬ
	private ImageView workImageV1;// ������Ʒ
	private ImageView workImageV2;
	private ImageView workImageV3;
	private TextView title_text_view;// ����������
	private Button finishbtn;// ���������
	private Bitmap conformBitmap;// ������֤ͼƬ
	private EditText contactEditText;
	private EditText infoEditText;
	private EditText inviteEditText;
	private Button locationBtn;
	private EditText nameEditText;
	private Bitmap workBitmap1;
	private Bitmap workBitmap2;
	private Bitmap workBitmap3;
	PopupWindow popupWindow;
	ProgressDialog mypDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyService.allActivity.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �Ƴ�ActionBar
		setContentView(R.layout.register);
		inflater = LayoutInflater.from(this);
		initItems();
	}

	private void initItems() {
		// ѡ���Ա�
		genderbtn = (Button) findViewById(R.id.gender_btn);
		genderbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changeSex(view);
			}
		});
		// �����֤
		photoImageV = (ImageView) findViewById(R.id.upload_photo_imageV);
		photoImageV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				RegisterActivity.this.takePersonInfo();
			}
		});
		// �ϴθ�����Ʒ
		workImageV1 = (ImageView) findViewById(R.id.upload_work_imageV1);
		workImageV1.setOnClickListener(new MyPhotoOnClickListener());
		workImageV2 = (ImageView) findViewById(R.id.upload_work_imageV2);
		workImageV2.setOnClickListener(new MyPhotoOnClickListener());
		workImageV3 = (ImageView) findViewById(R.id.upload_work_imageV3);
		workImageV3.setOnClickListener(new MyPhotoOnClickListener());

		// ���õ�����
		title_text_view = (TextView) findViewById(R.id.title_text_view);
		title_text_view.setText("ע���Ϊ��Ӱʦ");
		finishbtn = (Button) findViewById(R.id.right_btn);
		finishbtn.setText("���");
		// �ϴ���Ϣ��������������ע��
		finishbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				register();// ע��
			}

		});
		locationBtn = (Button) findViewById(R.id.loaction_btn);
		nameEditText = (EditText) findViewById(R.id.name_editText);
		contactEditText = (EditText) findViewById(R.id.contact_editText);
		infoEditText = (EditText) findViewById(R.id.info_editText);
		inviteEditText = (EditText) findViewById(R.id.invite_editText);
		// ��������ʱ��ʾ����
		initDialog();
	}

	//ע��Ի���
	private void initDialog() {
		mypDialog = new ProgressDialog(this);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ����ProgressDialog ����
		mypDialog.setTitle("ע��");
		mypDialog.setMessage("����ע��...");
		// ����ProgressDialog �Ľ������Ƿ���ȷ
		mypDialog.setIndeterminate(false);
		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
		mypDialog.setCancelable(false);
	}

	// ע��
	private void register() {
		// if (this.nameEditText.getText().toString().replace(" ", "").length()
		// <= 0) {
		// Util.showShortToast(getApplicationContext(), "��������Ϊ�գ�");
		// return;
		// }
		// if (this.genderbtn.getText().equals("�Ա�")) {
		// Util.showShortToast(getApplicationContext(), "��ѡ���Ա�");
		// return;
		// }
		// // if (this.locationBtn.getText().equals("���ڵ�"))
		// // {
		// // Util.showShortToast(getApplicationContext(), "��ѡ�����ڵأ�");
		// // return;
		// // }
		// if (this.contactEditText.getText().toString().replace(" ",
		// "").length() <= 0) {
		// Util.showShortToast(getApplicationContext(), "��ϵ�绰����Ϊ�գ�");
		// return;
		// }
		// if (!Util.isValidMobilePhoneNum(this.contactEditText.getText()
		// .toString())) {
		// Util.showShortToast(getApplicationContext(), "��ϵ�绰��ʽ���ԣ�");
		// return;
		// }
		// if (this.conformBitmap == null) {
		// Util.showShortToast(getApplicationContext(), "��֤��Ƭ����Ϊ�գ�");
		// return;
		// }
		// if ((this.workBitmap1 == null) || (this.workBitmap2 == null)
		// || (this.workBitmap3 == null)) {
		// Util.showShortToast(getApplicationContext(), "�����ϴ�������Ʒ��");
		// return;
		// }
		// if (!Util.isNetWorkAvailable(this)) {
		// Util.showShortToast(this, "��ǰ���粻���ã�");
		// return;
		// }
		//ע��
		new RegisterRequest().execute();
		mypDialog.show();
	}

	// ����������֤��Ϣ
	private void takePersonInfo() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}

	// ѡ���Ա��õ���������ʾ
	private void changeSex(View view) {

		// һ���Զ���Ĳ��֣���Ϊ��ʾ������
		View contentView = inflater.inflate(R.layout.sexchoosen, null);
		// ѡ���Ա�
		Button man_textV = (Button) contentView.findViewById(R.id.man_textV);
		man_textV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genderbtn.setText("��");
				popupWindow.dismiss();
			}
		});
		Button woman_textV = (Button) contentView
				.findViewById(R.id.woman_textV);
		woman_textV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genderbtn.setText("Ů");
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
				// �����������true�Ļ���touch�¼���������
				// ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			}
		});
		// ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
		// �Ҿ���������API��һ��bug
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.dialog_bg_normal));
		// ���úò���֮����show
		popupWindow.showAsDropDown(view, 100, 0);
	}

	// ������պ�
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:// ��ȡ�����Ϣ
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
				photoImageV.setImageBitmap(bitmap);// ��ͼƬ��ʾ��ImageView��
				conformBitmap = bitmap;
			}
			break;
		case 11:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				Log.e("uri", uri.toString());
				ContentResolver cr = this.getContentResolver();
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(cr
							.openInputStream(uri));
					workBitmap1 = bitmap;
					workImageV1.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					Log.e("Exception", e.getMessage(), e);
				}
			}
			break;
		case 12:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				Log.e("uri", uri.toString());
				ContentResolver cr = this.getContentResolver();
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(cr
							.openInputStream(uri));
					workBitmap2 = bitmap;
					workImageV2.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					Log.e("Exception", e.getMessage(), e);
				}
			}
			break;
		case 13:// ��ȡ���������Ʒ
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				Log.e("uri", uri.toString());
				ContentResolver cr = this.getContentResolver();
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(cr
							.openInputStream(uri));
					workBitmap3 = bitmap;
					workImageV3.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					Log.e("Exception", e.getMessage(), e);
				}
			}
			break;

		default:
			break;
		}

	}

	// �ϴ�������Ʒ
	public class MyPhotoOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			/* ����Pictures����Type�趨Ϊimage */
			intent.setType("image/*");
			/* ʹ��Intent.ACTION_GET_CONTENT���Action */
			intent.setAction(Intent.ACTION_GET_CONTENT);
			int vid = v.getId();
			switch (vid) {
			case R.id.upload_work_imageV1:
				/* ȡ����Ƭ�󷵻ر����� */
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
	
	//ע������
	@SuppressLint("NewApi")
	private class RegisterRequest extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			int statusCode[] = new int[1];
			try {
				// ����ע������
				String requestUrl = MyURI.registerURI;
				// ������ͨ��Ϣ
				Map<String, String> params2 = new HashMap<String, String>();
				String fileName1 = "fileName1";
				String fileName2 = "fileName2";
				params2.put("name", "����");
				params2.put("fileName1", fileName1);
				params2.put("fileName2", fileName2);
				FormFile formfile1 = new FormFile(fileName1,
						Util.Bitmap2IS(workBitmap1), "image1",
						"application/octet-stream");
				FormFile formfile2 = new FormFile(fileName2,
						Util.Bitmap2IS(workBitmap2), "image2",
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
				Util.showShortToast(RegisterActivity.this, "����δ����");
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

}

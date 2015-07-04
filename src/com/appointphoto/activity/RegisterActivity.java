package com.appointphoto.activity;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import com.appointphoto.activity.util.Util;
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
				register();//ע��
			}

		});
		locationBtn = (Button) findViewById(R.id.loaction_btn);
		nameEditText = (EditText) findViewById(R.id.name_editText);
		contactEditText = (EditText) findViewById(R.id.contact_editText);
		infoEditText = (EditText) findViewById(R.id.info_editText);
		inviteEditText = (EditText) findViewById(R.id.invite_editText);
	}
	
	//ע��
	private void register() {
		if (this.nameEditText.getText().toString().replace(" ", "").length() <= 0)
	    {
	      Util.showShortToast(getApplicationContext(), "��������Ϊ�գ�");
	      return;
	    }
	    if (this.genderbtn.getText().equals("�Ա�"))
	    {
	      Util.showShortToast(getApplicationContext(), "��ѡ���Ա�");
	      return;
	    }
//	    if (this.locationBtn.getText().equals("���ڵ�"))
//	    {
//	      Util.showShortToast(getApplicationContext(), "��ѡ�����ڵأ�");
//	      return;
//	    }
	    if (this.contactEditText.getText().toString().replace(" ", "").length() <= 0)
	    {
	      Util.showShortToast(getApplicationContext(), "��ϵ�绰����Ϊ�գ�");
	      return;
	    }
	    if (!Util.isValidMobilePhoneNum(this.contactEditText.getText().toString()))
	    {
	      Util.showShortToast(getApplicationContext(), "��ϵ�绰��ʽ���ԣ�");
	      return;
	    }
	    if (this.conformBitmap == null)
	    {
	      Util.showShortToast(getApplicationContext(), "��֤��Ƭ����Ϊ�գ�");
	      return;
	    }
	    if ((this.workBitmap1 == null) || (this.workBitmap2 == null) || (this.workBitmap3 == null))
	    {
	      Util.showShortToast(getApplicationContext(), "�����ϴ�������Ʒ��");
	      return;
	    }
	    if (!Util.isNetWorkAvailable(this))
	    {
	      Util.showShortToast(this, "��ǰ���粻���ã�");
	      return;
	    }
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
		popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
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
		case 11:// ��ȡ���������ͼ��Ʒ
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
		case 12:// ��ȡ���������ͼ��Ʒ
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
		case 13:// ��ȡ���������ͼ��Ʒ
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

	// �ϴθ�����Ʒ
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

}

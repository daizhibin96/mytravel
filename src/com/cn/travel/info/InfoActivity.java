package com.cn.travel.info;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.greenrobot.eventbus.EventBus;

import com.cn.travel.bean.InfoBean;
import com.cn.travel.bean.UserBean;
import com.cn.travel.domain.BaseActivity;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.domain.MessageEvent;
import com.cn.travel.domain.MyActivityManager;
import com.cn.travel.domain.RegisterActivity;
import com.cn.travel.service.me.InfoService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;

public class InfoActivity extends BaseActivity {
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	private ImageView iv_personal_icon;// ͷ�� 1
	private SelectPicPopupWindow menuWindow;
	private Spinner spinner_IdType, spinnerNation;// ֤�����ͣ����� 2
	private ArrayAdapter adapter, adapter2;
	private ImageView backHome;
	private TextView info_address, address;
	private String province, city;
	private InfoBean infobean;
	private UserBean userBean = new UserBean();
	private UserBean u = new UserBean();
	private UserBean us = new UserBean();
	private EditText username, truename, card, password, confirmPassword;// �û�������ַ����������֤����,����
																			// 6
	private Spinner nation, cardtype;
	private RadioButton mail, femail;// ��Ů 1
	private TextView phone, commit; // �绰 id 1
	private String sex, ID;
	private String img_src;
	private String Imageid = null;
	private String imageShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_me_info);
		getLayoutId();
		Intent intent = getIntent();
		u.setId(getToken());
		if (intent.getStringExtra("aaa") != null) {
			String d = intent.getStringExtra("aaa");
			us = new Gson().fromJson(d, UserBean.class);

			setValue();
			// images/20181010.png--->20181010
			Log.e("abcd++++++", us.getUserimage());
			String str0 = us.getUserimage();
			// String str1="images/";
			// //String str2=".png";
			// String result1 = str0.replaceAll(str1, "");
			// String result2 = result1.replaceAll(str2, "");
			IDclass.UserName = us.getUsername();
			IDclass.iconImage = str0;
			Log.e("eftffdfdfaf_+++++", str0);

			SharedPreferences.Editor editor = getSharedPreferences("ROOT",
					Context.MODE_PRIVATE).edit();
			editor.putString("UserID", us.getId());
			editor.putString("UserName", us.getUsername());
			editor.putString("iconImage", str0);
			editor.commit();

			// EventBus.getDefault().post(new MessageEvent(IDclass.UserName));
			EventBus.getDefault()
					.post(new MessageEvent(IDclass.UserName, str0));
		} else {
			InfoService.base(u, this);
			finish();
		}

		// �½�һ�������洢��Ƭ���ļ���
		File destDir = new File(Environment.getExternalStorageDirectory()
				+ "/TravelPersonal_icon");
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		spinner_IdType = (Spinner) findViewById(R.id.spinnerIdType);
		spinnerNation = (Spinner) findViewById(R.id.spinner_nation);

		info_address = (TextView) findViewById(R.id.set_info_address);
		info_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog1();
			}
		});

		iv_personal_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ʵ����SelectPicPopupWindow
				menuWindow = new SelectPicPopupWindow(InfoActivity.this,
						itemsOnClick);
				// ��ʾ����
				menuWindow.showAtLocation(
						InfoActivity.this.findViewById(R.id.main),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
			}
		});

		backHome = (ImageView) findViewById(R.id.back_home);
		backHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ʵ����SelectPicPopupWindow
				// EventBus.getDefault().post(new
				// MessageEvent(username.getText().toString()));
				finish();
			}
		});

		// ֤�������б�
		adapter = ArrayAdapter.createFromResource(this, R.array.plants,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_IdType.setAdapter(adapter);
		spinner_IdType
				.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		spinner_IdType.setVisibility(View.VISIBLE);

		// ����
		adapter2 = ArrayAdapter.createFromResource(this, R.array.nations,
				android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerNation.setAdapter(adapter2);
		spinnerNation
				.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		spinnerNation.setVisibility(View.VISIBLE);

		// ��ȡ��һ�μ��е���Ƭ
		if (destDir.exists() && destDir.isDirectory()) {
			if (destDir.list().length > 0) {
				Log.d("111111111112", destDir.toString() + "/image_icon.png");
				Bitmap bitmap = BitmapFactory.decodeFile(destDir.toString()
						+ "/image_icon.png");
				// iv_personal_icon.setImageBitmap(bitmap);
			} else {
				iv_personal_icon
						.setBackgroundResource(R.drawable.default_personal_image);
			}
		}
		getLayoutId();

		commit = (TextView) findViewById(R.id.commit_info);
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ��ȡ�����Լ��ύ������
				// InfoBean infobean=new InfoBean();
				// ��ʼ������
				if ((password.getText().toString()).equals(confirmPassword
						.getText().toString())) {
					UpInfo();
					finish();

					// Intent intet_lg = new Intent();
					// intet_lg.setClass(InfoActivity.this,
					// LoginActivity.class);
					// startActivity(intet_lg);
					// finish();
				} else {
					Toast.makeText(InfoActivity.this, "���벻һ��",
							Toast.LENGTH_SHORT).show();
				}
			}

			private void UpInfo() {
				// TODO Auto-generated method stub
				// �ϴ�ͷ��
				// new Thread(new UpFile()).start();
				// �ϴ�������Ϣ
				String Pass = new String();
				Pass = password.getText().toString() + "";
				userBean.setPhone(phone.getText().toString() + "");
				userBean.setPassword(Pass);
				userBean.setId(getToken().toString() + "");
				userBean.setTruename(truename.getText().toString() + "");
				userBean.setUsername(username.getText().toString() + "");
				userBean.setAddress(address.getText().toString() + "");
				userBean.setCard(card.getText().toString() + "");
				userBean.setCardtype(cardtype.getSelectedItem().toString() + "");
				userBean.setNation(nation.getSelectedItem().toString() + "");
				userBean.setUserimage(us.getUserimage().toString() + "");
				// Toast.makeText(getBaseContext(),
				// us.getUserimage(),Toast.LENGTH_SHORT).show();
				if (mail.isChecked()) {
					sex = "��";
				} 
				if (femail.isChecked()){
					sex = "Ů";
				}
				userBean.setSex(sex);
				InfoService.sendInfo(userBean, InfoActivity.this);

			}
		});

	}

	private class UpFile implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String filePath = "/storage/emulated/0/TravelPersonal_icon/";
			filePath = filePath + Imageid + ".png";
			Log.e("�ϴ��ļ�·��....", filePath + "");
			ArrayList<String> array = new ArrayList<String>();
			array.add(filePath);
			// String str = new InfoService().upload(Tools.url_uploadMyImage,
			// array, InfoActivity.this.getBaseContext());
			// Toast.makeText(getBaseContext(),str, Toast.LENGTH_SHORT).show();
			InfoService.upload(Tools.url_uploadMyImage, array,
					InfoActivity.this.getBaseContext());
			String str = "images/" + Imageid + ".png";
			us.setUserimage(str);
		}

	}

	private void setValue() {
		// TODO Auto-generated method stub
		if (!us.getUsername().equals("")) {
			username.setText(us.getUsername());// ����
		}
		if (!us.getAddress().equals("")) {
			address.setText(us.getAddress());// ��ַ
		}
		nation.setSelection(4, true); // ����
		cardtype.setSelection(4, true);// ֤������
		if (!us.getCard().equals("0")) {
			card.setText(us.getCard());
		}
		if (!us.getTruename().equals("0")) {
			truename.setText(us.getTruename());// ��ʵ����
		}
		if (!us.getSex().equals("")) {
			if (us.getSex().equals("��")) {
				mail.setChecked(true);
			} else {
				femail.setChecked(true);
			}
		}
		phone.setText(us.getPhone());// �ֻ���
		// Id.setText(getToken());
		String ImPath = Tools.baseUrl + us.getUserimage();
		Log.e(".....", ImPath);
		Picasso.with(getBaseContext()).load(ImPath.trim().toString())
				.placeholder(R.drawable.ic_launcher).into(iv_personal_icon);

	}

	public String getToken() {
		SharedPreferences sp = getSharedPreferences("ROOT",
				Context.MODE_PRIVATE);

		ID = sp.getString("UserID", "");
		Log.d("LoginActivity", ID);
		return ID;
	}

	public boolean getUserNameByToken() {
		String token = getToken();
		if (token == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean IsOnline() {
		// TODO Auto-generated method stub
		if (getUserNameByToken()) {
			Toast.makeText(this, "�û��ѵ�¼", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			Toast.makeText(this, "���ȵ�¼", Toast.LENGTH_SHORT).show();
			return false;
		}

	}

	private void getLayoutId() {
		// TODO Auto-generated method stub
		username = (EditText) findViewById(R.id.username);// ����
		address = (TextView) findViewById(R.id.set_info_address);// ��ַ
		nation = (Spinner) findViewById(R.id.spinner_nation); // ����
		cardtype = (Spinner) findViewById(R.id.spinnerIdType);// ֤������
		card = (EditText) findViewById(R.id.card);// ����
		truename = (EditText) findViewById(R.id.truename);// ��ʵ����
		password = (EditText) findViewById(R.id.password);// ����
		confirmPassword = (EditText) findViewById(R.id.confirmPassword);
		phone = (TextView) findViewById(R.id.phone);// �ֻ���
		// Id=(TextView)findViewById(R.id.ID);//ȫ��id
		// Id.setText(getToken());
		iv_personal_icon = (ImageView) findViewById(R.id.iv_personal_icon);

		femail = (RadioButton) findViewById(R.id.set_femail);
		mail = (RadioButton) findViewById(R.id.set_mail);

	}

	class SpinnerXMLSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	private void showDialog1() {
		// ʵ����Dialog1
		final DialogInfoAddress dialog1 = new DialogInfoAddress(this,
				info_address.getText().toString());
		dialog1.setCancelable(true);
		dialog1.show();
		dialog1.setClicklistener(new DialogInfoAddress.ClickListenerInterface() {
			@Override
			public void doConfirm() {
				province = dialog1.getProvince();
				city = dialog1.getCity();
				dialog1.dismiss();
				// �������������ո�ָ���Dialog1����ķָ���Ҫ�����һ��
				info_address.setText(province + "  " + city);
			}

			@Override
			public void doCancel() {
				// �������û���ϣ�������������ť�Ļ���������
			}
		});
	}

	// Ϊ��������ʵ�ּ�����
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.Layout_take_photo:
				Intent openCameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				tempUri = Uri.fromFile(new File(
						Environment.getExternalStorageDirectory()
								+ "/TravelPersonal_icon", "image.jpg"));
				Log.d("11111111", tempUri.toString());
				// ָ����Ƭ����·����SD������image.jpgΪһ����ʱ�ļ���ÿ�����պ����ͼƬ���ᱻ�滻
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
				startActivityForResult(openCameraIntent, TAKE_PICTURE);
				break;
			case R.id.Layout_pick_photo:
				Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
				openAlbumIntent.setType("image/*");
				startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // ����������ǿ����õ�
			switch (requestCode) {
			case TAKE_PICTURE:
				startPhotoZoom(tempUri); // ��ʼ��ͼƬ���вü�����
				break;
			case CHOOSE_PICTURE:
				startPhotoZoom(data.getData()); // ��ʼ��ͼƬ���вü�����
				break;
			case CROP_SMALL_PICTURE:
				if (data != null) {
					setImageToView(data); // �øղ�ѡ��ü��õ���ͼƬ��ʾ�ڽ�����
				}
				break;
			}
		}
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param
	 * 
	 * @param picdata
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = Utils.toRoundBitmap(photo, tempUri); // ���ʱ���ͼƬ�Ѿ��������Բ�ε���
			iv_personal_icon.setImageBitmap(photo);
			uploadPic(photo);
		}
	}

	private void renameFile(String oldPath, String newPath) {
		if (TextUtils.isEmpty(oldPath)) {
			return;
		}
		if (TextUtils.isEmpty(newPath)) {
			return;
		}
		File file = new File(oldPath);
		file.renameTo(new File(newPath));
	}

	private void uploadPic(Bitmap bitmap) {
		// �ϴ���������
		// ... �����������Bitmapת����file��Ȼ��õ�file��url�����ļ��ϴ�����
		// ע������õ���ͼƬ�Ѿ���Բ��ͼƬ��
		// bitmap��û������Բ�δ���ģ����Ѿ����ü���
		Time time = new Time();
		time.setToNow();
		Imageid = String.valueOf(time.year) + String.valueOf(time.month + 1)
				+ String.valueOf(time.monthDay) + String.valueOf(time.hour)
				+ String.valueOf(time.minute) + String.valueOf(time.second)
				+ "";
		String imagePath = Utils.savePhoto(bitmap, Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/TravelPersonal_icon", Imageid);
		File file = new File(imagePath);
		Log.d("imagePath", imagePath + "");
		if (imagePath != null) {
			// ����imagePath�ϴ���
			new Thread(new UpFile()).start();

			// ...

			// String fileName = Imageid+".png";
			// Log.e("fileName",fileName);
			// File fileChange = new File(imagePath, fileName) ;
			//
			// if(!fileChange.exists()){ try { fileChange.createNewFile();
			// String oldPath = fileChange.getAbsolutePath();
			// String newPath = "";
			// newPath = oldPath.replace(fileName, "image_icon.png");
			// renameFile(oldPath, newPath);
			// //file is create
			// } catch (IOException e) { // TODO Auto-generated catch block
			// e.printStackTrace();
			// } }
			// else { String oldPath = fileChange.getAbsolutePath();
			// if(!TextUtils.isEmpty(oldPath)) { String newPath =
			// fileChange.getAbsolutePath() + "other.txt";
			// newPath = oldPath.replace(fileName, "other.txt");
			// renameFile(oldPath, newPath);
			// } }
		}
	}

}

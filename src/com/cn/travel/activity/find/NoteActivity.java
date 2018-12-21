package com.cn.travel.activity.find;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.smssdk.SMSSDK.InitFlag;

import com.cn.travle.R;
import com.cn.travel.activity.find.FileUtils;
import com.cn.travel.activity.find.InterceptLinearLayout;
import com.cn.travel.activity.find.RichTextEditor;
import com.cn.travel.activity.find.RichTextEditor.EditData;
import com.cn.travel.activity.first.food.FirstXiangqingActivity;
import com.cn.travel.domain.IDclass;
import com.cn.travel.info.InfoActivity;
import com.cn.travel.info.SelectPicPopupWindow;
import com.cn.travel.info.Utils;
import com.cn.travel.service.me.InfoService;
import com.cn.travel.utils.Tools;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint("SimpleDateFormat")
public class NoteActivity extends Activity implements OnClickListener{
	private String Imageid=null;
	private forumbean forumbean1=new forumbean();
	private forumbean us=new forumbean();
	private SelectPicPopupWindow menuWindow;
	protected static Uri tempUri;
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
	private final int REQUEST_CODE_PICK_IMAGE = 200;
	private final int REQUEST_CODE_PICK_IMAGE1 = 300;
	private final String TAG = "NoteActivity";
	private Context context;
	private LinearLayout line_rootView, line_addImg;//分别是整个布局和添加图片的布局id
	private InterceptLinearLayout line_intercept;//自定义布局id
	private RichTextEditor richText;          //富文本编辑器
	private EditText et_name;                  //游记标题名
	private boolean isKeyBoardUp, isEditTouch;// 判断软键盘的显示与隐藏
	private File mCameraImageFile;// 照相机拍照得到的图片
	private FileUtils mFileUtils; //保存图片的工具类
	private TextView tv_back,tv_ok;  //返回主界面和提交游记按钮
	private ImageView ft;
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
      
       //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_find_note);
		context = this;
		init();
		
	}
	public void init(){
		mFileUtils = new FileUtils(context);
		line_addImg = (LinearLayout) findViewById(R.id.line_addImg);
		line_rootView = (LinearLayout) findViewById(R.id.line_rootView);
		line_intercept = (InterceptLinearLayout) findViewById(R.id.line_intercept);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener((OnClickListener) this);
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		tv_ok.setOnClickListener((OnClickListener) this);
		et_name = (EditText) findViewById(R.id.et_name);
		ft=(ImageView) findViewById(R.id.ft);
		richText = (RichTextEditor) findViewById(R.id.richText);
		ft.setOnClickListener(this);
		initRichEdit();
		
	}
	
	
	private void initRichEdit() {
		ImageView img_addPicture, img_takePicture;
		img_addPicture = (ImageView) line_addImg
				.findViewById(R.id.img_addPicture);
		img_addPicture.setOnClickListener(this);
		img_takePicture = (ImageView) line_addImg
				.findViewById(R.id.img_takePicture);
		img_takePicture.setOnClickListener(this);
//输入的标题监听
		et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					isEditTouch = false;
					line_addImg.setVisibility(View.GONE);
				}
			}

		});
		//富文本编辑器监听
		richText.setLayoutClickListener(new RichTextEditor.LayoutClickListener() {
			@Override
			public void layoutClick() {
				isEditTouch = true;
				line_addImg.setVisibility(View.VISIBLE);
			}
		});

		//整个布局的监听显示
		line_rootView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						int heightDiff = line_rootView.getRootView()
								.getHeight() - line_rootView.getHeight();
						if (isEditTouch) {
							if (heightDiff > 500) {// 大小超过500时，一般为显示虚拟键盘事件,此判断条件不唯一
								isKeyBoardUp = true;
								line_addImg.setVisibility(View.VISIBLE);
							} else {
								if (isKeyBoardUp) {
									isKeyBoardUp = false;
									isEditTouch = false;
									line_addImg.setVisibility(View.GONE);
								}
							}
						}
					}
				});
	}
	
	//一下为打开照相机的三个函数方法，共同协作完成。
	private void openCamera() {
		try {
			File PHOTO_DIR = new File(mFileUtils.getStorageDirectory());
			if (!PHOTO_DIR.exists())
				PHOTO_DIR.mkdirs();// 创建照片的存储目录

			mCameraImageFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCameraImageFile);
			startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
		} catch (ActivityNotFoundException e) {
		}
	}
	
	
	private Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	/**
	 * 用当前时间给取得的图片命名
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyy_MM_dd_HH_mm_ss");
		return dateFormat.format(date) + ".jpg";
	}
	//到此结束
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == REQUEST_CODE_PICK_IMAGE) {
			Uri uri = data.getData();
			startPhotoZoom(data.getData());
			
		}
		else if(requestCode == REQUEST_CODE_PICK_IMAGE1){
			Uri uri = data.getData();
			richText.insertImage(mFileUtils.getFilePathFromUri(uri));
			//startPhotoZoom1(data.getData());
		}
		else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA
				&& resultCode == Activity.RESULT_OK) {
			richText.insertImage(mCameraImageFile.getAbsolutePath());
		}else if(requestCode==CROP_SMALL_PICTURE){
			if (data != null) {
				 Bitmap bitmap = data.getExtras().getParcelable("data");
				 ft.setImageBitmap(bitmap);
				setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
			}
		}

	}
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		 
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 0.1);
		intent.putExtra("aspectY", 0.1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}
	protected void startPhotoZoom1(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		 
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 0.1);
		intent.putExtra("aspectY", 0.1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}
	
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			//photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			//ft.setImageBitmap(photo);
			uploadPic(photo);
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mFileUtils.deleteRichTextImage();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_ok:
			//Log.i(TAG, "---richtext-data:" + richText.getRichEditData());
		
			//Intent intent1=new Intent();
			//intent1.setAction("com.gasFragment");
			String a1=et_name.getText().toString().trim();
			
			//intent1.putExtra("titile",et_name.getText().toString().trim() );
           //sendBroadcast(intent1);
           
           sendforum();
          finish();
           
			break;

		case R.id.tv_back:
			finish();
			break;
			
		case R.id.img_addPicture:
			// 打开系统相册
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");// 相片类型
			startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE1);
			break;
		case R.id.img_takePicture:
			// 打开相机
			openCamera();
		case R.id.ft:
			Intent intent1 = new Intent(Intent.ACTION_PICK);
			intent1.setType("image/*");// 相片类型
			startActivityForResult(intent1, REQUEST_CODE_PICK_IMAGE);
			
			break;
		}
		
	}
	
	
	public void sendforum(){
		String name=et_name.getText().toString().trim();
		if(TextUtils.isEmpty(name)){
			Toast.makeText(getBaseContext(), "请输入标题",
					Toast.LENGTH_SHORT).show();
			return;
			
			
		}
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date(System.currentTimeMillis());
		
		forumbean1.setTime(simpleDateFormat.format(date));
		forumbean1.setUsername(IDclass.UserName);
		if(us.getImage()!=null){
		forumbean1.setImage(us.getImage().toString()+"");}
		forumbean1.setUserimage(IDclass.iconImage);
		forumbean1.setUserid(IDclass.ID);
		
		forumbean1.setTitle(et_name.getText().toString());
		
		//开始
		forumbean forumbeanx=new forumbean();
		forumbean forumbeanb=new forumbean();
		List<EditData> dataList1 = new ArrayList<EditData>();
		forumbeanb=richText.buildEditData(forumbeanx);
		Log.i("文字信息", forumbeanb.getInputStr()+"");
		Log.i("图片信息", forumbeanb.getImagePath()+"");
		forumbean1.setInputStr(forumbeanb.getInputStr()+"");
		forumbean1.setImagePath(forumbeanb.getImagePath()+"");
		//forumbeanx=richText.buildEditData();
		
			FindService.login(forumbean1, NoteActivity.this);
			Toast.makeText(context, "游记已经编写,请到服务器查看", Toast.LENGTH_SHORT).show();
		
		
	}
	private class UpFile implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String filePath = "/storage/emulated/0/TravelPersonal_icon/";			
			filePath =filePath+Imageid+".png";	
			Log.e("上传文件路径....", filePath+"");			
			ArrayList<String>  array = new ArrayList<String>();
			array.add(filePath);

			InfoService.upload(Tools.url_uploadMyImage, array, NoteActivity.this.getBaseContext());
			String str = "images/"+Imageid+".png";
			us.setImage(str);
			
		}
		
	}
	private void uploadPic(Bitmap bitmap) {
		// 上传至服务器
		// ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
		// 注意这里得到的图片已经是圆形图片了
		// bitmap是没有做个圆形处理的，但已经被裁剪了
		Time time = new Time();
		time.setToNow();
		Imageid = String.valueOf(time.year)
				+ String.valueOf(time.month + 1) + String.valueOf
                (time.monthDay) +String.valueOf(time.hour)+String.valueOf(time.minute)+ String.valueOf(time.second) + "";
		String imagePath = Utils.savePhoto(bitmap, Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/TravelPersonal_icon", Imageid);
		File file =new File(imagePath);
		Log.d("imagePath", imagePath + "");
		if (imagePath != null) {
			// 拿着imagePath上传了
			new Thread(new UpFile()).start();
			
		}
	}
	class EditData {
		String inputStr;//文本文字
		String imagePath;//帖子图片路径
		Bitmap bitmap;//帖子图片
	}

}

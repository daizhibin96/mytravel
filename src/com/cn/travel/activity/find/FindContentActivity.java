package com.cn.travel.activity.find;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.cn.travel.utils.Tools;
import com.cn.travle.R;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FindContentActivity extends Activity {
	private ImageView back;//返回	
	private ImageView imageView2;//收藏
	private ImageView fengmian;//封面图片
	private ImageView touxiang;//用户头像
	
	private TextView title;//标题
	private TextView user;//用户名
	private TextView time;//发帖时间
	private RelativeLayout first;
	private InterceptLinearLayout line_intercept1;
	private static RichTextEditor richText1;
	private boolean isKeyBoardUp, isEditTouch;// 判断软键盘的显示与隐藏
	private LinearLayout line_rootView1;
	String pic;
	//private FileUtils mFileUtils;
	//private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_find_content);
		//context=this;
		 back=(ImageView) findViewById(R.id.imageView1);//设置返回监听
		 imageView2=(ImageView) findViewById(R.id.imageView2);//设置收藏监听
		 touxiang=(ImageView) findViewById(R.id.touxiang);//设置收藏监听
		
          fengmian=(ImageView) findViewById(R.id.fengmian);//设置封面图片
		 title=(TextView)findViewById(R.id.title);//设置标题
		 user=(TextView)findViewById(R.id.user);//设置用户名
		 time= (TextView)findViewById(R.id.time);//时间
		 first=(RelativeLayout)findViewById(R.id.first);
		 IntView();//为变量赋值
		 
		 //返回按钮
		 back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();

				}
			});
	}
	public void IntView(){
		Intent intent1 = getIntent();
		 String msg=intent1.getStringExtra("title");//信息
		  pic=intent1.getStringExtra("pic");//图片
		 String aaa=intent1.getStringExtra("aaa");//用户头像
		 String name=intent1.getStringExtra("name");//
		 String timea=intent1.getStringExtra("time");//
		 String inputString=intent1.getStringExtra("inputString");
		 String images=intent1.getStringExtra("image");
		 String[] s = inputString.split(";");
		 String[] z=images.split(";");
		 //Log.i("帖子内容里面的文本信息", inputString);
		 time.setText(timea);
		 user.setText(name);
		 title.setText(msg);
		 Picasso.with(FindContentActivity.this).load(Tools.baseUrl+aaa.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(touxiang);
		 if(pic==null){
				fengmian.setBackgroundResource(R.drawable.ic_launcher);
			}else{
		 Picasso.with(FindContentActivity.this).load(Tools.baseUrl+pic.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(fengmian);}
		// 开始富文本
		 line_rootView1 = (LinearLayout) findViewById(R.id.line_rootView1);
		 line_intercept1 = (InterceptLinearLayout) findViewById(R.id.line_intercept1);
		 richText1=(RichTextEditor) findViewById(R.id.richText1);
		 initRichEdit();
		 first.setBackgroundColor(Color.TRANSPARENT);
		 line_intercept1.setIntercept(true);
			richText1.setIntercept(true);
			if(s.length>0){
			for(int i =0 ;i<s.length;i++){
				 //Log.i("  数组"+i, s[i]);
				 richText1.insertText(s[i]);
			 }}
			else{
				richText1.insertText("还未编写任何内容！！！");
			}
			
			if(z.length>0){
				for(int j=0;j<z.length;j++){
				 //Log.i("  数组", z[j]);
				String [] x=z[j].split("/");
				Log.i("  数组", x[x.length-1]);
				richText1.insertImageByURL(Tools.baseUrl+"images/"+ x[x.length-1]);}

			}
			//getData();
			
			
		 
		 
		
	}
	private void getData() {

		
		//richText1.insertText("第一行");
		//richText1.insertText("接下来是张图片-风车");
		//richText1.insertImage(pic);
		//richText1.insertImageByURL("http://172.27.1.18:8080/images/a1.jpg");
		//richText1.insertText("下面是一副眼镜");
	
		//richText1.insertImageByURL("http://img4.3lian.com/sucai/img6/230/29.jpg");
		//richText1.insertImageByURL("http://pic9.nipic.com/20100812/3289547_144304019987_2.jpg");
		//richText1.insertText("上面是一个树妖");
		//richText1.insertText("最后一行");
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//final Bitmap bitmap=getPicture("http://172.27.1.18:8080/images/a1.jpg");
				richText1.insertImageByURL("http://172.27.1.18:8080/images/a1.jpg");
				
			try{
				Thread.sleep(2000);//线程休眠两秒钟
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			}
		}).start();*/
		
		}
		
	
	public Bitmap getPicture(String path){
    	Bitmap bm=null;
    	URL url;
		try {
			url = new URL(path);//创建URL对象
			URLConnection conn=url.openConnection();//获取URL对象对应的连接
	    	conn.connect();//打开连接
	    	InputStream is=conn.getInputStream();//获取输入流对象
	    	bm=BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
		} catch (MalformedURLException e1) {
			e1.printStackTrace();//输出异常信息
		}catch (IOException e) {
			e.printStackTrace();//输出异常信息
		}
 
 
		return bm;    	
    }


	private void initRichEdit() {
		
		richText1.setLayoutClickListener(new RichTextEditor.LayoutClickListener() {
			@Override
			public void layoutClick() {
				isEditTouch = true;
				
			}
		});
		
		
		line_rootView1.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						int heightDiff = line_rootView1.getRootView()
								.getHeight() - line_rootView1.getHeight();
						if (isEditTouch) {
							if (heightDiff > 500) {// 大小超过500时，一般为显示虚拟键盘事件,此判断条件不唯一
								isKeyBoardUp = true;
								
							} else {
								if (isKeyBoardUp) {
									isKeyBoardUp = false;
									isEditTouch = false;
									
								}
							}
						}
					}
				});
	
	}
	
	


}

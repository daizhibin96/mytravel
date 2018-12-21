package com.cn.travel.activity.first.food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;



import com.cn.travle.R;
import com.cn.travel.activity.first.food.AnimationTools;
import com.cn.travel.activity.first.food.MyService;
import com.cn.travel.bean.NoteBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstXiangqingActivity extends Activity implements View.OnClickListener {
    private MyService myService=new MyService();
    private CollectService collectService=new CollectService();
    MyBroadcastReceiver1 broadcastReceiver;

    
    //private LocalBroadcastManager localBroadcastManager;
    
    
	private ImageView comment;
	private ImageView shuaxin;
	private ImageView imageView1;
	private ImageView collection;
	private TextView hide_down;
	private TextView showtext;
	private EditText comment_content;
	private Button comment_send;
     int flag1;
    int newflag;
    boolean a;
	private LinearLayout rl_enroll;
	private RelativeLayout rl_comment;
	private CollectBean collectBean;

	private ListView comment_list;
	private CommentAdapter adapterComment;
	private List<Comment1> data;
	private ArrayList<Comment1> comment1 = new ArrayList<Comment1>();
	 private int foodid;
	 String msg;
	 String pic;
	 String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//连接SerVice
		Intent intent1 = getIntent();
		String d = intent1.getStringExtra("aaa");
		 msg=intent1.getStringExtra("msg1");//信息
		 pic=intent1.getStringExtra("pic");//图片
		 title=intent1.getStringExtra("title");
		foodid=intent1.getIntExtra("id", 1);
		
		Gson gson = new Gson();
		comment1=new ArrayList<Comment1>();
		comment1= gson.fromJson(d, new TypeToken<ArrayList<Comment1>>() {
		}.getType());
		Log.i("name", comment1.toString());
		
		//结束
		
		setContentView(R.layout.food_xiangqing);
		initView();//初始化
		showtext=(TextView) findViewById(R.id.showtext);
		showtext.setText(msg);
		 imageView1=(ImageView) findViewById(R.id.imageView1);//设置封面图片
		 Picasso.with(FirstXiangqingActivity.this).load(Tools.baseUrl+pic.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(imageView1);
		 
		//初始化并且收藏信息
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date(System.currentTimeMillis());
			 collectBean=new CollectBean();
		
			collectBean.setUsername(IDclass.UserName);//用户id
			collectBean.setTitle(title);//标题
			collectBean.setImage(pic);//图片
			collectBean.setTime(simpleDateFormat.format(date));//图片
			collectBean.setType("美食");
	       
	        
	   
	        broadcastReceiver=new MyBroadcastReceiver1();
			IntentFilter filter = new IntentFilter();
	        filter.addAction("com.shoucang");
	        registerReceiver(broadcastReceiver, filter);
	        collectService.getcollect(collectBean, FirstXiangqingActivity.this);
	        		 
		
	}
	/*
	@Override
	public void onAttachedToWindow() {
		
		
		broadcastReceiver=new MyBroadcastReceiver();
	    //开始广播
				IntentFilter filter=new IntentFilter();
				filter.addAction("com.gasFragment");
				this.registerReceiver(broadcastReceiver, filter);
		        super.onAttachedToWindow();

	}*/
	
	
	
	
	public class MyBroadcastReceiver1 extends BroadcastReceiver{
		@Override
		
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			//boolean a1=intent.getBooleanExtra("biaoji", false);
			Log.i("guangbo", "成功发送广播");
			boolean a1=intent.getBooleanExtra("biaoji", false);
			a=a1;
			Log.i("发送过来的广播flag", a1+"");
			if(a1==true)
			{
				flag1=1;
				collection.setImageResource(R.drawable.shoucang);
			}
			else{
				flag1=0;
				collection.setImageResource(R.drawable.shoucang1);
			}
			
			
			
			
			//adapter.notifyDataSetChanged();
			
			
		}
	}
	
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("jjj","adtivitydestory");
        unregisterReceiver(broadcastReceiver);
	}
	/*
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(broadcastReceiver);
		super.onDetachedFromWindow();
	}*/

	

	private void initView() {
		

		// 初始化评论列
		comment_list = (ListView) findViewById(R.id.comment_list);
		// 初始化数
		data = new ArrayList<Comment1>();
		// 初始化配器
		adapterComment = new CommentAdapter(getApplicationContext(), comment1);
		// 为评论列表设置配器
		comment_list.setAdapter(adapterComment);

		comment = (ImageView) findViewById(R.id.comment);
		hide_down = (TextView) findViewById(R.id.hide_down);
		comment_content = (EditText) findViewById(R.id.comment_content);
		comment_send = (Button) findViewById(R.id.comment_send);
        shuaxin=(ImageView) findViewById(R.id.shuaxin);
       
		rl_enroll = (LinearLayout) findViewById(R.id.rl_enroll);// 查看评论时的底部导航
		rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);// 进行输入信息的底部导�?
		collection = (ImageView) findViewById(R.id.collection);
		
		setListener();
	}

	/**
	 * 设置监听
	 */
	public void setListener() {
		comment.setOnClickListener(this);//输入评论监听
        shuaxin.setOnClickListener(this);//刷新监听
        collection.setOnClickListener(this);//收藏监听
		hide_down.setOnClickListener(this);
		comment_send.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		//final ViewHolder holder;
		//holder=new ViewHolder();
		switch (v.getId()) {
		case R.id.comment:
			// 弹出输入�?
			InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

			// 显示评论�?
			rl_enroll.setVisibility(View.GONE);
			rl_comment.setVisibility(View.VISIBLE);

			break;
		case R.id.shuaxin:
			finish();
			break;
		case R.id.collection:
			if(flag1==0){
				
			collection.setImageResource(R.drawable.shoucang);
			 AnimationTools.scale(collection);
			flag1++;
			sendCollect();
			}
			else{
				
				collection.setImageResource(R.drawable.shoucang1);
				AnimationTools.scale(collection);
				flag1--;
				deleteCollect();
				
				
			}
			break;
		case R.id.hide_down:
			// 隐藏评论�?
			rl_enroll.setVisibility(View.VISIBLE);
			rl_comment.setVisibility(View.GONE);

			// 隐藏输入法，然后暂存当前输入框的内容，方便下次使�?
			InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
		case R.id.comment_send:
			
			sendComment();
			break;
		default:
			break;
		}
	}

	/**
	 * 发�?评论
	 */
	public void sendComment() {
		if (comment_content.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "评论不能为空",
					Toast.LENGTH_SHORT).show();
		} else {
			// 生成评论数据
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date(System.currentTimeMillis());
			
			Comment1 comment = new Comment1();
			comment.setUsername(IDclass.UserName);
		    comment.setFoodid(foodid);
			comment.setMessage(comment_content.getText().toString());
			comment.setTime(simpleDateFormat.format(date));
			adapterComment.addComment(comment);
			// 发完，清空输入
			MyService.login(comment,FirstXiangqingActivity.this);
			comment_content.setText("");

			Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT)
					.show();
		}
	}
	//发�?收藏的信�?
	public void sendCollect(){
		
		
		MyService.shoucang(collectBean, FirstXiangqingActivity.this);
		
		
	}
public void deleteCollect(){
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date(System.currentTimeMillis());
		CollectBean collectBean=new CollectBean();
		collectBean.setType("美食");
		collectBean.setUsername(IDclass.UserName);//用户id
		collectBean.setTitle(title);//信息
		collectBean.setImage(pic);//图片
		collectBean.setTime(simpleDateFormat.format(date));//时间
		MyService.deleteshoucang(collectBean, FirstXiangqingActivity.this);
		
		
	}
	

}

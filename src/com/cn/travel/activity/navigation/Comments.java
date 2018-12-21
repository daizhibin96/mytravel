package com.cn.travel.activity.navigation;

import java.util.ArrayList;

import com.baidu.mapapi.SDKInitializer;
import com.cn.travel.activity.navigation.HotelList.mAdapter;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.OrderCommentBean;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Comments extends Activity {

	private ArrayList<OrderCommentBean> list = new ArrayList<OrderCommentBean>();
	private OrderCommentBean cBean = new OrderCommentBean();
	private ListView lv;
	private ImageView back;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    SDKInitializer.initialize(getApplicationContext());
	    setContentView(R.layout.activity_navigation_comments);
	    Intent intent = getIntent();
	    Gson gson = new Gson();
		String d = intent.getStringExtra("comment");
		list = gson.fromJson(d, new TypeToken<ArrayList<OrderCommentBean>>(){}.getType());
	    initView();
	    mAdapter madapter = new mAdapter(this,list);
	    lv.setAdapter(madapter);
	    back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Comments.this.finish();
			}
	    	
	    });
	}
	
	public void initView(){
		lv = (ListView)this.findViewById(R.id.comments);
		back = (ImageView)this.findViewById(R.id.back);
	}
	
	public class mAdapter extends BaseAdapter{

		private  ArrayList<OrderCommentBean> a= new ArrayList<OrderCommentBean>();
		private LayoutInflater mInflater;
		
		public mAdapter(Context context,ArrayList<OrderCommentBean> hl){
			a= hl;
			mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return a.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return a.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = View.inflate(Comments.this, R.layout.list_item_comments, null);
			ImageView userimg = (ImageView)view.findViewById(R.id.userimg);
			TextView username = (TextView)view.findViewById(R.id.username);
			TextView cTime = (TextView)view.findViewById(R.id.time);
			TextView cMessage = (TextView)view.findViewById(R.id.message);
			OrderCommentBean cBean = a.get(arg0);
			String img = Tools.baseUrl+cBean.getUserimg();
			Picasso.with(Comments.this).load(img.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(userimg);
			username.setText(cBean.getUsername());
			cTime.setText(cBean.getTime());
			cMessage.setText(cBean.getMessage());
			return view;
		}
		
	}
}

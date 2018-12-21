package com.cn.travel.activity.navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.cn.travel.activity.navigation.PointList.mAdapter;
import com.cn.travel.bean.HistoryBean;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.PointBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.service.me.CollectService;
import com.cn.travel.service.me.CommentService;
import com.cn.travel.service.me.HistoryService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

public class PointSearch extends Activity{

	private ImageView back;
	private EditText search;
	private ListView lv;
	private PointBean pointbean=new PointBean();
	private ArrayList<PointBean> pointlist= new ArrayList<PointBean>();
	private ArrayList<PointBean> list = new ArrayList<PointBean>();
	private String key=null;
	private IDclass userid;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    SDKInitializer.initialize(getApplicationContext());
	    setContentView(R.layout.activity_navigation_pointlist);
	    Intent intent = getIntent();
	    Gson gson = new Gson();
		String d = intent.getStringExtra("aaa");
		pointlist = gson.fromJson(d, new TypeToken<ArrayList<PointBean>>(){}.getType());
	    initView();
	    initListener();
	}
	
	public void initView(){
		back = (ImageView)this.findViewById(R.id.pointlist_back);
		search = (EditText)this.findViewById(R.id.pointlist_search);
		lv = (ListView)this.findViewById(R.id.pointlist_list);
	}
	
	public void initListener(){
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				PointSearch.this.finish();
			}
		
		});
		search.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				key = search.getText().toString();
				list = new ArrayList<PointBean>();
				for(int i=0;i<pointlist.size();i++)
				{
					String str = pointlist.get(i).getName();
					if(str.indexOf(key)!=-1)
					{
						list.add(pointlist.get(i));
						Log.e("加入成功",pointlist.get(i).toString());
					}
				}
				if(key.isEmpty())
					list = new ArrayList<PointBean>();
				if(list!=null){
					mAdapter madapter = new mAdapter(PointSearch.this,list);
				    lv.setAdapter(madapter);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public class mAdapter extends BaseAdapter{

		private  ArrayList<PointBean> b= new ArrayList<PointBean>();
		private LayoutInflater mInflater;
		
		public mAdapter(Context context,ArrayList<PointBean> pl){
			b= pl;
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return b.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return b.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = View.inflate(PointSearch.this, R.layout.list_item_pointlist, null);
			final ImageView pointimg = (ImageView)view.findViewById(R.id.point_img);
			TextView pointname = (TextView)view.findViewById(R.id.point_name);
			TextView pointprice = (TextView)view.findViewById(R.id.point_price);
			TextView pointito = (TextView)view.findViewById(R.id.point_introduce);
			TextView pointadd = (TextView)view.findViewById(R.id.point_address);
			final PointBean pbean = b.get(arg0);
			String img = Tools.baseUrl+pbean.getImage();
			Picasso.with(PointSearch.this).load(img.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(pointimg);
			pointname.setText(pbean.getName());
			if(pbean.getPrice().equals("0")){
				pointprice.setText("免费");
			}else{
				pointprice.setText(pbean.getPrice()+"元起");
			}
			pointito.setText(pbean.getTitle());
			pointadd.setText(pbean.getAddress());
			view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
			        Date date = new Date(System.currentTimeMillis());
			        String time = simpleDateFormat.format(date);
					HistoryService historyService = new HistoryService();
					HistoryBean historyBean = new HistoryBean();
					historyBean.setId(pbean.getId());
					historyBean.setUserid(userid.ID);
					historyBean.setName(pbean.getName());
					historyBean.setImage(pbean.getImage());
					historyBean.setTime(time);
					historyBean.setType("景点");
					historyService.history(PointSearch.this, historyBean);
					if(userid.ID!=null){
						CollectService collect = new CollectService();
						collect.collectService(PointSearch.this, userid.UserName,pbean);
					}else{
						Gson gson = new Gson();
						String point = gson.toJson(pbean);
						String str = null;
						CommentService comm = new CommentService();
						comm.collectService(PointSearch.this, str, point, 2);
					}
				}
			
			});
			return view;
		}
		
	}
	
}

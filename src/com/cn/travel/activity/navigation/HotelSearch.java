package com.cn.travel.activity.navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.baidu.mapapi.SDKInitializer;
import com.cn.travel.activity.navigation.HotelList.mAdapter;
import com.cn.travel.bean.HistoryBean;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.service.me.CollectionService;
import com.cn.travel.service.me.CommentService;
import com.cn.travel.service.me.HistoryService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
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

public class HotelSearch extends Activity {

	private ImageView back;
	private EditText search;
	private ListView lv;
	private HotelBean hotelbean=new HotelBean();
	private ArrayList<HotelBean> hotelList= new ArrayList<HotelBean>();
	private ArrayList<HotelBean> list = new ArrayList<HotelBean>();
	private String key=null;
	private IDclass userid;
	@SuppressLint("JavascriptInterface") 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    SDKInitializer.initialize(getApplicationContext());
	    setContentView(R.layout.activity_navigation_hotellist);
	    Intent intent = getIntent();
	    Gson gson = new Gson();
		String d = intent.getStringExtra("aaa");
		hotelList = gson.fromJson(d, new TypeToken<ArrayList<HotelBean>>(){}.getType());
	    initView();
	    initListener();
	    
	}
	
	public void initView(){
		back = (ImageView)this.findViewById(R.id.hotellist_back);
		search = (EditText)this.findViewById(R.id.hotellist_search);
        lv = (ListView)this.findViewById(R.id.hotellist_list);
	}
	

	
	public void initListener(){
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HotelSearch.this.finish();
			}
		
		});
		search.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				key = search.getText().toString();
				list = new ArrayList<HotelBean>();
				for(int i=0;i<hotelList.size();i++)
				{
					String str = hotelList.get(i).getName();
					if(str.indexOf(key)!=-1)
					{
						list.add(hotelList.get(i));
						Log.e("加入成功",hotelList.get(i).toString());
					}
				}
				if(key.isEmpty())
					list = new ArrayList<HotelBean>();
				if(list!=null){
					mAdapter madapter = new mAdapter(HotelSearch.this,list);
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

		private  ArrayList<HotelBean> a= new ArrayList<HotelBean>();
		private LayoutInflater mInflater;
		
		public mAdapter(Context context,ArrayList<HotelBean> hl){
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
			View view = View.inflate(HotelSearch.this, R.layout.list_item_hotellist, null);
			final ImageView hotelimg = (ImageView)view.findViewById(R.id.hotel_img);
			TextView hotelname = (TextView)view.findViewById(R.id.hotel_name);
			TextView hotelprice = (TextView)view.findViewById(R.id.hotel_price);
			TextView hotelito = (TextView)view.findViewById(R.id.hotel_introduce);
			TextView hoteladd = (TextView)view.findViewById(R.id.hotel_address);
			final HotelBean hbean = a.get(arg0);
			String img = Tools.baseUrl+hbean.getImage();
			Picasso.with(HotelSearch.this).load(img.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(hotelimg);
			hotelname.setText(hbean.getName());
			hotelprice.setText(hbean.getOneprice()+"元起");
			hotelito.setText(hbean.getTitle());
			hoteladd.setText(hbean.getAddress());
			view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
			        Date date = new Date(System.currentTimeMillis());
			        String time = simpleDateFormat.format(date);
					HistoryService historyService = new HistoryService();
					HistoryBean historyBean = new HistoryBean();
					historyBean.setId(hbean.getId());
					historyBean.setUserid(userid.ID);
					historyBean.setName(hbean.getName());
					historyBean.setImage(hbean.getImage());
					historyBean.setTime(time);
					historyBean.setType("酒店");
					historyService.history(HotelSearch.this, historyBean);
					if(userid.ID!=null){
						CollectionService collect = new CollectionService();
						collect.collectService(HotelSearch.this, userid.UserName,hbean);
					}else{
						Gson gson = new Gson();
						String point = gson.toJson(hbean);
						String str = null;
						CommentService comm = new CommentService();
						comm.collectService(HotelSearch.this, str, point, 1);
					}
				}
			
			});
			return view;
		}
		
	}
	
}

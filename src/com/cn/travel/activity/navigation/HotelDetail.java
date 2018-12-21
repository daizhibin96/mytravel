package com.cn.travel.activity.navigation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.cn.travel.activity.navigation.HotelList.mAdapter;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.OrderCommentBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.service.me.AddCollectService;
import com.cn.travel.service.me.DeleteCollectService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HotelDetail extends Activity {
	
	private MapView mapView;
	private BaiduMap baiduMap;
	public LocationClient mLocationClient;
	public BDLocationListener myListener = new MyLocationListener();
	private LocationMode mLocationMode;
	public boolean isFirstIn = true;
	private ImageView img;
	private ImageView back;
	private ImageView collection;
	private HotelBean hotelbean = new HotelBean();
	private TextView name;
	private TextView address;
	private TextView message;
	private TextView startdate;
	private TextView stime;
	private TextView enddate;
	private TextView etime;
	private Button order;
	private Button inmap;
	private long time;
	private long ttime;
	private IDclass id = new IDclass();
	boolean Collected = false;
	private ArrayList<CollectBean> arrayList = new ArrayList<CollectBean>();
	private ArrayList<OrderCommentBean> aList = new ArrayList<OrderCommentBean>();
	private ArrayList<OrderCommentBean> cList = new ArrayList<OrderCommentBean>();
	private ImageView userimg;
	private TextView username;
	private TextView usertime;
	private TextView comment;
	private Button more;
	private ImageView editcomment;
	//private ListView comments;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_navigation_hoteldetail);
		Intent intent = getIntent();
		Gson gson = new Gson();
		String str = intent.getStringExtra("hotel");
		String str2 = intent.getStringExtra("comments");
		aList = gson.fromJson(str2, new TypeToken<ArrayList<OrderCommentBean>>(){}.getType());
		hotelbean = gson.fromJson(str, HotelBean.class);
		initView();
		initMap();
		initLocation();
		String Img = Tools.baseUrl+hotelbean.getImage();
        Picasso.with(HotelDetail.this).load(Img.trim().toString())
		.placeholder(R.drawable.ic_launcher).into(img);
        name.setText(hotelbean.getName());
        address.setText("地址:"+hotelbean.getAddress());
        message.setText("简介:"+hotelbean.getMessage());
        if(id.ID!=null){
        	String str1 = intent.getStringExtra("collection");
        	arrayList = gson.fromJson(str1, new TypeToken<ArrayList<CollectBean>>(){}.getType());
        	for(int n=0;n<arrayList.size();n++){
            	if(hotelbean.getName().equals(arrayList.get(n).getTitle())&&arrayList.get(n).getType().equals("酒店")){
            		BitmapFactory.Options opt = new BitmapFactory.Options();
    	            opt.inPreferredConfig = Bitmap.Config.RGB_565;
    	            opt.inPurgeable = true;
    	            opt.inInputShareable = true;
    	            InputStream is = getResources().openRawResource(R.drawable.collected);
    	            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
    	            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
    	            collection.setBackgroundDrawable(bd);
    	            Collected = true;
            	}
            }
        }
        stime.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setDialog(1);
			}
        	
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        stime.setText(simpleDateFormat.format(date));
        time = date.getTime();
        ttime = time;
        etime.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setdialog(2);
			}
        	
        });
        etime.setText(simpleDateFormat.format(date));
        
        order.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(id.ID!=null){
					Intent intent = new Intent(HotelDetail.this,HotelOrder.class);
					Gson gson = new Gson();
					String str = gson.toJson(hotelbean);
					Date std = new Date(time);
					Date etd = new Date(ttime);
					String sDate = gson.toJson(std);
					String eDate = gson.toJson(etd);
					intent.putExtra("hoteldetail", str);
					intent.putExtra("startTime", sDate);
					intent.putExtra("endTime", eDate);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(HotelDetail.this,LoginActivity.class);
					startActivity(intent);
				}
			}
        
        });
        
        inmap.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HotelDetail.this,Map.class);
				Gson gson = new Gson();
				double Lat = hotelbean.getLatlng();
				double Lng = hotelbean.getLonglng();
				String lat = gson.toJson(Lat);
				String lng = gson.toJson(Lng);
				intent.putExtra("latlng", lat);
				intent.putExtra("longlng", lng);
				startActivity(intent);
			}
        
        });
        back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HotelDetail.this.finish();
			}
        	
        });
        collection.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(id.ID!=null){
					if(Collected==false){
						BitmapFactory.Options opt = new BitmapFactory.Options();
			            opt.inPreferredConfig = Bitmap.Config.RGB_565;
			            opt.inPurgeable = true;
			            opt.inInputShareable = true;
			            InputStream is = getResources().openRawResource(R.drawable.collected);
			            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
			            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
			            collection.setBackgroundDrawable(bd);
			            Collected = true;
			            CollectBean cbean = new CollectBean();
			            AddCollectService add = new AddCollectService();
			            cbean.setUsername(id.UserName);
			            cbean.setTitle(hotelbean.getName());
			            cbean.setType("酒店");
			            cbean.setImage(hotelbean.getImage());
			            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
				        Date date = new Date(System.currentTimeMillis());
				        String time = simpleDateFormat.format(date);
			            cbean.setTime(time);
			            add.addCollection(HotelDetail.this, cbean);
			            Toast.makeText(HotelDetail.this, "收藏成功", Toast.LENGTH_SHORT).show();
					}
					else{
						BitmapFactory.Options opt = new BitmapFactory.Options();
			            opt.inPreferredConfig = Bitmap.Config.RGB_565;
			            opt.inPurgeable = true;
			            opt.inInputShareable = true;
			            InputStream is = getResources().openRawResource(R.drawable.collection);
			            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
			            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
			            collection.setBackgroundDrawable(bd);
			            Collected = false;
			            CollectBean cbean = new CollectBean();
			            DeleteCollectService delete = new DeleteCollectService();
			            cbean.setUsername(id.UserName);
			            cbean.setTitle(hotelbean.getName());
			            cbean.setType("酒店");
			            delete.addCollection(HotelDetail.this, cbean);
			            Toast.makeText(HotelDetail.this, "已取消收藏", Toast.LENGTH_SHORT).show();
					}
				}else{
					Intent intent = new Intent(HotelDetail.this,LoginActivity.class);
					startActivity(intent);
				}
				
			}
        
        });
        editcomment.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(id.ID!=null){
					Intent intent = new Intent(HotelDetail.this,EditComment.class);
					String hotelname = hotelbean.getName();
					String cType = "酒店";
					String userName = id.UserName;
					intent.putExtra("hotelname", hotelname);
					intent.putExtra("cType", cType);
					startActivity(intent);
				}else{
					Intent intent = new Intent(HotelDetail.this,LoginActivity.class);
					startActivity(intent);
				}
			}
        	
        });
        check(aList,cList);
        if(cList.size()!=0){
        	setComment(cList);
        }
        else{
        	more.setText("暂无评价");
        }
        //mAdapter madapter = new mAdapter(this,cList);
	    //comments.setAdapter(madapter);
        
	}
	
	//初始化视图
		public void initView(){
			mapView = (MapView)this.findViewById(R.id.hotel_mapView);
			img = (ImageView)this.findViewById(R.id.hoteldetail_img);
			name = (TextView)this.findViewById(R.id.hoteldetail_name);
			address = (TextView)this.findViewById(R.id.hoteldetail_address);
			message = (TextView)this.findViewById(R.id.hoteldetail_message);
			startdate = (TextView)this.findViewById(R.id.hoteldetail_startday);
			stime = (TextView)this.findViewById(R.id.startday);
			enddate = (TextView)this.findViewById(R.id.hoteldetail_endday);
			etime = (TextView)this.findViewById(R.id.endday);
			order = (Button)this.findViewById(R.id.hoteldetail_order);
			inmap = (Button)this.findViewById(R.id.in_map);
			back = (ImageView)this.findViewById(R.id.hoteldetail_back);
			collection = (ImageView)this.findViewById(R.id.hoteldetail_collection);
			userimg = (ImageView)this.findViewById(R.id.userimg);
			username = (TextView)this.findViewById(R.id.username);
			usertime = (TextView)this.findViewById(R.id.time);
			comment = (TextView)this.findViewById(R.id.comment);
			more = (Button)this.findViewById(R.id.more);
			editcomment = (ImageView)this.findViewById(R.id.edit_comment);
			//comments = (ListView)this.findViewById(R.id.comments);
		}
		//初始化地图
		public void initMap(){
	    	//获取地图控件引用
	        baiduMap = mapView.getMap();
	        //默认显示普通地图
	        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	        // 开启定位图层
	        baiduMap.setMyLocationEnabled(true);
	        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
	        //配置定位SDK参数
	        initLocation();
	        mLocationClient.registerLocationListener(myListener);//注册监听函数
	        //开启定位
	        mLocationClient.start();
	        //图片点击事件，回到定位点
	        mLocationClient.requestLocation();
	    }
		//我的位置初始化
		public void initLocation(){
	    	mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
	    	LocationClientOption option = new LocationClientOption();
	        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
	        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
	        int span = 1000;
	        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
	        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
	        option.setOpenGps(true);//可选，默认false,设置是否使用gps
	        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
	        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
	        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
	        option.setIgnoreKillProcess(false);
	        option.setOpenGps(true); // 打开gps
	        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
	        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
	        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
	        mLocationClient.setLocOption(option);

	    }
		
		//个人定位
		public class MyLocationListener implements BDLocationListener{

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
	            // 构造定位数据
	            MyLocationData locData = new MyLocationData.Builder()
	                    .accuracy(location.getRadius())
	                    // 此处设置开发者获取到的方向信息，顺时针0-360
	                    .latitude(hotelbean.getLatlng())
	                    .longitude(hotelbean.getLonglng()).build();
	            	// 设置定位数据
	            	baiduMap.setMyLocationData(locData);
	            if(isFirstIn){
	            	isFirstIn = false;
	                LatLng lagl = new LatLng(hotelbean.getLatlng(),hotelbean.getLonglng());
	        		MapStatus.Builder builder = new MapStatus.Builder();
	                builder.target(lagl).zoom(18.0f);
	                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
	            }
			
			}
		}
	
		public void setDialog(final int i){
			final Dialog dialog = new Dialog(this);
			RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
		                R.layout.activity_navigation_date_dialog, null);
			final CalendarView clview = (CalendarView)root.findViewById(R.id.date);
			final TextView submit = (TextView)root.findViewById(R.id.submit);
			final TextView cancel = (TextView)root.findViewById(R.id.cancel);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            long startDay = date.getTime()-Long.parseLong("43200000");
            clview.setMinDate(startDay);
            calendar.add(Calendar.DATE, 45);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            long lateTime = calendar.getTimeInMillis();
            clview.setMaxDate(lateTime);
			root.findViewById(R.id.date);
			root.findViewById(R.id.submit);
			dialog.setContentView(root);
			dialog.setTitle("选择时间");
			Window dialogWindow = dialog.getWindow();
		    WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		    lp.x = 0; // 新位置X坐标
		    lp.y = 0; // 新位置Y坐标
		    lp.width = 800; // 宽度
		    root.measure(0, 0);
		    lp.height = 800;
		    lp.alpha = 9f; // 透明度
		    dialogWindow.setAttributes(lp);
		    dialog.show();
		    submit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					long ddd = clview.getDate();
					getTime(i,ddd);
					dialog.dismiss();
				}
		    	
		    });
		    cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
		    	
		    });
		}
		
		public void setdialog(final int i){
			final Dialog dialog = new Dialog(this);
			RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
		                R.layout.activity_navigation_date_dialog, null);
			final CalendarView clview = (CalendarView)root.findViewById(R.id.date);
			final TextView submit = (TextView)root.findViewById(R.id.submit);
			final TextView cancel = (TextView)root.findViewById(R.id.cancel);
            Calendar calendar = Calendar.getInstance();
            clview.setMinDate(time);
            calendar.add(Calendar.DATE, 45);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            long lateTime = calendar.getTimeInMillis();
            clview.setMaxDate(lateTime);
			root.findViewById(R.id.date);
			root.findViewById(R.id.submit);
			dialog.setContentView(root);
			dialog.setTitle("选择时间");
			Window dialogWindow = dialog.getWindow();
		    WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		    lp.x = 0; // 新位置X坐标
		    lp.y = 0; // 新位置Y坐标
		    lp.width = 800; // 宽度
		    root.measure(0, 0);
		    lp.height = 800;
		    lp.alpha = 9f; // 透明度
		    dialogWindow.setAttributes(lp);
		    dialog.show();
		    submit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					long ddd = clview.getDate();
					getTime(i,ddd);
					dialog.dismiss();
				}
		    	
		    });
		    cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
		    	
		    });
		}
		
		public void getTime(int i,long t){
			Date date = new Date(t);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
			if(i == 1)
			{
				stime.setText(simpleDateFormat.format(date));
				etime.setText(simpleDateFormat.format(date));
				time = t;
				ttime = t;
			}
			if(i == 2)
			{
				etime.setText(simpleDateFormat.format(date));
				ttime = t;
			}
		}
		
		public void setComment(final ArrayList<OrderCommentBean> cList){
			OrderCommentBean cBean = cList.get(0);
			String img = Tools.baseUrl+cBean.getUserimg();
			Picasso.with(HotelDetail.this).load(img.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(userimg);
			username.setText(cBean.getUsername());
			usertime.setText(cBean.getTime());
			comment.setText(cBean.getMessage());
			more.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(HotelDetail.this,Comments.class);
					Gson gson = new Gson();
					String str = gson.toJson(cList);
					intent.putExtra("comment", str);
					startActivity(intent);
				}
			
			});
			
		}
		/*public class mAdapter extends BaseAdapter{

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
				View view = View.inflate(HotelDetail.this, R.layout.list_item_comments, null);
				ImageView userimg = (ImageView)view.findViewById(R.id.userimg);
				TextView username = (TextView)view.findViewById(R.id.username);
				TextView cTime = (TextView)view.findViewById(R.id.time);
				TextView cMessage = (TextView)view.findViewById(R.id.message);
				OrderCommentBean cBean = a.get(arg0);
				String img = Tools.baseUrl+cBean.getUserimg();
				Picasso.with(HotelDetail.this).load(img.trim().toString())
				.placeholder(R.drawable.ic_launcher).into(userimg);
				username.setText(cBean.getUsername());
				cTime.setText(cBean.getTime());
				cMessage.setText(cBean.getMessage());
				return view;
			}
			
		}*/
		public void check(ArrayList<OrderCommentBean> a1,ArrayList<OrderCommentBean> a2){
			for(int i=0;i<a1.size();i++){
				OrderCommentBean cBean = a1.get(i);
				String str1 = cBean.getName();
				String str2 = hotelbean.getName();
				if(str1.equals(str2)){
					a2.add(cBean);
				}
			}
		}
				
}

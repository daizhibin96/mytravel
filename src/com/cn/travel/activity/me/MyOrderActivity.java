package com.cn.travel.activity.me;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.smssdk.SMSSDK;

import com.cn.travel.activity.navigation.HotelOrder;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.bean.Order;
import com.cn.travel.bean.PointBean;
import com.cn.travel.bean.PointOrderBean;
import com.cn.travel.domain.BaseActivity;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.RegisterActivity.LocationReceiver;
import com.cn.travel.service.me.OrderPageService;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrderActivity extends BaseActivity {
	private TextView title;
	private LocationReceiver locationReceiver;
	private ArrayList<HotelOrderBean> hotelList = new ArrayList<HotelOrderBean>();
	private ArrayList<PointOrderBean> pointList = new ArrayList<PointOrderBean>();
	private  boolean HotelOk=false;
	private  boolean PointOk=false;
	private ArrayList<Order> orderList = new ArrayList<Order>();
	private ArrayList<HotelBean> HotelList = new ArrayList<HotelBean>();
	private HotelBean hb=new HotelBean();
	private ArrayList<PointBean> PointList = new ArrayList<PointBean>();	
	private PointBean pb=new PointBean();
	private  boolean FindOk=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myorder);

		locationReceiver = new LocationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("location.hotel");
		filter.addAction("location.point");
		filter.addAction("location.findhotel");
		filter.addAction("location.findpoint");
		registerReceiver(locationReceiver, filter);

		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("全部订单");
		initOrder();
		
		if(HotelOk&&PointOk){
			Log.i("orderList++++++++++++..",orderList.toString());
		Log.e("最后。。。。。。。。。。。。。。。。。","klfdjfoewhfoiedjflkjdlfj最后");
	
		}
	}

	private void initOrder() {
		// TODO Auto-generated method stub
		if (false) {
			Order order1 = new Order(1,"images/hotel.png", "维也纳酒店", "2018年10月5日 15:13:07",
					"500元", 1);
			orderList.add(order1);
			Order order2 = new Order(33,"images/hotel.png", "八方大酒店", "2018年10月2日 10:13:07",
					"200元", 1);
			orderList.add(order2);
			Order order3 = new Order(23,"images/tour.png", "松湖烟雨入场票", "2018年2月20日 15:13:07",
					"50元", 2);
			orderList.add(order3);
			Order order4 = new Order(876,"images/tour.png", "人山人海晚餐票", "2018年10月1日 15:13:07",
					"150元", 2);
			orderList.add(order4);
			Order order6 = new Order(785,"images/tour.png", "人民公园", "2018年10月1日 10:13:08",
					"免费", 2);
			orderList.add(order6);
			//Log.i("orderList>>>>>>>>>..",orderList.toString());

		}
		
		OrderPageService.GetHotelOrder(IDclass.ID, getBaseContext());
		OrderPageService.GetPointOrder(IDclass.ID, getBaseContext());
//		OrderPageService.findHotel("东莞", MyOrderActivity.this);
//		OrderPageService.findPoint("东莞", MyOrderActivity.this);
		
	}

	public class LocationReceiver extends BroadcastReceiver {
		// 必须要重载的方法，用来监听是否有广播发送
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String intentAction = arg1.getAction();
			if (intentAction.equals("location.hotel")) {
				String aaa = arg1.getStringExtra("listhotelorder");
				Log.e("广播aaaa", aaa);
				Gson gson1 = new Gson();
				hotelList = gson1.fromJson(aaa,
						new TypeToken<ArrayList<HotelOrderBean>>() {
						}.getType());
				Log.e("hotelOrder", hotelList.toString());
				for(int i = 0;i < hotelList.size(); i++){
				      Order order55=new Order(hotelList.get(i).getId(),"images/hotel.png", hotelList.get(i).getHotel(),
				    		  hotelList.get(i).getIntime(), hotelList.get(i).getPrice(), 1);
				      Gson gsons=new Gson();
						String str=gsons.toJson(order55);
				      orderList.add(order55);					
				     
				    }
				HotelOk=true;
			}
			
			if (intentAction.equals("location.point")) {
				String p = arg1.getStringExtra("listpointorder");
				Log.e("广播pppppp", p);
				Gson gson1 = new Gson();
				pointList = gson1.fromJson(p,
						new TypeToken<ArrayList<PointOrderBean>>() {
						}.getType());
				Log.e("PointOrder", pointList.toString());
				for(int i = 0;i < pointList.size(); i++){
				      Order order999=new Order(pointList.get(i).getId(),"images/tour.png",pointList.get(i).getTourist(),
				    		  pointList.get(i).getDate(), pointList.get(i).getPrice(), 2);
				      Gson gsons=new Gson();
						String str=gsons.toJson(order999);
						//Log.e("门票单个", pointList.get(i).getTime());
				      orderList.add(order999);
				     
				    }
				PointOk=true;
			}
			if(FindOk){
				OrderPageService.findHotel("东莞", MyOrderActivity.this);
				OrderPageService.findPoint("东莞", MyOrderActivity.this);
				FindOk=false;
			}
			CompareOrderTime co=new CompareOrderTime();
			Collections.sort(orderList,co);
			
			if (intentAction.equals("location.findhotel")) {
				String tempList = arg1.getStringExtra("findhotel");
				Log.i("广播22222222",tempList);
				Gson gson2 = new Gson();
				HotelList = gson2.fromJson(tempList,
						new TypeToken<ArrayList<HotelBean>>() {
						}.getType());
				Log.i("hotelOrder", HotelList.toString());
				for (int i = 0; i < HotelList.size(); i++) {
					for (int j = 0; j < orderList.size(); j++) {
						if (HotelList.get(i).getName()
								.equals(orderList.get(j).getOrdername())) {
							orderList.get(j).setImgePic(HotelList.get(i).getImage());
						}
					}
				}
			}

			if (intentAction.equals("location.findpoint")) {
				String tempList = arg1.getStringExtra("findpoint");
				Log.i("广播33333Order",tempList);
				Gson gson3 = new Gson();
				PointList = gson3.fromJson(tempList,
						new TypeToken<ArrayList<PointBean>>() {
						}.getType());
				Log.i("PointOrder", PointList.toString());
				for (int i = 0; i < PointList.size(); i++) {
					for (int j = 0; j < orderList.size(); j++) {
						if (PointList.get(i).getName()
								.equals(orderList.get(j).getOrdername())) {
							orderList.get(j).setImgePic(PointList.get(i).getImage());
						}
					}
				}
			}
			OrderAdapter adapter = new OrderAdapter(MyOrderActivity.this,
					R.layout.list_item_order, orderList);
			ListView listView = (ListView) findViewById(R.id.list_view233);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							Order order = orderList.get(position);
							//Toast.makeText(MyOrderActivity.this, order.getOrdername(),Toast.LENGTH_SHORT).show();
							if (order.getOrdertype() == 1) {
								for(int i = 0;i < hotelList.size(); i++){
									if(hotelList.get(i).getId()==(order.getId())){
										HotelOrderBean hob=new HotelOrderBean();
										hob=hotelList.get(i);
										Gson gsonH=new Gson();
										String str=gsonH.toJson(hob);
										Intent intent1 = new Intent(MyOrderActivity.this,
												HotelOrderPageActivity.class);	
										intent1.putExtra("hotelorder", str);
										startActivity(intent1);
										//finish();
									}
									//Toast.makeText(MyOrderActivity.this, "假信息",Toast.LENGTH_SHORT).show();
									
								}
								//OrderPageService.findHotel("东莞",MyOrderActivity.this);
							}
							if (order.getOrdertype() == 2) {
								for(int i = 0;i < pointList.size(); i++){
									if(pointList.get(i).getId()==(order.getId())){
										PointOrderBean pob=new PointOrderBean();
										pob=pointList.get(i);
										Gson gsonP=new Gson();
										String str=gsonP.toJson(pob);
										Intent intent1 = new Intent(MyOrderActivity.this,
												TicketOrderPageActivity.class);	
										intent1.putExtra("pointorder", str);
										startActivity(intent1);
										//finish();
									}
									//Toast.makeText(MyOrderActivity.this, "假信息",Toast.LENGTH_SHORT).show();
								}
							}
						}
			
					});
		}
	}
	class CompareOrderTime implements Comparator<Order>{
		//按照订单日期进行排序
		@Override
		public int compare(Order p1, Order p2) {
			// TODO Auto-generated method stub
			return p2.getOrdertime().compareTo(p1.getOrdertime());
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(locationReceiver);
		super.onDestroy();
	}
}

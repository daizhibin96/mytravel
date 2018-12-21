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
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportOrderActivity extends BaseActivity {
	private TextView title;
	private LocationReceiver locationReceiver;
	private ArrayList<HotelOrderBean> hotelList = new ArrayList<HotelOrderBean>();
	private ArrayList<PointOrderBean> pointList = new ArrayList<PointOrderBean>();
	private  boolean HotelOk=false;
	private  boolean PointOk=false;
	private  boolean FindOk=true;
	private ArrayList<Order> orderList = new ArrayList<Order>();
	private ArrayList<HotelBean> HotelList = new ArrayList<HotelBean>();
	private HotelBean hb=new HotelBean();
	private ArrayList<PointBean> PointList = new ArrayList<PointBean>();	
	private PointBean pb=new PointBean();

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
		title.setText("点评订单");
		initOrder();
		
		if((HotelOk==true)&&(PointOk==true)){
			Log.i("orderList++++++++++++..",orderList.toString());
		Log.e("最后。。。。。。。。。。。。。。。。。","klfdjfoewhfoiedjflkjdlfj最后");
	
		}
	}

	private void initOrder() {
		// TODO Auto-generated method stub
//		OrderPageService.findHotel("东莞", ReportOrderActivity.this);
//		OrderPageService.findPoint("东莞", ReportOrderActivity.this);
		OrderPageService.GetHotelOrder(IDclass.ID, getBaseContext());
		OrderPageService.GetPointOrder(IDclass.ID, getBaseContext());
		
		
	}

	public class LocationReceiver extends BroadcastReceiver {
		// 必须要重载的方法，用来监听是否有广播发送
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			Time time = new Time();
			time.setToNow();
			String NowTime =  String.valueOf(time.year)+"-"
					+ String.valueOf(time.month + 1) +"-"+ String.valueOf(time.monthDay)+ "";
			
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
					String temp=hotelList.get(i).getOuttime();
					String temp1=temp.replaceAll("年", "-");
					String s1=temp1.replaceAll("月", "-");
					String s2=s1.replaceAll("日", "");
					Log.i("order55time....",s2);
					if(hotelList.get(i).getState().equals("未点评")&&
							(hotelList.get(i).getPay().equals("已支付"))&&(NowTime.compareTo(s2)>0)){
				      Order order55=new Order(hotelList.get(i).getId(),"images/hotel.png", hotelList.get(i).getHotel(),
				    		  hotelList.get(i).getIntime(), hotelList.get(i).getPrice(), 1);
				      orderList.add(order55);					
					}
				    }
				
			}
			HotelOk=true;
			if (intentAction.equals("location.point")) {
				String p = arg1.getStringExtra("listpointorder");
				Log.e("广播pppppp", p);
				Gson gson1 = new Gson();
				pointList = gson1.fromJson(p,
						new TypeToken<ArrayList<PointOrderBean>>() {
						}.getType());
				Log.e("PointOrder", pointList.toString());
				for(int i = 0;i < pointList.size(); i++){
					String tempP=pointList.get(i).getDate();
					String temp1=tempP.replaceAll("年", "-");
					String s1=temp1.replaceAll("月", "-");
					String s2=s1.replaceAll("日", "");
					Log.i("order55time....",s2);
					if((pointList.get(i).getState().equals("未点评"))&&
							(pointList.get(i).getPay().equals("已支付"))&&(NowTime.compareTo(s2)>0)){
				      Order order999=new Order(pointList.get(i).getId(),"images/tour.png",pointList.get(i).getTourist(),
				    		  pointList.get(i).getDate(), pointList.get(i).getPrice(), 2);				      
				      orderList.add(order999);
						}
				    }
				
			}
			PointOk=true;
			if(FindOk){
				OrderPageService.findHotel("东莞", ReportOrderActivity.this);
				OrderPageService.findPoint("东莞", ReportOrderActivity.this);
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
			
			//接收point链表
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
			OrderAdapter adapter = new OrderAdapter(ReportOrderActivity.this,
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
										Intent intent1 = new Intent(ReportOrderActivity.this,
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
										Intent intent1 = new Intent(ReportOrderActivity.this,
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

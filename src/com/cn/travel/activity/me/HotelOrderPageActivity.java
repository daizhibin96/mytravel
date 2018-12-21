package com.cn.travel.activity.me;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.travel.activity.me.MyOrderActivity.LocationReceiver;
import com.cn.travel.activity.navigation.EditComment;
import com.cn.travel.activity.navigation.Map;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.bean.Order;
import com.cn.travel.bean.PointOrderBean;
import com.cn.travel.domain.BaseActivity;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.domain.RegisterActivity;
import com.cn.travel.service.me.OrderPageService;
import com.cn.travel.service.me.UpdateOrderState;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HotelOrderPageActivity extends BaseActivity implements
		OnClickListener {
	private LocationReceiver locationReceiver2;
	private TextView title;
	private TextView ordername, orderid, ordertime, orderprice, orderpay,
			orderphone, roomtype, roomnum, checkintime, checkouttime,
			hotelAddress, Gopay, Delete, cancalOrder;
	private HotelOrderBean ho = new HotelOrderBean();
	private HotelBean hb = new HotelBean();
	private ArrayList<HotelBean> HotelList = new ArrayList<HotelBean>();
	private String mflagH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hotel_order_page);
		Intent intent = getIntent();
		String h = intent.getStringExtra("hotelorder");
		ho = new Gson().fromJson(h, HotelOrderBean.class);

		locationReceiver2 = new LocationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("location.findhotel");
		registerReceiver(locationReceiver2, filter);
		OrderPageService.findHotel("东莞", HotelOrderPageActivity.this);

		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("酒店订单详情");
		ordername = (TextView) this.findViewById(R.id.Rordername);
		orderid = (TextView) this.findViewById(R.id.Rorderid);
		ordertime = (TextView) this.findViewById(R.id.Rordertime);
		orderprice = (TextView) this.findViewById(R.id.Rorderprice);
		orderpay = (TextView) this.findViewById(R.id.Rorderpay);
		orderphone = (TextView) this.findViewById(R.id.Rorderphone);
		roomnum = (TextView) this.findViewById(R.id.Rroom_number);
		roomtype = (TextView) this.findViewById(R.id.Rroom_type);
		checkintime = (TextView) this.findViewById(R.id.Rhotel_intime);
		checkouttime = (TextView) this.findViewById(R.id.Rhotel_outtime);
		hotelAddress = (TextView) this.findViewById(R.id.Rhotel_address);
		Gopay = (TextView) this.findViewById(R.id.R_to_pay);
		Delete = (TextView) this.findViewById(R.id.R_delete_order);
		cancalOrder = (TextView) this.findViewById(R.id.R_cancal_order);
	}

	private void setit() {
		// TODO Auto-generated method stub
		ordername.setText(ho.getHotel());
		orderid.setText(ho.getId() + "");
		ordertime.setText(ho.getTime());
		orderprice.setText(ho.getPrice());
		if(ho.getPay().equals("已删除")){
			orderpay.setText("已取消");
		}else{
			orderpay.setText(ho.getPay());
		}
		orderphone.setText(ho.getPhone());
		roomtype.setText(ho.getRoomtype());
		roomnum.setText(ho.getRoomnum() + "");
		checkintime.setText(ho.getIntime());
		checkouttime.setText(ho.getOuttime());
		hotelAddress.setText(hb.getAddress());

		hotelAddress.setOnClickListener(this);
		// 获取当前时间
		Time time = new Time();
		time.setToNow();
		String NowTime = String.valueOf(time.year) + "-"
				+ String.valueOf(time.month + 1) + "-"
				+ String.valueOf(time.monthDay) + "";
		Log.i("nowTime>>.....", NowTime);
		// 获取出酒店时间
		String temp = ho.getOuttime();
		String temp1 = temp.replaceAll("年", "-");
		String s1 = temp1.replaceAll("月", "-");
		String s2 = s1.replaceAll("日", "");
		Log.i("orderOuttime....", s2);

		if (ho.getPay().equals("已删除")) {
			Delete.setVisibility(View.VISIBLE);
			Delete.setText("订单已取消");
		} else {
			if (NowTime.compareTo(s2) < 0) {
				if (ho.getPay().equals("待支付")) { // 没过期的未支付订单
					Gopay.setVisibility(View.VISIBLE);
					Gopay.setOnClickListener(this);
					mflagH = "待支付";
					cancalOrder.setVisibility(View.VISIBLE);
					cancalOrder.setOnClickListener(this);
				}
				if (ho.getPay().equals("已支付") && (ho.getState().equals("未点评"))) { // 已完成待使用
					Gopay.setVisibility(View.VISIBLE);
					Gopay.setText("出示消费码");
					Gopay.setOnClickListener(this);
					mflagH = "使用";
				}
			} else {
				if (ho.getPay().equals("待支付")) { // 已经过期的未支付订单
					Delete.setVisibility(View.VISIBLE);
					Delete.setText("订单已过期，删除");
					Delete.setOnClickListener(this);
				}
				if (ho.getPay().equals("已支付") && (ho.getState().equals("未点评"))) { // 已完成未
					Gopay.setVisibility(View.VISIBLE);
					Gopay.setText("点评");
					Gopay.setOnClickListener(this);
					mflagH = "待点评";
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Rhotel_address:
			// Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(HotelOrderPageActivity.this, Map.class);
			Gson gson = new Gson();
			double Lat = hb.getLatlng();
			double Lng = hb.getLonglng();
			String lat = gson.toJson(Lat);
			String lng = gson.toJson(Lng);
			intent.putExtra("latlng", lat);
			intent.putExtra("longlng", lng);
			startActivity(intent);
			break;

		case R.id.R_delete_order:
			// Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
			OrderPageService.DeleteOrder(ho.getId(), "酒店",
					HotelOrderPageActivity.this);
			finish();
			break;

		case R.id.R_cancal_order:
			// Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
			OrderPageService.DeleteOrder(ho.getId(), "酒店",
					HotelOrderPageActivity.this);
			finish();
			break;

		case R.id.R_to_pay:
			if (mflagH.equals("待支付")) {
				if (mflagH.equals("待支付")) {
					setdialog();
				}
				if (mflagH.equals("待点评")) {
					Intent intent1 = new Intent(HotelOrderPageActivity.this,
							EditComment.class);
					String hotelname = hb.getName();
					String cType = "酒店";
					String userName = IDclass.UserName;
					int useriD = ho.getId();
					String orderid = Integer.toString(useriD);
					intent1.putExtra("orderid", orderid);
					intent1.putExtra("hotelname", hotelname);
					intent1.putExtra("cType", cType);
					startActivity(intent1);
				}
			}
			if (mflagH.equals("待点评")) {
				Intent intent1 = new Intent(HotelOrderPageActivity.this,EditComment.class);
				String hotelname = hb.getName();
				String cType = "酒店";
				String userName = IDclass.UserName;
				int useriD = ho.getId();
				String orderid = Integer.toString(useriD);
				intent1.putExtra("orderid", orderid);
				intent1.putExtra("hotelname", hotelname);
				intent1.putExtra("cType", cType);
				startActivity(intent1);
			}
			if (mflagH.equals("使用")) {
				// Intent ToPay=new
				// Intent(HotelOrderPageActivity.this,RegisterActivity.class);
				// ToPay.putExtra("orderId", ho.getId());
				// startActivity(ToPay);
				dialogCore dialogcore = new dialogCore(
						HotelOrderPageActivity.this);
				dialogcore.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// dialogcore.setTitle("消费码");
				dialogcore.show();
				// Toast.makeText(HotelOrderPageActivity.this,
				// "消费码加载中...",Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	public class LocationReceiver extends BroadcastReceiver {
		// 必须要重载的方法，用来监听是否有广播发送
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String intentAction = arg1.getAction();
			if (intentAction.equals("location.findhotel")) {
				String tempList = arg1.getStringExtra("findhotel");
				Log.i("广播22222222", tempList);
				Gson gson2 = new Gson();
				HotelList = gson2.fromJson(tempList,
						new TypeToken<ArrayList<HotelBean>>() {
						}.getType());
				Log.i("hotelOrder", HotelList.toString());
				for (int i = 0; i < HotelList.size(); i++) {
					if (HotelList.get(i).getName().equals(ho.getHotel())) {
						hb = HotelList.get(i);
						setit();
					}
				}
			}
		}
	}

	public void setdialog() {
		final Dialog dialog = new Dialog(this);
		RelativeLayout root = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.activity_navigation_payqr, null);
		final Button cancel = (Button) root.findViewById(R.id.pay_cancel);
		final Button after = (Button) root.findViewById(R.id.pay_after);
		final Button pay = (Button) root.findViewById(R.id.pay_pay);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(root);
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
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		});
		after.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		});
		pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				String sbean = gson.toJson(ho);
				int type = 1;
				UpdateOrderState update = new UpdateOrderState();
				update.updateOrder(HotelOrderPageActivity.this, sbean, type);
				Toast.makeText(HotelOrderPageActivity.this, "支付成功",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				HotelOrderPageActivity.this.finish();
			}

		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(locationReceiver2);
		super.onDestroy();
	}
}

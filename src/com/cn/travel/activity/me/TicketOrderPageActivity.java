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

import com.cn.travel.activity.navigation.EditComment;
import com.cn.travel.activity.navigation.Map;
import com.cn.travel.bean.PointBean;
import com.cn.travel.bean.PointOrderBean;
import com.cn.travel.bean.UserBean;
import com.cn.travel.domain.BaseActivity;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.domain.RegisterActivity;
import com.cn.travel.service.me.OrderPageService;
import com.cn.travel.service.me.UpdateOrderState;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TicketOrderPageActivity extends BaseActivity implements OnClickListener{
	private LocationReceiver locationReceiver3;
	private PointOrderBean po=new PointOrderBean();
	private PointBean pb=new PointBean();
	private ArrayList<PointBean> PointList = new ArrayList<PointBean>();
	private TextView title,ordername,ordertime,orderprice,orderpay,
	orderphone,roomnum,PointAddress,tourTime,Gpay,DeleteOrder,cancalOrder;
	private String mflag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ticket_order_page);
		Intent intent=getIntent();
		String p=intent.getStringExtra("pointorder");	
		po= new Gson().fromJson(p,PointOrderBean.class);
		//Toast.makeText(getBaseContext(), po.getTime(), Toast.LENGTH_SHORT).show();
		
		locationReceiver3 = new LocationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("location.findpoint");
		registerReceiver(locationReceiver3, filter);
		OrderPageService.findPoint("东莞",TicketOrderPageActivity.this);
		init();
		
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("景点订单详情");
		ordername= (TextView) this.findViewById(R.id.Tordername);
		ordertime= (TextView) this.findViewById(R.id.Tordertime);
		orderprice= (TextView) this.findViewById(R.id.Torderprice);
		orderpay= (TextView) this.findViewById(R.id.Torderpay);
		orderphone=(TextView) this.findViewById(R.id.Torderphone);
		roomnum= (TextView) this.findViewById(R.id.T_number);		
		PointAddress=(TextView) this.findViewById(R.id.T_address);
		tourTime=(TextView) this.findViewById(R.id.tour_time);
		Gpay=(TextView) this.findViewById(R.id.TT_to_pay);
		DeleteOrder=(TextView)findViewById(R.id.TT_delete_order);
		cancalOrder=(TextView)findViewById(R.id.TT_cancal_order);
	}

	private void setit() {
		// TODO Auto-generated method stub
		ordername.setText(po.getTourist());
		ordertime.setText(po.getTime());
		orderprice.setText(po.getPrice());
		if(po.getPay().equals("已删除")){
			orderpay.setText("已取消");
		}else{
			orderpay.setText(po.getPay());
		}		
		orderphone.setText(po.getPhone());
		roomnum.setText(po.getNum() + "");
		PointAddress.setText(pb.getAddress());
		tourTime.setText(po.getDate());
		
		PointAddress.setOnClickListener(this);
		

		// 获取当前时间
		Time time = new Time();
		time.setToNow();
		String NowTime = String.valueOf(time.year) + "-"
				+ String.valueOf(time.month + 1) + "-"
				+ String.valueOf(time.monthDay) + "";
		Log.i("nowTime>>.....", NowTime);
		// 获取游玩时间
		String temp = po.getDate();
		String temp1 = temp.replaceAll("年", "-");
		String s1 = temp1.replaceAll("月", "-");
		String s2 = s1.replaceAll("日", "");
		Log.i("orderOuttime....", s2);
		if (po.getPay().equals("已删除")) {
			DeleteOrder.setVisibility(View.VISIBLE);
			DeleteOrder.setText("订单已取消");
		} else {
			if (NowTime.compareTo(s2) < 0) {
				if (po.getPay().equals("待支付")) { // 没过期的未支付订单
					Gpay.setVisibility(View.VISIBLE);
					Gpay.setOnClickListener(this);
					mflag = "待支付";
					cancalOrder.setVisibility(View.VISIBLE);
					cancalOrder.setOnClickListener(this);
				}
				if (po.getPay().equals("已支付") && (po.getState().equals("未点评"))) { // 已支付待使用
					Gpay.setVisibility(View.VISIBLE);
					Gpay.setText("出示消费码");
					Gpay.setOnClickListener(this);
					mflag = "使用";
				}
			} else {
				if (po.getPay().equals("待支付")) { // 已经过期的未支付订单
					DeleteOrder.setVisibility(View.VISIBLE);
					DeleteOrder.setText("订单已过期，删除");
					DeleteOrder.setOnClickListener(this);
				}
				if (po.getPay().equals("已支付") && (po.getState().equals("未点评"))) { // 已使用的未点评订单
					Gpay.setVisibility(View.VISIBLE);
					Gpay.setText("点评");
					Gpay.setOnClickListener(this);
					mflag = "待点评";
				}
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.T_address:
				//Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(TicketOrderPageActivity.this,Map.class);
				Gson gson = new Gson();
				double Lat = pb.getLatlng();
				double Lng = pb.getLonglng();
				String lat = gson.toJson(Lat);
				String lng = gson.toJson(Lng);
				intent.putExtra("latlng", lat);
				intent.putExtra("longlng", lng);
				startActivity(intent);
				break;
			case R.id.TT_delete_order:
				//Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
				OrderPageService.DeleteOrder(po.getId(), "景点", TicketOrderPageActivity.this);
				finish();
				break;
			case R.id.TT_cancal_order:
				//Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
				OrderPageService.DeleteOrder(po.getId(), "景点", TicketOrderPageActivity.this);
				finish();
				break;
			case R.id.TT_to_pay:
				//Toast.makeText(this, "地址", Toast.LENGTH_SHORT).show();
				if(mflag.equals("待支付")){
					setdialog();
				}
				if(mflag.equals("待点评")){
					Intent intent1 = new Intent(TicketOrderPageActivity.this,EditComment.class);
					String hotelname = po.getTourist();
					String cType = "景点";
					String userName = IDclass.UserName;
					int useriD = po.getId();
					String orderid = Integer.toString(useriD);
					intent1.putExtra("orderid", orderid);
					intent1.putExtra("hotelname", hotelname);
					intent1.putExtra("cType", cType);
					startActivity(intent1);
				}
				if(mflag.equals("使用")){
//					Intent ToPay=new Intent(TicketOrderPageActivity.this,RegisterActivity.class);
//					ToPay.putExtra("order", po.getTime());
//					startActivity(ToPay);
					dialogCore dialogcore = new dialogCore(TicketOrderPageActivity.this);
					dialogcore.requestWindowFeature(Window.FEATURE_NO_TITLE);
//					dialogcore.setTitle("消费码");
					dialogcore.show();
					//Toast.makeText(TicketOrderPageActivity.this, "消费码加载中...",Toast.LENGTH_SHORT).show();
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
			if (intentAction.equals("location.findpoint")) {
				String tempList = arg1.getStringExtra("findpoint");
				Log.i("广播333333",tempList);
				Gson gson2 = new Gson();
				PointList = gson2.fromJson(tempList,
						new TypeToken<ArrayList<PointBean>>() {
						}.getType());
				Log.i("PointOrder", PointList.toString());
				for(int i = 0;i < PointList.size(); i++){					
				     if(PointList.get(i).getName().equals(po.getTourist())){
				    	 pb=PointList.get(i);
				    	 setit();
				     }
				    }
				}
			}
		}
	
	public void setdialog(){
		final Dialog dialog = new Dialog(this);
		RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
	                R.layout.activity_navigation_payqr, null);
		final Button cancel = (Button)root.findViewById(R.id.pay_cancel);
		final Button after = (Button)root.findViewById(R.id.pay_after);
		final Button pay = (Button)root.findViewById(R.id.pay_pay);
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
	    cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
	    
	    });
	    after.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
	    	
	    });
	    pay.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				String sbean = gson.toJson(po);
				int type = 2;
				UpdateOrderState update = new UpdateOrderState();
				update.updateOrder(TicketOrderPageActivity.this, sbean, type);
				Toast.makeText(TicketOrderPageActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				TicketOrderPageActivity.this.finish();
			}
	    	
	    });
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(locationReceiver3);
		super.onDestroy();
	}
}

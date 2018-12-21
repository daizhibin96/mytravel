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
		OrderPageService.findPoint("��ݸ",TicketOrderPageActivity.this);
		init();
		
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("���㶩������");
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
		if(po.getPay().equals("��ɾ��")){
			orderpay.setText("��ȡ��");
		}else{
			orderpay.setText(po.getPay());
		}		
		orderphone.setText(po.getPhone());
		roomnum.setText(po.getNum() + "");
		PointAddress.setText(pb.getAddress());
		tourTime.setText(po.getDate());
		
		PointAddress.setOnClickListener(this);
		

		// ��ȡ��ǰʱ��
		Time time = new Time();
		time.setToNow();
		String NowTime = String.valueOf(time.year) + "-"
				+ String.valueOf(time.month + 1) + "-"
				+ String.valueOf(time.monthDay) + "";
		Log.i("nowTime>>.....", NowTime);
		// ��ȡ����ʱ��
		String temp = po.getDate();
		String temp1 = temp.replaceAll("��", "-");
		String s1 = temp1.replaceAll("��", "-");
		String s2 = s1.replaceAll("��", "");
		Log.i("orderOuttime....", s2);
		if (po.getPay().equals("��ɾ��")) {
			DeleteOrder.setVisibility(View.VISIBLE);
			DeleteOrder.setText("������ȡ��");
		} else {
			if (NowTime.compareTo(s2) < 0) {
				if (po.getPay().equals("��֧��")) { // û���ڵ�δ֧������
					Gpay.setVisibility(View.VISIBLE);
					Gpay.setOnClickListener(this);
					mflag = "��֧��";
					cancalOrder.setVisibility(View.VISIBLE);
					cancalOrder.setOnClickListener(this);
				}
				if (po.getPay().equals("��֧��") && (po.getState().equals("δ����"))) { // ��֧����ʹ��
					Gpay.setVisibility(View.VISIBLE);
					Gpay.setText("��ʾ������");
					Gpay.setOnClickListener(this);
					mflag = "ʹ��";
				}
			} else {
				if (po.getPay().equals("��֧��")) { // �Ѿ����ڵ�δ֧������
					DeleteOrder.setVisibility(View.VISIBLE);
					DeleteOrder.setText("�����ѹ��ڣ�ɾ��");
					DeleteOrder.setOnClickListener(this);
				}
				if (po.getPay().equals("��֧��") && (po.getState().equals("δ����"))) { // ��ʹ�õ�δ��������
					Gpay.setVisibility(View.VISIBLE);
					Gpay.setText("����");
					Gpay.setOnClickListener(this);
					mflag = "������";
				}
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.T_address:
				//Toast.makeText(this, "��ַ", Toast.LENGTH_SHORT).show();
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
				//Toast.makeText(this, "��ַ", Toast.LENGTH_SHORT).show();
				OrderPageService.DeleteOrder(po.getId(), "����", TicketOrderPageActivity.this);
				finish();
				break;
			case R.id.TT_cancal_order:
				//Toast.makeText(this, "��ַ", Toast.LENGTH_SHORT).show();
				OrderPageService.DeleteOrder(po.getId(), "����", TicketOrderPageActivity.this);
				finish();
				break;
			case R.id.TT_to_pay:
				//Toast.makeText(this, "��ַ", Toast.LENGTH_SHORT).show();
				if(mflag.equals("��֧��")){
					setdialog();
				}
				if(mflag.equals("������")){
					Intent intent1 = new Intent(TicketOrderPageActivity.this,EditComment.class);
					String hotelname = po.getTourist();
					String cType = "����";
					String userName = IDclass.UserName;
					int useriD = po.getId();
					String orderid = Integer.toString(useriD);
					intent1.putExtra("orderid", orderid);
					intent1.putExtra("hotelname", hotelname);
					intent1.putExtra("cType", cType);
					startActivity(intent1);
				}
				if(mflag.equals("ʹ��")){
//					Intent ToPay=new Intent(TicketOrderPageActivity.this,RegisterActivity.class);
//					ToPay.putExtra("order", po.getTime());
//					startActivity(ToPay);
					dialogCore dialogcore = new dialogCore(TicketOrderPageActivity.this);
					dialogcore.requestWindowFeature(Window.FEATURE_NO_TITLE);
//					dialogcore.setTitle("������");
					dialogcore.show();
					//Toast.makeText(TicketOrderPageActivity.this, "�����������...",Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
	}
	public class LocationReceiver extends BroadcastReceiver {
		// ����Ҫ���صķ��������������Ƿ��й㲥����
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String intentAction = arg1.getAction();
			if (intentAction.equals("location.findpoint")) {
				String tempList = arg1.getStringExtra("findpoint");
				Log.i("�㲥333333",tempList);
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
	    WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
	    lp.x = 0; // ��λ��X����
	    lp.y = 0; // ��λ��Y����
	    lp.width = 800; // ���
	    root.measure(0, 0);
	    lp.height = 800;
	    lp.alpha = 9f; // ͸����
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
				Toast.makeText(TicketOrderPageActivity.this, "֧���ɹ�", Toast.LENGTH_SHORT).show();
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

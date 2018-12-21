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
		OrderPageService.findHotel("��ݸ", HotelOrderPageActivity.this);

		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("�Ƶ궩������");
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
		if(ho.getPay().equals("��ɾ��")){
			orderpay.setText("��ȡ��");
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
		// ��ȡ��ǰʱ��
		Time time = new Time();
		time.setToNow();
		String NowTime = String.valueOf(time.year) + "-"
				+ String.valueOf(time.month + 1) + "-"
				+ String.valueOf(time.monthDay) + "";
		Log.i("nowTime>>.....", NowTime);
		// ��ȡ���Ƶ�ʱ��
		String temp = ho.getOuttime();
		String temp1 = temp.replaceAll("��", "-");
		String s1 = temp1.replaceAll("��", "-");
		String s2 = s1.replaceAll("��", "");
		Log.i("orderOuttime....", s2);

		if (ho.getPay().equals("��ɾ��")) {
			Delete.setVisibility(View.VISIBLE);
			Delete.setText("������ȡ��");
		} else {
			if (NowTime.compareTo(s2) < 0) {
				if (ho.getPay().equals("��֧��")) { // û���ڵ�δ֧������
					Gopay.setVisibility(View.VISIBLE);
					Gopay.setOnClickListener(this);
					mflagH = "��֧��";
					cancalOrder.setVisibility(View.VISIBLE);
					cancalOrder.setOnClickListener(this);
				}
				if (ho.getPay().equals("��֧��") && (ho.getState().equals("δ����"))) { // ����ɴ�ʹ��
					Gopay.setVisibility(View.VISIBLE);
					Gopay.setText("��ʾ������");
					Gopay.setOnClickListener(this);
					mflagH = "ʹ��";
				}
			} else {
				if (ho.getPay().equals("��֧��")) { // �Ѿ����ڵ�δ֧������
					Delete.setVisibility(View.VISIBLE);
					Delete.setText("�����ѹ��ڣ�ɾ��");
					Delete.setOnClickListener(this);
				}
				if (ho.getPay().equals("��֧��") && (ho.getState().equals("δ����"))) { // �����δ
					Gopay.setVisibility(View.VISIBLE);
					Gopay.setText("����");
					Gopay.setOnClickListener(this);
					mflagH = "������";
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Rhotel_address:
			// Toast.makeText(this, "��ַ", Toast.LENGTH_SHORT).show();
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
			// Toast.makeText(this, "��ַ", Toast.LENGTH_SHORT).show();
			OrderPageService.DeleteOrder(ho.getId(), "�Ƶ�",
					HotelOrderPageActivity.this);
			finish();
			break;

		case R.id.R_cancal_order:
			// Toast.makeText(this, "��ַ", Toast.LENGTH_SHORT).show();
			OrderPageService.DeleteOrder(ho.getId(), "�Ƶ�",
					HotelOrderPageActivity.this);
			finish();
			break;

		case R.id.R_to_pay:
			if (mflagH.equals("��֧��")) {
				if (mflagH.equals("��֧��")) {
					setdialog();
				}
				if (mflagH.equals("������")) {
					Intent intent1 = new Intent(HotelOrderPageActivity.this,
							EditComment.class);
					String hotelname = hb.getName();
					String cType = "�Ƶ�";
					String userName = IDclass.UserName;
					int useriD = ho.getId();
					String orderid = Integer.toString(useriD);
					intent1.putExtra("orderid", orderid);
					intent1.putExtra("hotelname", hotelname);
					intent1.putExtra("cType", cType);
					startActivity(intent1);
				}
			}
			if (mflagH.equals("������")) {
				Intent intent1 = new Intent(HotelOrderPageActivity.this,EditComment.class);
				String hotelname = hb.getName();
				String cType = "�Ƶ�";
				String userName = IDclass.UserName;
				int useriD = ho.getId();
				String orderid = Integer.toString(useriD);
				intent1.putExtra("orderid", orderid);
				intent1.putExtra("hotelname", hotelname);
				intent1.putExtra("cType", cType);
				startActivity(intent1);
			}
			if (mflagH.equals("ʹ��")) {
				// Intent ToPay=new
				// Intent(HotelOrderPageActivity.this,RegisterActivity.class);
				// ToPay.putExtra("orderId", ho.getId());
				// startActivity(ToPay);
				dialogCore dialogcore = new dialogCore(
						HotelOrderPageActivity.this);
				dialogcore.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// dialogcore.setTitle("������");
				dialogcore.show();
				// Toast.makeText(HotelOrderPageActivity.this,
				// "�����������...",Toast.LENGTH_SHORT).show();
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
			if (intentAction.equals("location.findhotel")) {
				String tempList = arg1.getStringExtra("findhotel");
				Log.i("�㲥22222222", tempList);
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
		WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
		lp.x = 0; // ��λ��X����
		lp.y = 0; // ��λ��Y����
		lp.width = 800; // ���
		root.measure(0, 0);
		lp.height = 800;
		lp.alpha = 9f; // ͸����
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
				Toast.makeText(HotelOrderPageActivity.this, "֧���ɹ�",
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

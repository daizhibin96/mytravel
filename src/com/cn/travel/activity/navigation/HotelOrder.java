package com.cn.travel.activity.navigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.service.me.HotelOrderService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class HotelOrder extends Activity {

	private HotelBean hotelbean = new HotelBean();
	private ImageView hotelImg;
	private ImageView back;
	private TextView hotelName;
	private RadioGroup radiogroup;
	private RadioButton sin;
	private RadioButton dou;
	private ImageView jian;
	private ImageView add;
	private TextView number;
	private TextView start;
	private TextView end;
	private TextView price;
	private EditText phoneNum;
	private Button submit;
	long day = 1;
	int num = 1;
	int type = 1;
	int Price = 0;
	Date sDate = new Date();
	Date eDate = new Date();
	String phoneNumber;
	String roomType="单人房";
	HotelOrderBean orderBean = new HotelOrderBean();
	private IDclass id = new IDclass();
	private TextView liveday;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_navigation_hotelorder);
		initView();
		Intent intent = getIntent();
		Gson gson = new Gson();
		String str = intent.getStringExtra("hoteldetail");
		String ss= intent.getStringExtra("startTime");
		String ee = intent.getStringExtra("endTime");
		sDate = gson.fromJson(ss,Date.class);
		eDate = gson.fromJson(ee,Date.class);
		Log.e("1","....");
		day = eDate.getTime()-sDate.getTime();
		long day1 = 86400000;
		if(day<=day1){
			day=1;
		}
		if(day>day1){
			day = day/day1;
		}
		if(day<1){
			day=1;
		}
		Log.e("....",day+"");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		start.setText(simpleDateFormat.format(sDate));
		end.setText(simpleDateFormat.format(eDate));
		hotelbean = gson.fromJson(str, HotelBean.class);
		hotelName.setText(hotelbean.getName());
		String Img = Tools.baseUrl+hotelbean.getImage();
		Picasso.with(HotelOrder.this).load(Img.trim().toString())
		.placeholder(R.drawable.ic_launcher).into(hotelImg);
		initListener();
		Price = Integer.parseInt(hotelbean.getOneprice())*num*Integer.parseInt(String.valueOf(day));
		price.setText("￥ "+Integer.toString(Price));
		liveday.setText(day+"晚");
	}
	
	public void initView(){
		hotelImg = (ImageView)this.findViewById(R.id.hotelorder_img);
		back = (ImageView)this.findViewById(R.id.back);
		hotelName = (TextView)this.findViewById(R.id.hotelorder_name);
		radiogroup = (RadioGroup)this.findViewById(R.id.room);
		sin = (RadioButton)this.findViewById(R.id.single);
		dou = (RadioButton)this.findViewById(R.id.ddouble);
		jian = (ImageView)this.findViewById(R.id.jian);
		add = (ImageView)this.findViewById(R.id.add);
		number = (TextView)this.findViewById(R.id.number);
		start = (TextView)this.findViewById(R.id.start);
		end = (TextView)this.findViewById(R.id.end);
		price = (TextView)this.findViewById(R.id.o_price);
		phoneNum = (EditText)this.findViewById(R.id.phoneNum);
		submit = (Button)this.findViewById(R.id.o_submit);
		liveday = (TextView)this.findViewById(R.id.live_day);
	}
	
	public void initListener(){
		
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HotelOrder.this.finish();
			}
		
		});
		
		jian.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(num>1)
				{
					num--;
					String str = Integer.toString(num);
					number.setText(str);
					if(type==1){
						Price = Integer.parseInt(hotelbean.getOneprice())*num*Integer.parseInt(String.valueOf(day));
						price.setText("￥ "+Integer.toString(Price));
					}
					if(type==2)
					{
						Price = Integer.parseInt(hotelbean.getTwoprice())*num*Integer.parseInt(String.valueOf(day));
						price.setText("￥ "+Integer.toString(Price));
					}
				}
				if(num==1)
				{
					Toast.makeText(HotelOrder.this, "最小订房数:1", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(num<5)
				{
					num++;
					String str = Integer.toString(num);
					number.setText(str);
					if(type==1){
						Price = Integer.parseInt(hotelbean.getOneprice())*num*Integer.parseInt(String.valueOf(day));
						price.setText("￥ "+Integer.toString(Price));
					}
					if(type==2)
					{
						Price = Integer.parseInt(hotelbean.getTwoprice())*num*Integer.parseInt(String.valueOf(day));
						price.setText("￥ "+Integer.toString(Price));
					}
				}
				if(num==5){
					Toast.makeText(HotelOrder.this, "最大订房数:5", Toast.LENGTH_SHORT).show();
				}
			}
		
		});
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = group.getCheckedRadioButtonId();
				RadioButton button = (RadioButton) findViewById(id);
				roomType = button.getText().toString();
				if(roomType.equals("双人房"))
				{
					type = 2;
					Price = Integer.parseInt(hotelbean.getTwoprice())*num*Integer.parseInt(String.valueOf(day));
					price.setText("￥ "+Integer.toString(Price));
				}
				if(roomType.equals("单人房"))
				{
					type = 1;
					Price = Integer.parseInt(hotelbean.getOneprice())*num*Integer.parseInt(String.valueOf(day));
					price.setText("￥ "+Integer.toString(Price));
				}
			}
			
		});
		phoneNum.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				phoneNumber = phoneNum.getText().toString();
			}
		
		});
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phoneNumber==null){
					Toast.makeText(HotelOrder.this, "请输入联系方式", Toast.LENGTH_SHORT).show();
				}else{
					setdialog();
				}
			}
		
		});
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
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
		        Date date = new Date(System.currentTimeMillis());
		        String time = simpleDateFormat.format(date);
				orderBean.setRoomtype(roomType);
				orderBean.setRoomnum(num);
				orderBean.setIntime(start.getText().toString());
				orderBean.setOuttime(end.getText().toString());
				orderBean.setPhone(phoneNumber);
				orderBean.setPrice(Integer.toString(Price));
				orderBean.setTime(time);
				orderBean.setPay("待支付");
				orderBean.setHotel(hotelbean.getName());
				HotelOrderService orderService = new HotelOrderService();
				orderBean.setUserid(id.ID);
				orderService.hotelOrder(HotelOrder.this, orderBean);
				dialog.dismiss();
				HotelOrder.this.finish();
			}
	    	
	    });
	    pay.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
		        Date date = new Date(System.currentTimeMillis());
		        String time = simpleDateFormat.format(date);
				orderBean.setRoomtype(roomType);
				orderBean.setRoomnum(num);
				orderBean.setIntime(start.getText().toString());
				orderBean.setOuttime(end.getText().toString());
				orderBean.setPhone(phoneNumber);
				orderBean.setPrice(Integer.toString(Price));
				orderBean.setTime(time);
				orderBean.setPay("已支付");
				orderBean.setHotel(hotelbean.getName());
				HotelOrderService orderService = new HotelOrderService();
				orderBean.setUserid(id.ID);
				orderService.hotelOrder(HotelOrder.this, orderBean);
				dialog.dismiss();
				HotelOrder.this.finish();
			}
	    	
	    });
	}
	
}

package com.cn.travel.activity.navigation;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.bean.PointBean;
import com.cn.travel.bean.PointOrderBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.service.me.HotelOrderService;
import com.cn.travel.service.me.PointOrderService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PointOrder extends Activity {

	private PointBean pointbean = new PointBean();
	private ImageView pointImg;
	private ImageView back;
	private TextView pointName;
	private ImageView jian;
	private ImageView add;
	private TextView number;
	private TextView price;
	private EditText phoneNum;
	private Button submit;
	int num =1;
	int Price = 0;
	String phoneNumber;
	PointOrderBean orderBean = new PointOrderBean();
	private IDclass id = new IDclass();
	Date pointdate = new Date();
	private TextView pointDate;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_navigation_pointorder);
		initView();
		Intent intent = getIntent();
		Gson gson = new Gson();
		String str = intent.getStringExtra("pointdetail");
		String str1 = intent.getStringExtra("pointdate");
		pointdate = gson.fromJson(str1,Date.class);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		pointDate.setText(simpleDateFormat.format(pointdate));
		pointbean = gson.fromJson(str, PointBean.class);
		pointName.setText(pointbean.getName());
		String Img = Tools.baseUrl+pointbean.getImage();
		Picasso.with(PointOrder.this).load(Img.trim().toString())
		.placeholder(R.drawable.ic_launcher).into(pointImg);
		initListener();
		Price = Integer.parseInt(pointbean.getPrice())*num;
		price.setText("￥ "+Integer.toString(Price));
	}
	
	public void initView(){
		pointImg = (ImageView)this.findViewById(R.id.pointorder_img);
		back = (ImageView)this.findViewById(R.id.back);
		pointName = (TextView)this.findViewById(R.id.pointorder_name);
		jian = (ImageView)this.findViewById(R.id.jian);
		add = (ImageView)this.findViewById(R.id.add);
		number = (TextView)this.findViewById(R.id.number);
		price = (TextView)this.findViewById(R.id.o_price);
		phoneNum = (EditText)this.findViewById(R.id.phoneNum);
		submit = (Button)this.findViewById(R.id.o_submit);
		pointDate = (TextView)this.findViewById(R.id.start);
	}
	
	public void initListener(){
		
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PointOrder.this.finish();
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
					Price = Integer.parseInt(pointbean.getPrice())*num;
					price.setText("￥ "+Integer.toString(Price));
				}
				if(num==1)
				{
					Toast.makeText(PointOrder.this, "最小订票数:1", Toast.LENGTH_SHORT).show();
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
					Price = Integer.parseInt(pointbean.getPrice())*num;
					price.setText("￥ "+Integer.toString(Price));
				}
				if(num==5){
					Toast.makeText(PointOrder.this, "最大订票数:5", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(PointOrder.this, "请输入联系方式", Toast.LENGTH_SHORT).show();
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
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy年MM月dd日");
		        Date date = new Date(System.currentTimeMillis());
		        String time = simpleDateFormat.format(date);
				orderBean.setNum(num);
				orderBean.setPhone(phoneNumber);
				orderBean.setPrice(Integer.toString(Price));
				orderBean.setTime(time);
				orderBean.setPay("待支付");
				orderBean.setTourist(pointbean.getName());
				orderBean.setDate(simpleDateFormat1.format(pointdate));
				PointOrderService orderService = new PointOrderService();
				orderBean.setUserid(id.ID);
				orderService.pointOrder(PointOrder.this, orderBean);
				dialog.dismiss();
				PointOrder.this.finish();
			}
	    	
	    });
	    pay.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy年MM月dd日");
		        Date date = new Date(System.currentTimeMillis());
		        String time = simpleDateFormat.format(date);
				orderBean.setNum(num);
				orderBean.setPhone(phoneNumber);
				orderBean.setPrice(Integer.toString(Price));
				orderBean.setTime(time);
				orderBean.setPay("已支付");
				orderBean.setTourist(pointbean.getName());
				orderBean.setDate(simpleDateFormat1.format(pointdate));
				PointOrderService orderService = new PointOrderService();
				orderBean.setUserid(id.ID);
				orderService.pointOrder(PointOrder.this, orderBean);
				dialog.dismiss();
				PointOrder.this.finish();
			}
	    	
	    });
	}
	
}

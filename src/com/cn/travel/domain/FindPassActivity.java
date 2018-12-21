package com.cn.travel.domain;


import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.cn.travel.bean.UserBean;
import com.cn.travel.domain.RegisterActivity.LocationReceiver;
import com.cn.travel.domain.RegisterActivity.TimeCount;
import com.cn.travel.service.me.RegisterService;
import com.cn.travle.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindPassActivity extends BaseActivity {
	private EditText findPhone,phoneKey;
	private Button mConfirmKey;
	private TextView getKey,title;
	private TimeCount time;
	private LocationReceiver locationreceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_find_pass);
		findPhone=(EditText)findViewById(R.id.find_phone);
		phoneKey=(EditText)findViewById(R.id.pass_key);
		mConfirmKey=(Button)findViewById(R.id.confirm_key);
		getKey=(TextView)findViewById(R.id.get_phone_key);
		title=(TextView)findViewById(R.id.head_center_text);
		title.setText("验证号码");
		time = new TimeCount(60000, 1000);
		SMSSDK.registerEventHandler(eventHandler);	
		
		//广播
		locationreceiver = new LocationReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction("location.reportsucc");
		registerReceiver(locationreceiver, filter);
		
		getKey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (findPhone.getText().toString().equals("")) {
					Toast.makeText(getBaseContext(), "请输入手机号",
							Toast.LENGTH_SHORT).show();
				}else{
					UserBean userBean=new UserBean();
					RegisterService registerService=new RegisterService();
					String phone = findPhone.getText().toString() + "";
					userBean.setPhone(phone);
					registerService.exist(userBean, FindPassActivity.this);
				}
				
			}
		});
		
		mConfirmKey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!(findPhone.getText().toString().equals(""))) {
					if (phoneKey.getText().toString().equals("")) {
						Toast.makeText(getBaseContext(), "请输入验证码",
								Toast.LENGTH_SHORT).show();
					} else {
						String phString = findPhone.getText().toString();
						SMSSDK.submitVerificationCode("86", phString, phoneKey
								.getText().toString());						
					}
				}
			}
		});	
			
	}
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			getKey.setBackgroundColor(Color.parseColor("#B6B6D8"));
			getKey.setClickable(false);
			getKey.setText("(" + millisUntilFinished / 1000 + ")");
		}

		@Override
		public void onFinish() {
			getKey.setText("重新获取");
			getKey.setClickable(true);
			getKey.setBackgroundColor(Color.parseColor("#4EB84A"));
		}
	}
	public class LocationReceiver extends BroadcastReceiver {
		// 必须要重载的方法，用来监听是否有广播发送
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String intentAction = arg1.getAction();
			if (intentAction.equals("location.reportsucc")) {
				Log.i("接收广播.....", arg1.getBooleanExtra("falg", true) + "");

				if (arg1.getBooleanExtra("falg", true)) {
					Log.i("....", "当前手机未注册");
				} else {
					
					SMSSDK.getVerificationCode("86", findPhone.getText()
							.toString());
					System.out.println("发送短信.....");
					time.start();
				}
			}
		}
	}

	private EventHandler eventHandler = new EventHandler() {
		@Override
		public void afterEvent(int event, int result, Object data) {
			super.afterEvent(event, result, data);
			if (result == SMSSDK.RESULT_COMPLETE) {
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					// 提交验证码成功
					Intent intent = new Intent(FindPassActivity.this,
							SetPassActivity.class);
					if (!(findPhone.getText().toString().equals(""))) {
						intent.putExtra("findPhone", findPhone.getText().toString());}
					startActivity(intent);
					finish();
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					// 获取验证码成功
				} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
					// 返回支持发送验证码的国家列表
				}
			} else {
				((Throwable) data).printStackTrace();
			}
		}
	};
		
	protected void onDestroy() {
		unregisterReceiver(locationreceiver);
		SMSSDK.unregisterEventHandler(eventHandler);
		super.onDestroy();
	};
}

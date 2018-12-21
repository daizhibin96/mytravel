package com.cn.travel.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;

import com.cn.travel.bean.UserBean;
import com.cn.travel.data.MyDatabaseHelper;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.domain.LoginOKActivity;
import com.cn.travel.service.me.RegisterService;
import com.cn.travel.utils.Utils;
import com.cn.travle.R;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.ResHelper;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	private Button validateButton, validateButton2, button_lg;
	private EditText phoneCode, phoneNumber;
	private EventHandler eh;
	private EditText password;
	private EditText confirmPassword;
	private MyDatabaseHelper dbHelper;
	ArrayList<String[]> second; // 第二层数组
	String countyList = "国家列表：\n";
	private UserBean userBean = new UserBean();
	private RegisterService registerService = new RegisterService();
	// 填写从短信SDK应用后台注册得到的APPKEY
	private static String APPKEY = "27cd5c6bfce40";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "f8be2cacbbddda795a4a92dc7c0debbb";
	public String phString;
	private TimeCount time;
	private TextView title;
	private LocationReceiver locationReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		locationReceiver = new LocationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("location.reportsucc");
		registerReceiver(locationReceiver, filter);

		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("注册页面");
		validateButton = (Button) findViewById(R.id.validate_button);
		phoneNumber = (EditText) findViewById(R.id.phone_ed);
		validateButton.setOnClickListener(this);// 获取验证码
		phoneNumber.setOnClickListener(this);
		time = new TimeCount(60000, 1000);

		password = (EditText) findViewById(R.id.et_password);
		confirmPassword = (EditText) findViewById(R.id.et_confirmpassword);

		// String passWord = password.getText().toString().trim();
		// String phonenumber = phoneNumber.getText().toString().trim();

		// String ConfirmPassword = confirmPassword.getText().toString().trim();
		// consist(passWord, ConfirmPassword);
		button_lg = (Button) this.findViewById(R.id.go_to_login);
		button_lg.setOnClickListener(this);

		phoneCode = (EditText) findViewById(R.id.edt);
		phoneCode.setOnClickListener(this);
		validateButton2 = (Button) findViewById(R.id.register);// 注册按钮
		validateButton2.setOnClickListener(this);

	}

	// 获取验证码按钮设置
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			validateButton.setBackgroundColor(Color.parseColor("#B6B6D8"));
			validateButton.setClickable(false);
			validateButton.setText("(" + millisUntilFinished / 1000 + ")");
		}

		@Override
		public void onFinish() {
			validateButton.setText("重新获取");
			validateButton.setClickable(true);
			validateButton.setBackgroundColor(Color.parseColor("#4EB84A"));
		}
	}

	public static boolean saveUserInfo(Context context, String phoneNumber,
			String passWord) {
		SharedPreferences sp = context.getSharedPreferences("sendData",
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("phoneNumber", phoneNumber);
		// edit.putString("userName", UserName);
		edit.putString("passWord", passWord);
		edit.commit();
		return true;
	}

	private void consist(String str1, String str2) {
		// TODO Auto-generated method stub
		if (!str1.equals(str2)) {
			Toast.makeText(this, "密码不一致", 1).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 获取验证码
		case R.id.validate_button:

			System.out.println("-----------------发送短信111");
			if (!TextUtils.isEmpty(phoneNumber.getText().toString())) {

				String phone = phoneNumber.getText().toString() + "";
				userBean.setPhone(phone);

				registerService.exist(userBean, RegisterActivity.this);
			} else {
				Toast.makeText(this, "电话不能为空", 1).show();
			}
			break;
		// 验证输入的验证码
		case R.id.register:
			if ((password.getText().toString()).equals("")) {
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			} else {
				if ((password.getText().toString()).equals(confirmPassword
						.getText().toString())) {

					System.out.println("--------");
					phString = phoneNumber.getText().toString();
					SMSSDK.submitVerificationCode("86", phString, phoneCode
							.getText().toString());

				} else {
					Toast.makeText(this, "输入的密码不一致", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.go_to_login:
			Intent intet_lg = new Intent();
			intet_lg.setClass(RegisterActivity.this, LoginActivity.class);
			startActivity(intet_lg);
			finish();
			break;

		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			// TODO Auto-generated method stub

			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event=" + event);
			// System.out.println("--------event---"+event+"--------result*"+result+"--------"+data);
			if (result == SMSSDK.RESULT_COMPLETE) {
				if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
					System.out.println("--------event---" + event
							+ "--------result*" + result);
					Toast.makeText(getApplicationContext(), "文字成功",
							Toast.LENGTH_SHORT).show();
				} else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					System.out.println("--------event---" + event
							+ "--------result*" + result);
					Toast.makeText(getApplicationContext(), "验证成功",
							Toast.LENGTH_SHORT).show();

					Time time = new Time();
					time.setToNow();
					String id = String.valueOf(time.year)
							+ String.valueOf(time.month + 1) + String.valueOf

							(time.monthDay) + String.valueOf(time.second) + "";
					// String id = time.year+"";
					// id += time.month+"";

					String Pass = new String();
					Pass = password.getText().toString() + "";
					userBean.setPhone(phString);
					userBean.setPassword(Pass);
					userBean.setId(id);

					registerService.register(userBean, RegisterActivity.this);

				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {// 提交验证码成功
					System.out.println("--------event---" + event
							+ "--------result*" + result);
					Toast.makeText(getApplicationContext(), "发送验证码成功",
							Toast.LENGTH_SHORT).show();
				}
			} else if (event == SMSSDK.EVENT_GET_CONTACTS) {
				System.out.println("--------event---" + event
						+ "--------result*" + result + "--------"
						+ data.toString());
			} else if (event == SMSSDK.EVENT_GET_FRIENDS_IN_APP) {
				System.out.println("--------event---" + event
						+ "--------result*" + result + "--------"
						+ data.toString());
				// SMSSDK.getContacts(false);
			} else {
				// 失败都会进入这个else，res下的values下保证有smssdk_errors.xml
				// 这些代码是访问xml里面的给的提示
				// System.out.println("--------result---"+event+"--------*"+result+"--------"+data);
				System.out.println("------------错误1");
				((Throwable) data).printStackTrace();
				int status = 0;
				try {
					((Throwable) data).printStackTrace();
					Throwable throwable = (Throwable) data;
					int resId = ResHelper.getStringRes(RegisterActivity.this,
							"smssdk_network_error");
					JSONObject object = new JSONObject(throwable.getMessage());
					String des = object.optString("detail");
					status = object.optInt("status");
					if (!TextUtils.isEmpty(des)) {
						Toast.makeText(RegisterActivity.this, des,
								Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (Exception e) {
					SMSLog.getInstance().w(e);
				}
			}

		};
	};

	public class LocationReceiver extends BroadcastReceiver {
		// 必须要重载的方法，用来监听是否有广播发送
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String intentAction = arg1.getAction();
			if (intentAction.equals("location.reportsucc")) {
				Log.i("接收广播.....", arg1.getBooleanExtra("falg", false) + "");

				if (arg1.getBooleanExtra("falg", false)) {
					SMSSDK.getVerificationCode("86", phoneNumber.getText()
							.toString());
					System.out.println("-----------------发送短信");
					// SMSSDK.getVoiceVerifyCode("86",phonEditText.getText().toString());
					phString = phoneNumber.getText().toString();
					time.start();
				} else {
					Log.i("....", "yizhuce");
				}
			}
		}
	}

	protected void onDestroy() {
		unregisterReceiver(locationReceiver);
		super.onDestroy();
		// SMSSDK.unregisterAllEventHandler();
		System.out.println("------------onDestroy");
	};

	/**
	 * Activity从后台重新回到前台时被调用
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("------------onRestart");
	}

	/**
	 * Activity创建或者从后台重新回到前台时被调用
	 */
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("------------onStart");
	}

	/**
	 * Activity创建或者从被覆盖、后台重新回到前台时被调用
	 */
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("------------onResume");
		EventHandler eh = new EventHandler() {

			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				mHandler.sendMessage(msg);
			}

		};
		SMSSDK.registerEventHandler(eh);
	}

	/**
	 * Activity被覆盖到下面或者锁屏时被调用
	 */
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("------------onPause");
	}

	/**
	 * 退出当前Activity或者跳转到新Activity时被调用
	 */
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("------------onStop");
		SMSSDK.unregisterEventHandler(eh);
	}

}

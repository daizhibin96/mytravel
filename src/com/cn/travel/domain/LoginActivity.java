package com.cn.travel.domain;

import java.util.Map;

import com.cn.travel.activity.first.HomeActivity;
import com.cn.travel.bean.UserBean;
import com.cn.travel.service.me.LoginService;
import com.cn.travel.utils.Utils;
import com.cn.travle.R;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	private EditText etName;
	private EditText etPass;
	private TextView button_rg, mLogin;
	private CheckBox remindPass,showPassword;
	private UserBean userBean = new UserBean();
	private TextView title,findPass;
	private LoginService loginService = new LoginService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login);
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("登录界面");
		etName = (EditText) this.findViewById(R.id.et_username);
		etPass = (EditText) this.findViewById(R.id.et_password);
		mLogin = (Button) findViewById(R.id.btn_login);
		remindPass = (CheckBox) this.findViewById(R.id.remember_password);
		showPassword=(CheckBox) this.findViewById(R.id.show_password);
		button_rg = (TextView) this.findViewById(R.id.go_to_register);
		findPass= (TextView) this.findViewById(R.id.go_to_findpass);
		Map<String, String> userInfo = Utils.getUserInfo(this);
		etName.setText(userInfo.get("userName"));
		etPass.setText(userInfo.get("passWord"));
		
//		if(showPassword.isChecked()){
//			etPass.setInputType(0x90);
//			
//		}else{
//			etPass.setInputType(0x81);
//		}

		showPassword.setOnClickListener(new View.OnClickListener() {    
            @Override    
            public void onClick(View v) {   
            	boolean flag =showPassword.isChecked();
                if (flag) {    
                	etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());    
                } else {    
                	etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());    
                }    
                flag = !flag;    
                etPass.postInvalidate(); 
                CharSequence text = etPass.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable)text;
                    Selection.setSelection(spanText, text.length());
                }
            }    
        });
		
		button_rg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent inte = new Intent();
				inte.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(inte);
			}
		});
		findPass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent inte = new Intent();
				inte.setClass(LoginActivity.this, FindPassActivity.class);
				startActivity(inte);
			}
		});
		mLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String userName = etName.getText().toString().trim();
				String passWord = etPass.getText().toString().trim();
				if (TextUtils.isEmpty(userName)) {
					// Toast.makeText(this, "请输入用户名",
					// Toast.LENGTH_SHORT).show();
					Toast.makeText(getBaseContext(), "请输入用户名",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(passWord)) {
					Toast.makeText(getBaseContext(), "请输入密码",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
				// Log.i("LoginActivity", "记住密码"+userName+","+passWord);
				// boolean isSaveSuccess=Utils.saveUserInfo(this, userName,
				// passWord);
				// 从sendData文件获取用户名密码
				

				if (remindPass.isChecked()) {
					String userNameTemp = etName.getText().toString().trim();
					String passWordTemp = etPass.getText().toString().trim();
					Utils.saveUserInfo(LoginActivity.this, userNameTemp, passWordTemp);
				} else {
					String userNameTemp = etName.getText().toString().trim();
					Utils.saveUserInfo(LoginActivity.this, userNameTemp, null);
				}

				userBean.setUsername(userName);
				userBean.setPassword(passWord);

				loginService.login(userBean, LoginActivity.this);
				finish();
			}
		});

	}
}

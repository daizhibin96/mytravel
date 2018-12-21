package com.cn.travel.domain;

import com.cn.travel.service.me.InfoService;
import com.cn.travle.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetPassActivity extends BaseActivity {
	private EditText passOne,passTwo;
	private Button mConfirmPass;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set_pass);
		passOne=(EditText)findViewById(R.id.pass_one);
		passTwo=(EditText)findViewById(R.id.pass_two);
		mConfirmPass=(Button)findViewById(R.id.confirm_pass);
		title=(TextView)findViewById(R.id.head_center_text);
		title.setText("重置密码");
		
		Intent inte=getIntent();
		final String phoneNum=inte.getStringExtra("findPhone");
		
		
		mConfirmPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(passOne.getText().toString())) {
					Toast.makeText(SetPassActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}else{
					if (passOne.getText().toString()
							.equals(passTwo.getText().toString())) {
						//InfoService is=new InfoService();
						InfoService.FindPass(phoneNum, passOne.getText().toString(), SetPassActivity.this);
						finish();
					}
				}
			}
		});
	}
}

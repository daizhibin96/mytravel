package com.cn.travel.domain;


import com.cn.travel.activity.first.HomeActivity;
import com.cn.travel.bean.UserBean;
import com.cn.travel.info.InfoActivity;
import com.cn.travle.R;
import com.google.gson.Gson;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class LoginOKActivity extends BaseActivity {
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intentROOT=getIntent();		
		String userQ=intentROOT.getStringExtra("userQ");
		Gson gson=new Gson();		
		UserBean u=gson.fromJson(userQ, UserBean.class);
//		String userName=intentROOT.getStringExtra("username");
		//Toast.makeText(this, iD, Toast.LENGTH_SHORT).show();
		SharedPreferences.Editor editor = getSharedPreferences("ROOT",Context.MODE_PRIVATE).edit();		 
		 editor.putString("UserID",u.getId());
		 editor.putString("UserName",u.getUsername());
		 editor.putString("iconImage",u.getUserimage());
		 editor.commit();
		// Toast.makeText(getBaseContext(), u.getUserimage(), Toast.LENGTH_SHORT).show();
		IDclass.ID = u.getId(); 
		IDclass.UserName=u.getUsername();
		IDclass.iconImage=u.getUserimage();
		 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_end);
		
		button=(Button)this.findViewById(R.id.go_to_login);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(LoginOKActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}

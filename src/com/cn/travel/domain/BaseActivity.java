package com.cn.travel.domain;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}
}

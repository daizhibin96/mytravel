package com.cn.travel.activity.me;

import com.cn.travel.domain.BaseActivity;
import com.cn.travle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MeActivity extends BaseActivity {
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
    //去掉Activity上面的状态栏
//    getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
//                  WindowManager.LayoutParams. FLAG_FULLSCREEN);
	setContentView(R.layout.activity_me_collect);
}
}

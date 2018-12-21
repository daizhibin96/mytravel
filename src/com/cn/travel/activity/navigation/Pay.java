package com.cn.travel.activity.navigation;

import com.cn.travle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class Pay extends Activity {

	private ImageView back;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_navigation_pay);
		initView();
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Pay.this.finish();
			}
			
		});
	}
	
	public void initView(){
		back = (ImageView)this.findViewById(R.id.back);
	}
	
}

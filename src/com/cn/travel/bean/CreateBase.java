package com.cn.travel.bean;

import com.cn.travel.data.MyDatabaseHelper;
import com.cn.travle.R;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateBase extends Activity {
	private MyDatabaseHelper dbHelper;
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.create_base);
		button =(Button)findViewById(R.id.create_base);
		dbHelper = new MyDatabaseHelper(this,"Travel.db",null,1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dbHelper.getWritableDatabase();
				
			}
		});
	}
}

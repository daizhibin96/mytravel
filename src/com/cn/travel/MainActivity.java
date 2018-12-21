package com.cn.travel;


import com.cn.travel.activity.first.HomeActivity;
import com.cn.travle.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity{

	private CustomThread thread=null;
    public int num=5;
    public static final int WHAT=1;
    private Handler handler;
    private TextView skip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainactivity);
		skip = (TextView)this.findViewById(R.id.countdown);
		new Thread(new CustomThread()).start();
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				skip.setText(num+"  Ìø¹ý");	
			}
			
		};
		skip.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				num=1;
			}
		
		});
	}
	
	private class CustomThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try{
				
				for(int i=6;i>=0;i--){
					Thread.sleep(1000);
					num--;
					handler.sendMessage(new Message());
					if(num==0){
						Intent intent = new Intent(MainActivity.this,HomeActivity.class);
						startActivity(intent);
						MainActivity.this.finish();
						Thread.currentThread().destroy();
					}
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
		
}

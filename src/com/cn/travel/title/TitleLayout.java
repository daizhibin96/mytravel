package com.cn.travel.title;

import java.security.PublicKey;

import com.cn.travle.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleLayout extends LinearLayout{
	
	private Button B1;
	private TextView t1;
	public TitleLayout(Context context,AttributeSet attrs){
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.activity_alltitle, this);
		 B1=(Button)findViewById(R.id.TitleBackBtn);
		 t1=(TextView)findViewById(R.id.head_center_text);
		 String name = t1.getText().toString().trim();
		
		B1.setOnClickListener(new OnClickListener() {    
            public void onClick(View v) {    
                Log.d("Title back","key down");  
                  
                ((Activity)getContext()).finish();  
            }    
        });  
		
		
	}

}

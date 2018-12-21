package com.cn.travel.domain;


import java.io.File;

import com.cn.travle.R;

import android.R.id;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LogoutActivity extends BaseActivity implements android.view.View.OnClickListener {
	private Button logout,udefine;
	private TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_me_setting);
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("…Ë÷√");
		logout=(Button)findViewById(R.id.mlogout);
		if(IDclass.ID!=null){
			logout.setVisibility(View.VISIBLE);		
			logout.setOnClickListener(this);
		}
		
		udefine=(Button)findViewById(R.id.unknownButton);
		udefine.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.mlogout:
			
			 File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","ROOT.xml");			 
			  if(file.exists()){			  
				  file.delete(); 
			  }
			  String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TravelPersonal_icon/image_icon.png";
			  File fileImage=new File(s);
			  if(fileImage.exists()){			  
				  file.delete(); 
			  }
			  
			  MyActivityManager.finishAll();

			  android.os.Process.killProcess(android.os.Process.myPid());
			  
			Intent intent=new Intent(LogoutActivity.this,LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			Toast.makeText(this, "mlayout", Toast.LENGTH_SHORT).show();
			break;
		case R.id.unknownButton:
			Toast.makeText(this, "unknownButton", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
	}
	
		
}


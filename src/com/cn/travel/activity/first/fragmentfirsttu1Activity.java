package com.cn.travel.activity.first;


import com.cn.travle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class fragmentfirsttu1Activity extends Activity {
	TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
       getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_fragment_first_tu1);
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("海南双飞6日游");
	}

}

package com.cn.travel.activity.first;


import java.io.File;
import java.util.ArrayList;

import com.cn.travel.domain.IDclass;
import com.cn.travel.fragment.MyFragmentPagerAdapter;
import com.cn.travle.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnCreateContextMenuListener;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity{
	
	private ViewPager viewPager;
	private MyFragmentPagerAdapter mAdapter;
	private TextView findView,firstView,navigationView,meView;
	private ArrayList<TextView> linearTextView;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
         //去掉Activity上面的状态栏
        //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        
        //初始化静态IDclass类
        
        File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","ROOT.xml");			 
		  if(file.exists()){			  
			 
		  
        SharedPreferences sp = getSharedPreferences("ROOT",
				Context.MODE_PRIVATE);		
		String initId=sp.getString("UserID", "");
		String initUsername=sp.getString("UserName", "");
		String initImage=sp.getString("iconImage", "");
	//		Toast.makeText(this, initUsername, Toast.LENGTH_SHORT).show();
			
			
	//		if((initUsername!=null)&&(initId!=null)){
				IDclass.ID = initId; 
				IDclass.UserName=initUsername;
				IDclass.iconImage=initImage;
	//		}
		  }
      //初始化按钮
        initView();
        viewPager = (ViewPager)findViewById(R.id.fg_view);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
    	TextViewColor(0);
        viewPager.setOnPageChangeListener(new CustomPagerChangeListener());
      //按钮点击事件
        initListener();
        
        
        
        
	}
	
	
	public void initView() {//初始化按钮
		linearTextView = new ArrayList<TextView>();
		findView = (TextView)this.findViewById(R.id.findView);
		firstView = (TextView)this.findViewById(R.id.firstView);
		navigationView = (TextView)this.findViewById(R.id.navigationView);
		meView = (TextView)this.findViewById(R.id.meView);
		
		linearTextView.add(firstView);
		linearTextView.add(navigationView);
		linearTextView.add(findView);
		linearTextView.add(meView);
	
		
	}
	public void initListener(){
		findView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(2);
				TextViewColor(2);
				
			}
			
		});
		firstView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
				TextViewColor(0);
			}
			
		});
		navigationView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
				TextViewColor(1);
			}
			
		});
		meView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(3);
				TextViewColor(3);
				
			}
			
		});
		
	}
	
	private class CustomPagerChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			//state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			TextViewColor(arg0);
			
		}
		
	}
	private void TextViewColor(int index){
		for(TextView tv:linearTextView){
			tv.setTextColor(Color.parseColor("#666666"));
			
		}
		Drawable drawable1 = getResources().getDrawable(
				R.drawable.activity_home1);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
				drawable1.getIntrinsicHeight());
		
		Drawable drawable2 = getResources().getDrawable(
				R.drawable.xindao);
		drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
				drawable2.getIntrinsicHeight());
		Drawable drawable3 = getResources().getDrawable(
				R.drawable.find1);
		drawable3.setBounds(0, 0, drawable3.getMinimumWidth(),
				drawable3.getIntrinsicHeight());
		Drawable drawable4 = getResources().getDrawable(
				R.drawable.my1);
		drawable4.setBounds(0, 0, drawable4.getMinimumWidth(),
				drawable4.getIntrinsicHeight());
		
		
		Drawable drawable12 = getResources().getDrawable(
				R.drawable.lvjia);
		drawable12.setBounds(0, 0, drawable1.getMinimumWidth(),
				drawable12.getIntrinsicHeight());
		
		Drawable drawable22 = getResources().getDrawable(
				R.drawable.xindao2);
		drawable22.setBounds(0, 0, drawable22.getMinimumWidth(),
				drawable2.getIntrinsicHeight());
		Drawable drawable32 = getResources().getDrawable(
				R.drawable.find2);
		drawable32.setBounds(0, 0, drawable32.getMinimumWidth(),
				drawable32.getIntrinsicHeight());
		Drawable drawable42 = getResources().getDrawable(
				R.drawable.my2);
		drawable42.setBounds(0, 0, drawable42.getMinimumWidth(),
				drawable42.getIntrinsicHeight());
		
		
		
		
		
		linearTextView.get(0).setCompoundDrawables(  null,drawable1, null,null);
		
		linearTextView.get(1).setCompoundDrawables(  null,drawable2,  null,null);
		
		linearTextView.get(2).setCompoundDrawables(  null,drawable3,  null,null);
		
		linearTextView.get(3).setCompoundDrawables(  null,drawable4, null,null);
		
		
		switch (index) {
		case 0:
			linearTextView.get(index).setCompoundDrawables(  null,drawable12,  null,null);
			break;
		case 1:
			linearTextView.get(index).setCompoundDrawables(  null,drawable22,  null,null);
			break;
		case 2:
			linearTextView.get(index).setCompoundDrawables(  null,drawable32,  null,null);
			break;

		default:
			linearTextView.get(index).setCompoundDrawables( null, drawable42,  null,null);
			break;
		}
		
		
		
		
		
		linearTextView.get(index).setTextColor(Color.parseColor("#33ff33"));
	}
	
	
}

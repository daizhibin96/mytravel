package com.cn.travel.fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.cn.travel.MainActivity;
import com.cn.travel.activity.me.AvailibleOrderActivity;
import com.cn.travel.activity.me.BrowseActivity;
import com.cn.travel.activity.me.CollectActivity;
import com.cn.travel.activity.me.MeActivity;
import com.cn.travel.activity.me.MyOrderActivity;
import com.cn.travel.activity.me.ReportOrderActivity;
import com.cn.travel.activity.me.UnPayOrderActivity;

import com.cn.travel.activity.me.dialogme;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.domain.LogoutActivity;
import com.cn.travel.domain.MessageEvent;
import com.cn.travel.info.InfoActivity;
import com.cn.travel.service.me.ShowBrowseService;
import com.cn.travel.service.me.ShowCollectService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.squareup.picasso.Picasso;

import android.support.v4.app.Fragment;
import android.R.integer;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MeFragment extends Fragment {
	private TextView button_lg;
	private TextView collectin, record;
	private TextView more, info,msetting;
	private String Action12=null;
	private ImageView portrait;
	private LinearLayout mMyorder,unpay,Toreport,Availible;
    private ShowBrowseService showBrowseService = new ShowBrowseService();
	private ShowCollectService showCollectService = new ShowCollectService();
	
	
	
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_me, container, false);

		button_lg = (TextView) view.findViewById(R.id.to_login);
		collectin = (TextView) view.findViewById(R.id.collection);
		record = (TextView) view.findViewById(R.id.record);
		more = (TextView) view.findViewById(R.id.more);
		info = (TextView) view.findViewById(R.id.set_info);
		msetting = (TextView) view.findViewById(R.id.setting);
		portrait = (ImageView) view.findViewById(R.id.head_portrait);
		mMyorder=(LinearLayout) view.findViewById(R.id.all_orders);
		unpay=(LinearLayout) view.findViewById(R.id.unpay_order);
		Toreport=(LinearLayout) view.findViewById(R.id.to_review);
		Availible=(LinearLayout) view.findViewById(R.id.waiting_trip);
		
		if((Action12!=IDclass.UserName)&&(IDclass.UserName!=null)){
			button_lg.setText(IDclass.UserName);
		}
		
		EventBus.getDefault().register(this);
		if(IDclass.iconImage!=null){
//			String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TravelPersonal_icon/"+IDclass.iconImage;
//			Bitmap bitmap = BitmapFactory.decodeFile(s);
//			Log.e("ssssssssssssss+++++",s);
//			if(bitmap!=null){
//				portrait.setImageBitmap(bitmap);
//			}
			String ImPath=Tools.baseUrl+IDclass.iconImage;
			 Log.e(".....", ImPath);
			 Picasso.with(getActivity()).load(ImPath.trim().toString())
				.placeholder(R.drawable.ic_launcher).into(portrait);
		}
//        Uri uri = Uri.fromFile(new File(s));
//        mImageview.setImageURI(uri);
		// button_rg=(TextView)view.findViewById(R.id.to_register);

		button_lg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				// TODO Auto-generated method stub
				if(IDclass.UserName==null){
					//button_lg.setText("登录");
					Intent intent1 = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent1);
					
				}
			}
		});
		info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(IDclass.UserName==null){
					Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
					startActivity(intentToLogin);
				}else{
				// TODO Auto-generated method stub
					Intent intentToInfo = new Intent(getActivity(), InfoActivity.class);	
					startActivity(intentToInfo);
					
				}
			
			}
		});
		collectin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(IDclass.UserName!=null){
				// TODO Auto-generated method stub
					int i = 1;
				showCollectService.showCollect(i,getActivity());
				}else{
					Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
					startActivity(intentToLogin);
				}
			}
		});
		record.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(IDclass.UserName!=null){
					// TODO Auto-generated method stub
					showBrowseService.showBrowse(getActivity());
					}else{
						Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
						startActivity(intentToLogin);
					}
			}
		});
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent1=new Intent(getActivity(), MoreActivity.class);

				// startActivity(intent1);

				// 自定义Dialog使用
				dialogme dialoga = new dialogme(getActivity());
				dialoga.setTitle("更多功能");
				dialoga.show();

			}
		});
		
		msetting.setOnClickListener(new OnClickListener() {
		
		 @Override
		 public void onClick(View arg0) {
		 // TODO Auto-generated method stub
		 Intent intent = new Intent(getActivity(),LogoutActivity.class);
		 startActivity(intent);
		 }
		 });
		
		mMyorder.setOnClickListener(new OnClickListener() {
			
			 @Override
			 public void onClick(View arg0) {
			 // TODO Auto-generated method stub
			 if(IDclass.UserName!=null){
					// TODO Auto-generated method stub
					Intent intent1 = new Intent(getActivity(),
							MyOrderActivity.class);

					startActivity(intent1);
					}else{
						Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
						startActivity(intentToLogin);
					}
			 }
			 });
		unpay.setOnClickListener(new OnClickListener() {
			
			 @Override
			 public void onClick(View arg0) {
			 // TODO Auto-generated method stub
			 if(IDclass.UserName!=null){
					// TODO Auto-generated method stub
					Intent intent1 = new Intent(getActivity(),
							UnPayOrderActivity.class);

					startActivity(intent1);
					}else{
						Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
						startActivity(intentToLogin);
					}
			 }
			 });
		Toreport.setOnClickListener(new OnClickListener() {
			
			 @Override
			 public void onClick(View arg0) {
			 // TODO Auto-generated method stub
			 if(IDclass.UserName!=null){
					// TODO Auto-generated method stub
					Intent intent1 = new Intent(getActivity(),
							ReportOrderActivity.class);

					startActivity(intent1);
					}else{
						Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
						startActivity(intentToLogin);
					}
			 }
			 });
		Availible.setOnClickListener(new OnClickListener() {
			
			 @Override
			 public void onClick(View arg0) {
			 // TODO Auto-generated method stub
			 if(IDclass.UserName!=null){
					// TODO Auto-generated method stub
					Intent intent1 = new Intent(getActivity(),
							AvailibleOrderActivity.class);

					startActivity(intent1);
					}else{
						Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
						startActivity(intentToLogin);
					}
			 }
			 });
		return view;
	}
	
	
	 
	@Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
		button_lg.setText(messageEvent.getMessage());
		IDclass.iconImage=messageEvent.getIconImageId();
		String locationPath=messageEvent.getIconImageId().replaceAll("images/", "");
		String s = Environment.getExternalStorageDirectory().getAbsolutePath() + 
				"/TravelPersonal_icon/"+locationPath;
		Bitmap bitmap = BitmapFactory.decodeFile(s);
		if(bitmap!=null){
			portrait.setImageBitmap(bitmap);
		}
//		String locationPath=path.replaceAll("images/", "");
//		IDclass.iconImage=locationPath;
    }
	
	
	@Override
	public void onDestroy() {
		 super.onDestroy(); 
		 if(EventBus.getDefault().isRegistered(this)){
			 EventBus.getDefault().unregister(this);
		 } 
	 }

}

package com.cn.travel.activity.first.food;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cn.travel.activity.first.food.FoodBean;
import com.cn.travel.activity.navigation.HotelList;
import com.cn.travel.bean.HistoryBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.service.me.HistoryService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;

import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodAdapter extends ArrayAdapter<FoodBean> {
	private int resourceId;
	 Handler handler;
	 
	public FoodAdapter(Context context,  int textViewResourceId,
			List<FoodBean> objects) {
		super(context,  textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	
	
	
	@SuppressLint({ "HandlerLeak", "ViewHolder" })
	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//
		
		 

         final FoodBean food=getItem(position);
        
		View view = LayoutInflater.from(getContext()).inflate(resourceId, arg2,false);

		 
		String a=food.getImageID();
		
		
		TextView mTextView2 = (TextView) view.findViewById(R.id.textView1);
		mTextView2.setText(food.getName());
		final ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
		
		Picasso.with(getContext()).load(Tools.baseUrl+food.getImageID().trim().toString())
		.placeholder(R.drawable.ic_launcher).into(imageView);
		final String msg=food.getMessage();
		Log.i("食物", msg);
		/*handler=new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				imageView.setImageBitmap((Bitmap) msg.obj);
				
			};
		};*/
		
		//imageView.setImageBitmap(bitmap);
		//imageView.setImageResource(food.getImageID());
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(IDclass.ID!=null){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
			        Date date = new Date(System.currentTimeMillis());
			        String time = simpleDateFormat.format(date);
					HistoryService historyService = new HistoryService();
					HistoryBean historyBean = new HistoryBean();
					historyBean.setId(food.getId());
					historyBean.setUserid(IDclass.ID);
					historyBean.setName(food.getName());
					historyBean.setImage(food.getImageID());
					historyBean.setTime(time);
					historyBean.setType("美食");
					historyService.history(getContext(), historyBean);
				
				MyService.commentlist(food,getContext());}
				else{
					Intent intent = new Intent(getContext(),LoginActivity.class);
					getContext().startActivity(intent);
					
				}
				
			}
		});

		return view;

	}
	
	/*
	public void getBitmapFromURL(final String src) {
		Thread thread =new Thread()
		{
			public void run(){
        try {
        	
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
           
        	//HttpURLConnection conn = (HttpURLConnection) new URL(src).openConnection();
        	//conn.setConnectTimeout(5000);
        	//conn.setRequestMethod("GET");
        	
        	
        		//InputStream inputStream=conn.getInputStream();
        		//Bitmap myBitmap=BitmapFactory.decodeStream(inputStream);
        		
        		 Message msg=handler.obtainMessage();
                 msg.obj=myBitmap;
                 handler.sendMessage(msg);
        		
        	
           
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
           
        }
			}
    };
    thread.start();
	
		}*/



}

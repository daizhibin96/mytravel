package com.cn.travel.fragment;

import java.io.InputStream;

import com.cn.travel.activity.navigation.Hotel;
import com.cn.travel.activity.navigation.HotelList;
import com.cn.travel.activity.navigation.SelectCity;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.PointBean;
import com.cn.travel.service.me.HotelService;
import com.cn.travel.service.me.PointService;
import com.cn.travle.R;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationFragment extends Fragment {

	protected TextView tv;
	protected ImageView img;
	private View view;
	private ImageView hotel;
	private TextView hotelT;
	private ImageView point;
	private TextView pointT;
	private HotelService hotelservice;
	private HotelBean hotelbean = new HotelBean();
	private PointBean pointbean = new PointBean();
	private String address = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_navigation, container,false);
		initView();
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SelectCity.class);
				startActivityForResult(intent,1);
			}

		});
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SelectCity.class);
				startActivityForResult(intent,1);
			}

		});
		
		
		hotel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				address = (String) tv.getText();
				HotelService hotel = new HotelService();
			    hotel.hotellist(hotelbean, getActivity(),address);
			}
			
		});
		
		hotelT.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				address = (String) tv.getText();
				HotelService hotel = new HotelService();
			    hotel.hotellist(hotelbean, getActivity(),address);
			}
			
		});
		
		point.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				address = (String) tv.getText();
				PointService point = new PointService();
				point.pointlist(pointbean, getActivity(), address);
			}
			
		});
		
		pointT.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				address = (String) tv.getText();
				PointService point = new PointService();
				point.pointlist(pointbean, getActivity(), address);
			}
			
		});
		return view;
	}

	public void initView() {
		tv = (TextView) view.findViewById(R.id.navigation_city);
		img = (ImageView) view.findViewById(R.id.navigation_image);
		hotel = (ImageView) view.findViewById(R.id.hotel_img);
		hotelT = (TextView) view.findViewById(R.id.hotel_text);
		point = (ImageView) view.findViewById(R.id.point_img);
		pointT = (TextView) view.findViewById(R.id.point_text);
	}
	
	public void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			if(resultCode==1){
				if(requestCode==1){
					tv.setText("¶«Ý¸."+data.getSerializableExtra("city").toString());
					String imgId = data.getSerializableExtra("img").toString();
					int id = Integer.parseInt(imgId);
					BitmapFactory.Options opt = new BitmapFactory.Options();
		            opt.inPreferredConfig = Bitmap.Config.RGB_565;
		            opt.inPurgeable = true;
		            opt.inInputShareable = true;
		            InputStream is = getResources().openRawResource(id);
		            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
		            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
		            img.setBackgroundDrawable(bd);
				}
			}
		}
	}
}

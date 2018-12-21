package com.cn.travel.activity.me;

import java.util.List;

import com.cn.travel.bean.Order;
import com.cn.travel.domain.IDclass;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter extends ArrayAdapter<Order> {
	private int resourceId;
	public OrderAdapter(Context context, int textViewResourceId,
			List<Order> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		resourceId=textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		Order order=getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view=LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
			viewHolder=new ViewHolder();
			viewHolder.OrderImage=(ImageView)view.findViewById(R.id.order_image);
			viewHolder.OrderName=(TextView)view.findViewById(R.id.order_name);
			viewHolder.OrderTime=(TextView)view.findViewById(R.id.order_time);
			viewHolder.OrderPrice=(TextView)view.findViewById(R.id.order_price);
			view.setTag(viewHolder);
		}else{
			view =convertView;
			viewHolder=(ViewHolder) view.getTag();
		}
		
//		String ImagePath=Tools.baseUrl+order.getImgePic();	
//		Picasso.with(getContext()).load(ImagePath.trim().toString())
//		.placeholder(R.drawable.ic_launcher).into(OrderImage);
//		String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TravelPersonal_icon/"+IDclass.iconImage;
//		Bitmap bitmap = BitmapFactory.decodeFile(s);
//		viewHolder.OrderImage.setImageBitmap(bitmap);
		String ImPath=Tools.baseUrl+order.getImgePic();
		 //Log.e("ͼƬ.....", ImPath);
		 Picasso.with(getContext()).load(ImPath.trim().toString())
			.placeholder(R.drawable.ic_launcher).into(viewHolder.OrderImage);
		viewHolder.OrderName.setText(order.getOrdername());
		viewHolder.OrderTime.setText(order.getOrdertime());
		viewHolder.OrderPrice.setText(order.getPrice());
		return view;
		
		
	}
	class ViewHolder{
		ImageView OrderImage;
		TextView OrderName;
		TextView OrderTime;
		TextView OrderPrice;
	}
}

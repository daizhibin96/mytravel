package com.cn.travel.activity.find;

import java.util.List;

import com.cn.travel.activity.first.ViewHolder;
import com.cn.travel.activity.first.food.MyService;
import com.cn.travel.bean.NoteBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travle.R;
import com.squareup.picasso.Picasso;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<forumbean> {
  private int resourceId;
	public NoteAdapter(Context context,  int textViewResourceId,
			List<forumbean> objects) {
		super(context,  textViewResourceId, objects);
		resourceId=textViewResourceId;
	}
	
	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		//
        final forumbean noteBean=getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, arg2,false);

		
		TextView mTextView2 = (TextView) view.findViewById(R.id.tv_time);
		mTextView2.setText(noteBean.getTitle());
		TextView mTextView3 = (TextView) view.findViewById(R.id.user);
		mTextView3.setText(noteBean.getUsername());
		
		
		ImageView imageView = (ImageView) view.findViewById(R.id.image);
		if(noteBean.getImage()==null){
			imageView.setBackgroundResource(R.drawable.ic_launcher);
		}else{
		Picasso.with(getContext()).load("http://114.116.35.0:8080/"+noteBean.getImage().trim().toString())
		.placeholder(R.drawable.ic_launcher).into(imageView);}
		
		ImageView imageView2 = (ImageView) view.findViewById(R.id.userimage);
		Picasso.with(getContext()).load("http://114.116.35.0:8080/"+noteBean.getUserimage().trim().toString())
		.placeholder(R.drawable.ic_launcher).into(imageView2);
		
          view.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				if(IDclass.ID!=null){
					FindforumService.sendforum(noteBean, getContext());
				
				}
				else{
					Intent intent = new Intent(getContext(),LoginActivity.class);
					getContext().startActivity(intent);
					
				}
				
			}
		});

		return view;

	}

}

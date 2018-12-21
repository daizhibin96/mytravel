package com.cn.travel.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.travel.activity.find.FindService;
import com.cn.travel.activity.find.NoteActivity;
import com.cn.travel.activity.find.NoteAdapter;
import com.cn.travel.activity.find.forumbean;
import com.cn.travel.activity.first.ViewHolder;
import com.cn.travel.activity.first.food.FoodBean;
import com.cn.travel.bean.NoteBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FindFragment extends Fragment {
	private ListView listView;
	MyBroadcastReceiver receiveBroadCast;
	IntentFilter intentFilter;
	private TextView tv_time;
	private ImageView imageViews;
	private FindFragment context;
	private ImageView btn;
	Map<String, Object> map;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private NoteAdapter adapter;
	private List<forumbean> noteList = new ArrayList<forumbean>();
	private FindService findService=new FindService();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_find, container, false);

		context = this;
		//初始化两个数据
		//NoteBean noteBean1 = new NoteBean("这是第一个帖子", R.drawable.tiezi1);
		//noteList.add(noteBean1);
		//NoteBean noteBean2 = new NoteBean("这是第二个帖子", R.drawable.tiezi2);
		//noteList.add(noteBean2);
		final NoteAdapter adapter=new NoteAdapter(getActivity(), R.layout.fragment_find_listitem, noteList);

		listView = (ListView) view.findViewById(R.id.listView);
		
		imageViews = (ImageView) view.findViewById(R.id.imageView1);
		btn = (ImageView) view.findViewById(R.id.imageView2);
		//开始广播
		findService.getmsg(getActivity());
		
		//下面这个是跳进游记编写界面的按钮
		imageViews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if(IDclass.ID!=null){
				Intent intent = new Intent(getActivity(), NoteActivity.class);
				startActivity(intent);}
				else{
					Intent intent = new Intent(getActivity(),LoginActivity.class);
					getActivity().startActivity(intent);
				}

			}
		});

		//adapter = new CustomAdapter(getActivity(), list);
		//listView.setAdapter(adapter);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//map = list.get(1);
				//map.put("Name", "更新的名字");
				
				
				//Toast.makeText(context2, "游记已经更新完成", Toast.LENGTH_LONG).show();
				
				findService.getmsg(getActivity());
				Toast.makeText(getActivity(), "更新完成",Toast.LENGTH_LONG).show();
				
			}
		});
		return view;
		

	}
	
	
	
	public void onAttach(Activity activity) {
		// 注册广播
		receiveBroadCast=new MyBroadcastReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction("com.youji");
		activity.registerReceiver(receiveBroadCast, filter);
		super.onAttach(activity);
	}
	public class MyBroadcastReceiver extends BroadcastReceiver{
		@Override
		
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			String a1=intent.getStringExtra("aaa");
			
			Log.i("FInd", a1);
			Gson gson = new Gson();
			noteList = gson.fromJson(a1, new TypeToken<ArrayList<forumbean>>() {
				}.getType());
			Collections.reverse(noteList);//让列表倒序显示
			final NoteAdapter adapter=new NoteAdapter(getActivity(), R.layout.fragment_find_listitem, noteList);
			listView.setAdapter(adapter);
			
			//adapter.notifyDataSetChanged();
			//NoteBean noteBean3=new NoteBean(a1,R.drawable.tiezi2);
			
			//noteList.add(0,noteBean3);
			
			//adapter.notifyDataSetChanged();
			
			
		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(receiveBroadCast);
		
	}
	
/*
	private class CustomAdapter extends BaseAdapter {

		List<Map<String, Object>> list;
		LayoutInflater inflater;

		public CustomAdapter(Context context, List<Map<String, Object>> list) {
			this.list = list;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			//
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			//
			return names[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			//
			return position;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			//
             ViewHolder viewHolder;
			View view = View.inflate(getActivity(),
					R.layout.fragment_find_listitem, null);

			Log.d("create", "-----------");
			TextView mTextView2 = (TextView) view.findViewById(R.id.tv_time);
			mTextView2.setText(names[position]);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			imageView.setBackgroundResource(icons[position]);

			return view;

		}

	}
	*/

	
	
	
	

}

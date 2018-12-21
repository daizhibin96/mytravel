package com.cn.travel.activity.me;

import java.util.ArrayList;
import java.util.HashMap;


import com.cn.travel.bean.Browse;
import com.cn.travel.bean.Collect;
import com.cn.travel.service.me.ShowBrowseService;
import com.cn.travel.service.me.ShowService;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

@SuppressLint("NewApi")
public class BrowseActivity extends Activity{
	private PopupWindow popupWindow = null,popupWindow2=null;
	private ListView listView;
	
	private int[] images;
	private LinearLayout linearLayout ;
	private boolean isShowRadioButton = false,isCheckRadioButton = false;
	
    private RadioButton radio1,radio2,radio3,radio4,radio5;
	private BrowseBrowseAdapter adapter;
	private ShowBrowseService showBrowseService = new ShowBrowseService();
	private ArrayList<Browse> arrayList1 = new ArrayList<Browse>(),arrayList2;
	private ArrayList<String> list = new ArrayList<String>();
	private Button B1;
	private ImageView imageView;
	BrowseBottomAdapter bottomAdapter = new BrowseBottomAdapter();
	private String show = "全部";
	TextView title;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
//                      WindowManager.LayoutParams. FLAG_FULLSCREEN);
       
        
		setContentView(R.layout.activity_me_browse);
		Intent intent = getIntent();
		if(intent.getExtras()==null){
			Log.e("...", "还没调用");
			showBrowseService.showBrowse(this);
			finish();
		}else{
	    
		String s = intent.getStringExtra("s");
		Gson gson = new Gson();
		arrayList1 = gson.fromJson(s,new TypeToken<ArrayList<Browse>>(){}.getType());
		arrayList2 = new ArrayList<Browse>(arrayList1);
		initData();//初始化数据	
			
		initView(); //初始化界面
		
		
	
		
	    B1.setOnClickListener(new OnClickListener() {    
            public void onClick(View v) {    
                  
                finish();  
            }    
        });  
	    
	    imageView.setOnClickListener(new OnClickListener() {    
            public void onClick(View v) {    
                  
                showBrowseService.deletesBrowse(show,BrowseActivity.this);
                arrayList2.clear();
                list.clear();
                initData();
                bottomAdapter.notifyDataSetChanged();
                for(int i =0;i<arrayList1.size();i++){
					if(arrayList1.get(i).getType().equals(show)){
						arrayList1.remove(i);
						i--;
					}
				}
                
                
            }    
        });  
	    
	    radio1.setOnClickListener(new OnClickListener() {//全部按钮
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show = "全部";
				title.setText("全部浏览记录");
				arrayList2 = new ArrayList<Browse>(arrayList1);
				initData();//初始化数据	
				bottomAdapter.notifyDataSetChanged();
				
				
			}
		});
       radio2.setOnClickListener(new OnClickListener() {//酒店按钮
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show = "酒店";
				title.setText("酒店浏览记录");
				arrayList2 = new ArrayList<Browse>();
				for(int i =0;i<arrayList1.size();i++){
					if(arrayList1.get(i).getType().equals("酒店")){
						arrayList2.add(arrayList1.get(i));
					}
				}
				
				
				initData();//初始化数据	
				bottomAdapter.notifyDataSetChanged();
				
				
			}
		});
       radio3.setOnClickListener(new OnClickListener() {//美食按钮
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show = "美食";
				title.setText("美食浏览记录");
				arrayList2 = new ArrayList<Browse>();
				for(int i =0;i<arrayList1.size();i++){
					if(arrayList1.get(i).getType().equals("美食")){
						arrayList2.add(arrayList1.get(i));
					}
				}
				
				
				initData();//初始化数据	
				bottomAdapter.notifyDataSetChanged();
				
			}
		});
       radio4.setOnClickListener(new OnClickListener() {//景点按钮
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show = "景点";
				title.setText("景点浏览记录");
				arrayList2 = new ArrayList<Browse>();
				for(int i =0;i<arrayList1.size();i++){
					if(arrayList1.get(i).getType().equals("景点")){
						arrayList2.add(arrayList1.get(i));
					}
				}
				
				
				initData();//初始化数据	
				bottomAdapter.notifyDataSetChanged();
			}
		});
       radio5.setOnClickListener(new OnClickListener() {//帖子按钮
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show = "帖子";
				title.setText("帖子浏览记录");
				arrayList2 = new ArrayList<Browse>();
				for(int i =0;i<arrayList1.size();i++){
					if(arrayList1.get(i).getType().equals("帖子")){
						arrayList2.add(arrayList1.get(i));
					}
				}
				
				
				initData();//初始化数据	
				bottomAdapter.notifyDataSetChanged();
			}
		});
	    
	
		listView.setAdapter(bottomAdapter);
		}
	}

	public void initData(){//初始化数据
		list = new ArrayList<String>();
		for(int i=0;i<arrayList2.size();i++){
			arrayList1.get(i).setDelete(false);
			if(i==0){
				String string = arrayList2.get(i).getTime().split(" ")[0];
				list.add(string);
				
			}else{
				String string = arrayList2.get(i).getTime().split(" ")[0];
				if(!string.equals(arrayList2.get(i-1).getTime().split(" ")[0])){
					list.add(string);
				}
			}
			
			
		}
		if(arrayList2.size()==0){
			list.add("  空空如也~~~~~~~~~~~~");
		}
	}
	
	
	public void initView(){////初始化界面
		B1=(Button)findViewById(R.id.TitleBackBtn);	
		imageView = (ImageView)findViewById(R.id.imageView1);
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("全部浏览记录");
		
		listView = (ListView) findViewById(R.id.listView1);
		linearLayout = (LinearLayout )findViewById(R.id.linearLayout);
		radio1 = (RadioButton)findViewById(R.id.radio1);
		radio2 = (RadioButton)findViewById(R.id.radio2);
		radio3 = (RadioButton)findViewById(R.id.radio3);
		radio4 = (RadioButton)findViewById(R.id.radio4);
		radio5 = (RadioButton)findViewById(R.id.radio5);
	}
	
	
	
	
	private class BrowseBottomAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			arg1 = View.inflate(BrowseActivity.this, R.layout.list_item_browse_bottom, null);
			TextView textView = (TextView)arg1.findViewById(R.id.textView1);
			ListView lView = (ListView)arg1.findViewById(R.id.listView1);
			
			ArrayList<Browse> list1 = new ArrayList<Browse>();
			for(int i =0;i<arrayList2.size();i++){
				String string = arrayList2.get(i).getTime().split(" ")[0];
				if(string.equals(list.get(arg0))){
					list1.add(arrayList2.get(i));
				}
			}
			
			adapter = new BrowseBrowseAdapter(list1);
			lView.setAdapter(adapter);
			
			ListAdapter listAdapter = lView.getAdapter();
			int totalheight = 0;
			for(int i = 0; i < listAdapter.getCount();i++)
			{
				View iView = listAdapter.getView(i, null, lView);
				iView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				totalheight = totalheight+iView.getMeasuredHeight()+2;
			}
			ViewGroup.LayoutParams params = lView.getLayoutParams();
			params.height = totalheight ;
			lView.setLayoutParams(params);
			
	        textView.setText(list.get(arg0));  
			return arg1;
		}
		
	}
	
	private class BrowseBrowseAdapter extends BaseAdapter{
		
		
		private  ArrayList<Browse> list2 = new ArrayList<Browse>();
		HashMap<Integer, View> lmap = new HashMap<Integer,View>();

		public BrowseBrowseAdapter(ArrayList<Browse> list3) {
			super();
			this.list2 = list3;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list2.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list2.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
            final ViewHolder viewHolder;
			final int show = arg0;
			if(lmap.get(arg0) ==null){
				arg1 = View
						.inflate(BrowseActivity.this, R.layout.list_item_browse_browse, null);
				viewHolder = new ViewHolder(arg1);
				arg1.setTag(viewHolder);
				lmap.put(arg0, arg1);
				
			}else{
				arg1 = lmap.get(arg0);
				viewHolder = (ViewHolder)arg1.getTag();
			}
			 Picasso.with(BrowseActivity.this).load(Tools.baseUrl+list2.get(arg0).getImage())
				.placeholder(R.drawable.ic_launcher).into(viewHolder.imageView);
			//viewHolder.imageView.setImageResource(arrayList3.get(arg0).getImage());
			
			viewHolder.textView.setText(list2.get(arg0).getName()+"");
			viewHolder.time.setText("浏览时间: "+list2.get(arg0).getTime());
			viewHolder.type.setText("类型: "+list2.get(arg0).getType());
	        
	       
			
	        
			viewHolder.linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
                    if(list2.get(show).getType().equals("帖子"))	{
						
					}else {
						ShowService showService = new ShowService();
						showService.show(list2.get(show).getType(),list2.get(show).getName(),BrowseActivity.this);
					}

					
				}
			});
	        
	     
			return arg1;
		}
		
		class ViewHolder{
		    TextView textView,time,type;
		    LinearLayout linearLayout;
		    ImageView imageView;
		   
			
			public ViewHolder(View arg1){
				linearLayout = (LinearLayout) arg1
						.findViewById(R.id.LinearLayout1);
				imageView = (ImageView) arg1
						.findViewById(R.id.imageView1);
				time = (TextView) arg1.findViewById(R.id.time);
				textView = (TextView) arg1.findViewById(R.id.textView1);
				type = (TextView) arg1.findViewById(R.id.type);
			}
		}
		
	}
	
	
	
	
	

}

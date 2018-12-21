package com.cn.travel.activity.me;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.smssdk.SMSSDK;

import com.cn.travel.activity.navigation.HotelDetail;
import com.cn.travel.bean.Collect;
import com.cn.travel.domain.RegisterActivity.LocationReceiver;
import com.cn.travel.service.me.ShowCollectService;
import com.cn.travel.service.me.ShowService;
import com.cn.travel.tools.XListView.IXListViewListener;
import com.cn.travel.utils.Tools;
import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.MobSDK;
import com.squareup.picasso.Picasso;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


@SuppressLint("NewApi")
public class CollectActivity extends Activity implements
		SearchView.OnQueryTextListener,IXListViewListener {
	private com.cn.travel.tools.XListView listView1;
	private SearchView searchView;
	private ArrayList<Collect> arrayList1 = new ArrayList<Collect>(), arrayList2;
	private static ArrayList<Collect> arrayList3;
	private ArrayList<Boolean> list = new ArrayList<Boolean>();
	private ArrayList<RadioButton> imageList = new ArrayList<RadioButton>();
	private LinearLayout searchLayout;
	private PopupWindow popupWindow = null;
	private View view;
	private TextView textView1,textView2,textView3,textView4,textView5;
	private customsAdapter adapter = new customsAdapter();
	private boolean isShowRadioButton = false,isCheckRadioButton = false;
	private Handler handler;
	ShowCollectService showCollectService = new ShowCollectService();
	LocationReceiver locationReceiver = new LocationReceiver();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
//                      WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_me_collect);
		Intent intent = getIntent();
		if(intent.getExtras()==null){
			Log.e("...", "还没调用");
			int i = 1;
			showCollectService.showCollect(i,this);
			finish();
		}else{
		MobSDK.init(this,"2850adcf56ff5","daae4f9ca83e0a9147c3660e9bb2fa99");
		String s = intent.getStringExtra("s");
		Gson gson = new Gson();
		arrayList1 = gson.fromJson(s,new TypeToken<ArrayList<Collect>>(){}.getType());
		
		for(int i=0;i<arrayList1.size();i++){
			arrayList1.get(i).setDelete(false);
			list.add(i, false);
		}
		
		TextView title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("个人收藏");
		view = View.inflate(CollectActivity.this, R.layout.popup_me_collect_bottom, null);
		searchView = (SearchView) findViewById(R.id.searchView1);
		searchLayout = (LinearLayout)findViewById(R.id.searchLayout);
		listView1 = (com.cn.travel.tools.XListView)this.findViewById(R.id.list);
	    listView1.setPullLoadEnable(true);
	    listView1.setXListViewListener(this);
	   

	    
	    if(arrayList1.size()==0){
	    	textView1 = (TextView)findViewById(R.id.textView1);
	    	textView1.setVisibility(View.VISIBLE);
	    }
		searchView.setOnQueryTextListener(this);
		arrayList3 = new ArrayList<Collect>(arrayList1);
		
		listView1.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				arrayList3 = (ArrayList<Collect>) msg.obj;
				
				
				onload();
				adapter.notifyDataSetChanged();
			}
			
		};
		}
		
	}
	//
	private void onload(){
		listView1.stopRefresh();
		listView1.stopLoadMore();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
		listView1.setRefreshTime(time);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		searchLayout.setFocusable(true);
		searchLayout.setFocusableInTouchMode(true);
	}

	public class customsAdapter extends BaseAdapter {
		
		
		HashMap<Integer, View> lmap = new HashMap<Integer,View>();
        //public static HashMap<Integer, Boolean> isChecked;
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrayList3.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arrayList3.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			final int delete = arg0;
			final ViewHolder viewHolder;
			
			if(lmap.get(arg0) ==null){
				arg1 = View
						.inflate(CollectActivity.this, R.layout.list_item_me_collect, null);
				viewHolder = new ViewHolder(arg1);
				arg1.setTag(viewHolder);
				lmap.put(arg0, arg1);
				
			}else{
				arg1 = lmap.get(arg0);
				viewHolder = (ViewHolder)arg1.getTag();
			}
			 Picasso.with(CollectActivity.this).load(Tools.baseUrl+arrayList3.get(arg0).getImage())
				.placeholder(R.drawable.ic_launcher).into(viewHolder.imageView);
			//viewHolder.imageView.setImageResource(arrayList3.get(arg0).getImage());
			
			viewHolder.textView.setText(arrayList3.get(arg0).getTitle()+"");
			viewHolder.time.setText("收藏时间: "+arrayList3.get(arg0).getTime());
			viewHolder.type.setText("类型: "+arrayList3.get(arg0).getType());
			if(getDelete(delete)){
				viewHolder.imageView2.setChecked(true);
				//viewHolder.textImage.setText("true");
			}else{
				viewHolder.imageView2.setChecked(false);
				//viewHolder.textImage.setText("false");
			}
			
			viewHolder.linearLayout.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View arg0) {
					// TODO Auto-generated method stub
					if (popupWindow == null) {
						VisiblePopupWindow();
					}

					return true;
				}
			});
			
			if(isShowRadioButton) {
				viewHolder.imageView2.setVisibility(View.VISIBLE);
	        }else {
	        	viewHolder.imageView2.setVisibility(View.INVISIBLE);
	        }
			
			
            viewHolder.imageView2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!getDelete(delete)){
						viewHolder.imageView2.setChecked(true);
						setDelete(delete);
						
							
						
					}else{
						viewHolder.imageView2.setChecked(false);
						unsetDelete(delete);
			
					}
					
				}
			});
            viewHolder.linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(!isShowRadioButton){	
					if(arrayList3.get(delete).getType().equals("帖子"))	{
						
					}else {
						ShowService showService = new ShowService();
						showService.show(arrayList3.get(delete).getType(),arrayList3.get(delete).getTitle(),CollectActivity.this);
					}
					
					}
					if(isShowRadioButton)
					{
						if(!getDelete(delete)){
							viewHolder.imageView2.setChecked(true);
							setDelete(delete);
						//	notifyDataSetChanged();
							
						}else{
							viewHolder.imageView2.setChecked(false);
							unsetDelete(delete);
						//	notifyDataSetChanged();
						}
					}
				}
			});
			
 
			return arg1;
		}
		
		
		class ViewHolder{
		    TextView textView,time,type;
		    LinearLayout linearLayout;
		    ImageView imageView;
		    RadioButton imageView2;
			
			public ViewHolder(View arg1){
				linearLayout = (LinearLayout) arg1
						.findViewById(R.id.myLinearLayout);
				imageView = (ImageView) arg1
						.findViewById(R.id.imageView1);
				time = (TextView) arg1.findViewById(R.id.time);
				textView = (TextView) arg1.findViewById(R.id.textView1);
				type = (TextView) arg1.findViewById(R.id.type);
				imageView2 = (RadioButton) arg1
						.findViewById(R.id.imageView2);
			}
		}
		
		
		public void setDelete(int i){
			arrayList3.get(i).setDelete(true);
			//list.set(i, true);
			notifyDataSetChanged();
			
		}
		public void unsetDelete(int i){
			//list.set(i, false);
			arrayList3.get(i).setDelete(false);
			notifyDataSetChanged();
			
		}
		public boolean getDelete(int i){
			return arrayList3.get(i).isDelete();
		}
		

	}
	
	
	
	

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		searchItem(arg0);
		imageList.clear();
		
		
	    if(popupWindow!=null){
	    	popupWindow.dismiss();
			popupWindow = null;
	    	VisiblePopupWindow();
	    }else{
	    	adapter.notifyDataSetChanged();
	    }
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	//查询
	public void searchItem(String arg0) {
		arrayList2 = new ArrayList<Collect>();
		for (int i = 0; i < arrayList1.size(); i++) {
			int index = arrayList1.get(i).getTitle().indexOf(arg0);
			if (index != -1) {
				arrayList2.add(arrayList1.get(i));
			}
		}
		arrayList3 = new ArrayList<Collect>(arrayList2);

	}
	//删除
	public void deleteItem(){
		arrayList2 = new ArrayList<Collect>();
		ArrayList<Integer> list1 =new ArrayList<Integer>();
		for(int j=0;j<arrayList3.size();j++)
				{
					if(!arrayList3.get(j).isDelete()){
						arrayList2.add(arrayList3.get(j));
					}else{
						list1.add(arrayList3.get(j).getId());
						for(int i=0;i<arrayList1.size();i++){
							if(arrayList1.get(i).getId()==arrayList3.get(j).getId())
							{
								arrayList1.remove(i);
								break;
							}
						}
					}
				}
				
		showCollectService = new ShowCollectService();
		showCollectService.deletesCollect(list1, this);
		arrayList3 = new ArrayList<Collect>(arrayList2);
		
	}

	public void VisiblePopupWindow() { // 显示底层浮动框
		isShowRadioButton = true;
		adapter.notifyDataSetChanged();
	
		popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, 70, true);
		popupWindow.setInputMethodMode(popupWindow.INPUT_METHOD_NEEDED);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//设置popupwindow不被软键盘遮挡

		popupWindow.setAnimationStyle(R.drawable.anim_pop);// 设置加载动画

		/*
		 * 点击非popupwindow区域，popupwindow会消失
		 */

		popupWindow.setFocusable(false);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(false); // 设置点击非popupwindow区域不消失
		/*
		 * popupWindow.setTouchInterceptor(new OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View arg0, MotionEvent arg1) { //
		 * TODO Auto-generated method stub
		 * 
		 * return false;//返回true,touch事件被拦截，点击非popupwindow区域不会消失 } });
		 */
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub

			}
		});

		popupWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.popup_me_backgrond));// 要为设置popupwindow背景颜色才有效

		popupWindow.showAtLocation(findViewById(R.id.linearlayout_collect),
				Gravity.BOTTOM, 0, 0);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView3 = (TextView) view.findViewById(R.id.textView3);
		textView4 = (TextView) view.findViewById(R.id.textView4);
		textView5 = (TextView) view.findViewById(R.id.textView5);
		textView2.setFocusable(true);
		textView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// searchView.setEnabled(true);
				isShowRadioButton = false;
				isCheckRadioButton = false;
				
				for(int i=0;i<arrayList3.size();i++)
					arrayList3.get(i).setDelete(false);
				adapter.notifyDataSetChanged();
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
		textView3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// searchView.setEnabled(true);
				deleteItem();
				isShowRadioButton = false;
				isCheckRadioButton = false;
				adapter.notifyDataSetChanged();
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
		textView4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// searchView.setEnabled(true);
				if(!isCheckRadioButton){
				isCheckRadioButton = true;
				for(int i=0;i<arrayList3.size();i++)
					arrayList3.get(i).setDelete(true);
					
				adapter.notifyDataSetChanged();
				}else{
					isCheckRadioButton = false;
					for(int i=0;i<arrayList3.size();i++)
						arrayList3.get(i).setDelete(false);
					adapter.notifyDataSetChanged();
				}
				
			}
		});
		
		textView5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// searchView.setEnabled(true);
				isShowRadioButton = false;
				isCheckRadioButton = false;
				
				for(int i=0;i<arrayList3.size();i++){
					if(arrayList3.get(i).isDelete())
					{
						showShare(arrayList3.get(i));
					}
					arrayList3.get(i).setDelete(false);
				}
				adapter.notifyDataSetChanged();
				popupWindow.dismiss();
				popupWindow = null;
				
				
				
			}
		});
		
		

	}
	private void showShare(Collect collect) {
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
		 oks.setTitle(collect.getTitle()+"");
		 // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
		 oks.setTitleUrl("http://839n24ynkrh.s.cn.vc");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("来自:"+collect.getUsername()+"的分享");
		 //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
		 oks.setImageUrl(Tools.baseUrl+collect.getImage());
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://839n24ynkrh.s.cn.vc");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite("ShareSDK");
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://839n24ynkrh.s.cn.vc");
		
		// 启动分享GUI
		 oks.show(this);
		
			 
		 }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {// 没有作用
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			Log.e("111", "0000");
			if (popupWindow != null) {
				listView1.setAdapter(new customsAdapter());
				popupWindow.dismiss();
				popupWindow = null;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void visibleImageDeleteButton() {
		Log.e("进入button改变","00000");
		if (imageList.size()>0) {
			for (RadioButton tv : imageList) {
				tv.setVisibility(0);
				Log.e("设置成功", "000000000000");
			}
		}else{
			Log.e("imageList为空", "000000000000");
		}
	}
	
	public class LocationReceiver extends BroadcastReceiver {
        //必须要重载的方法，用来监听是否有广播发送
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			 String intentAction = arg1.getAction();
	            if (intentAction.equals("location.refresh")) {
	               // Log.i("接收广播.....", arg1.getBooleanExtra("falg", false)+"");
	            	String s = arg1.getStringExtra("s");
	        		Gson gson = new Gson();
	        		arrayList1 = gson.fromJson(s,new TypeToken<ArrayList<Collect>>(){}.getType());
	            	
	               }else{
	            	   Log.i("....", "yizhuce");
	               }
	            }
	}
    
	
	//下拉刷新
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					int i = 2;
					locationReceiver = new LocationReceiver();
			        IntentFilter filter = new IntentFilter();
			        filter.addAction("location.refresh");
			        registerReceiver(locationReceiver, filter);
				    showCollectService.showCollect(i,CollectActivity.this);
					Thread.sleep(1000);
					Message message = new Message();
					message.obj = arrayList1;
					handler.sendMessage(message);
					unregisterReceiver(locationReceiver);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				super.run();
			}
			
		}.start();
		
	}
	//加载更多
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
					
					Message message = new Message();
					message.obj = arrayList1;
					handler.sendMessage(message);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				super.run();
			}
			
		}.start();
		
		
	}



}

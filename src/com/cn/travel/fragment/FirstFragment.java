package com.cn.travel.fragment;

import java.util.ArrayList;


import com.cn.travel.activity.find.NoteActivity;
import com.cn.travel.activity.first.HomeActivity;
import com.cn.travel.activity.first.SearchActivity;
import com.cn.travel.activity.first.fragmentfirstgonglue;
import com.cn.travel.activity.first.fragmentfirsttu1Activity;
import com.cn.travel.activity.first.food.FoodService;
import com.cn.travel.activity.me.ReportOrderActivity;
import com.cn.travel.activity.navigation.Hotel;
import com.cn.travel.activity.navigation.MapActivity;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.PointBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.domain.LoginActivity;
//import com.cn.travel.bean.PointBean;
import com.cn.travel.service.me.HotelService;
import com.cn.travel.service.me.PointService;
//import com.cn.travel.service.me.PointService;


import com.cn.travle.R;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstFragment extends Fragment {
	private ViewPager viewPager;
	private LinearLayout point_group;
	
	private TextView image_desc;
	private TextView textView6;
	private EditText search_et_input;
	private RelativeLayout daohang;
	private RelativeLayout jiudian;
	private RelativeLayout jiaotong;
	private RelativeLayout gonglue;
	private RelativeLayout jingdian;
	private RelativeLayout meishi;
	private ImageView imageView4;
	private HotelBean hotelbean = new HotelBean();
	private PointBean pointbean = new PointBean();
	private String address;
	
	
	// 图片资源id
	private final int[] images = { R.drawable.fragment_first_image_tu1,
			R.drawable.fragment_first_image_tu2,
			R.drawable.fragment_first_image_tu3, R.drawable.fragment_first_image_tu4, R.drawable.fragment_first_image_tu5 };
	// 图片标题集合
	private final String[] imageDescriptions = { "1", "2", "3", "4", "5" };

	private ArrayList<ImageView> imageList;
	// 上一个页面的位置
	protected int lastPosition = 0;

	// 判断是否自动滚动viewPager
	private boolean isRunning = true;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 执行滑动到下一个页面
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			if (isRunning) {
				// 在发一个handler延时
				handler.sendEmptyMessageDelayed(0, 5000);
			}
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_first,container,false);
		
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		point_group = (LinearLayout) view.findViewById(R.id.point_group);
		image_desc = (TextView) view.findViewById(R.id.image_desc);
		search_et_input = (EditText) view.findViewById(R.id.search_et_input);
		image_desc.setText(imageDescriptions[0]);
		daohang = (RelativeLayout) view.findViewById(R.id.daohang);
		jiudian = (RelativeLayout) view.findViewById(R.id.jiudian);
		jiaotong = (RelativeLayout) view.findViewById(R.id.jiaotong);
		gonglue = (RelativeLayout) view.findViewById(R.id.gong_lue);
		jingdian = (RelativeLayout)view.findViewById(R.id.jingdian);
		meishi = (RelativeLayout)view.findViewById(R.id.meishi);
		imageView4= (ImageView)view.findViewById(R.id.imageView4);
		textView6=(TextView)view.findViewById(R.id.pingjia);
          textView6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),ReportOrderActivity.class);
				startActivity(intent);
			}
		});
		
		daohang.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),MapActivity.class);
				startActivity(intent);
			}
		});
		jiudian.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				address = "东";
				HotelService hotelservice = new HotelService();
				hotelservice.hotellist(hotelbean, getActivity(), address);
			}
		});
		jiaotong.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
			}
		});
		gonglue.setOnClickListener(new View.OnClickListener() {
			
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
		jingdian.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				address = "东";
				PointService pointservice = new PointService();
				pointservice.pointlist(pointbean, getActivity(), address);
			}
		});
		meishi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				FoodService foodService=new FoodService();
				foodService.FoodList(getActivity());
			}
		});
		// 跳转到搜索的界面
		search_et_input.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						SearchActivity.class);
				startActivity(intent);

			}
		});
		
		imageView4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						fragmentfirstgonglue.class);
				startActivity(intent);

			}
		});
		
		

		// 初始化图片资源
		imageList = new ArrayList<ImageView>();
		for (int i : images) {
			// 初始化图片资源
			ImageView imageView = new ImageView(getActivity());
			imageView.setBackgroundResource(i);
			imageView.setId(i);
			imageView.setOnClickListener(new pagerImageOnClick());
			imageList.add(imageView);
			
			// 添加指示小点
			ImageView point = new ImageView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					32, 32);
			params.rightMargin = 20;
			params.bottomMargin = 10;
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.yuanw);
			if (i == R.drawable.fragment_first_image_tu1) {
				// 默认聚焦在第一张
				point.setBackgroundResource(R.drawable.yuanb);
				point.setEnabled(true);
			} else {
				point.setEnabled(false);
			}

			point_group.addView(point);
		}

		viewPager.setAdapter(new MyPageAdapter());
		// 设置当前viewPager的位置
		
		viewPager.setCurrentItem(Integer.MAX_VALUE / 2
				- (Integer.MAX_VALUE / 2 % imageList.size()));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 页面切换后调用， position是新的页面位置

				// 实现无限制循环播放
				position %= imageList.size();

				image_desc.setText(imageDescriptions[position]);

				// 把当前点设置为true,将上一个点设为false；并设置point_group图标
				point_group.getChildAt(position).setEnabled(true);
				point_group.getChildAt(position).setBackgroundResource(
						R.drawable.yuanb);// 设置聚焦时的图标样式
				point_group.getChildAt(lastPosition).setEnabled(false);
				point_group.getChildAt(lastPosition).setBackgroundResource(
						R.drawable.yuanw);// 上一张恢复原有图标
				lastPosition = position;
				
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// 页面正在滑动时间回调

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// 当pageView 状态发生改变的时候，回调

			}
		});

		// 循环过程
		// 1.定时器：Timer
		// 2.开子线程：while true循环
		// 3.ClockManger
		// 4.用Handler发送延时信息，实现循环

		handler.sendEmptyMessageDelayed(0, 5000);
		return view;
	}
	
	private class pagerImageOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
        	
            switch (v.getId()) {
                case R.drawable.fragment_first_image_tu1:
                	Intent intent = new Intent(getActivity(),
    						fragmentfirsttu1Activity.class);
    				startActivity(intent);
                    break;
                case R.drawable.fragment_first_image_tu2:
                    Toast.makeText(getActivity(), "图片2被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.drawable.fragment_first_image_tu3:
                    Toast.makeText(getActivity(), "图片3被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.drawable.fragment_first_image_tu4:
                    Toast.makeText(getActivity(), "图片4被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.drawable.fragment_first_image_tu5:
                    Toast.makeText(getActivity(), "图片5被点击", Toast.LENGTH_SHORT).show();
                    break;
                
            }
        }
    }
	
	public void onDestroy() {
		// 停止滚动
		isRunning = false;
		super.onDestroy();
	}

	public class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// 判断view和Object对应是否有关联关系
			if (view == object) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 获得相应位置上的view； container view的容器，其实就是viewpage自身,
			// position: viewpager上的位置
			// 给container添加内容
			container.addView(imageList.get(position % imageList.size()));

			return imageList.get(position % imageList.size());
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 销毁对应位置上的Object
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
			object = null;
		}

	}
	
}

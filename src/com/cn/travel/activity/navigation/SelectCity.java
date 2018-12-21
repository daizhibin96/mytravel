package com.cn.travel.activity.navigation;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.travel.fragment.NavigationFragment;
import com.cn.travle.R;

public class SelectCity extends Activity {

	private ListView lview;
	private ListView lview1;
	private String[] citys = {"东莞"};
	private String[] address = {"松山湖","莞城","虎门"};
	private int[] imgs = {R.drawable.songshanhu01,R.drawable.guancheng01,R.drawable.humen01};
	private ImageView back;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigation_selectcity);
        Log.e("1","...");
        initView();
        Log.e("2","...");
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter();
        lview.setAdapter(myBaseAdapter);
        CitysAdapter citysAdapter = new CitysAdapter();
        lview1.setAdapter(citysAdapter);
        back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SelectCity.this.finish();
			}
        	
        });
	}
	
	public void initView(){
		lview = (ListView)this.findViewById(R.id.selectcity_list);
		lview1 = (ListView)this.findViewById(R.id.city_detail);
		back = (ImageView)this.findViewById(R.id.selectcity_back);
	}
	
	
	class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return citys.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return citys[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View converView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(SelectCity.this, R.layout.list_item_selectcity, null);
			TextView tv = (TextView)view.findViewById(R.id.selectcity_list_city);
			tv.setText(citys[position]);
			return view;
		}
		
	}
	
	class CitysAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return imgs[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View converView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(SelectCity.this, R.layout.list_item_citys, null);
			final TextView tv = (TextView)view.findViewById(R.id.selectcity_list_citys);
			tv.setText(address[position]);
			final ImageView img = (ImageView)view.findViewById(R.id.list_cityimage);
			BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            InputStream is = getResources().openRawResource(imgs[position]);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
            img.setBackgroundDrawable(bd);
			tv.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("city",tv.getText().toString());
					intent.putExtra("img", imgs[position]);
					setResult(1,intent);
					SelectCity.this.finish();
				}
				
			});
			img.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.putExtra("city",tv.getText().toString());
					intent.putExtra("img", imgs[position]);
					setResult(1,intent);
					SelectCity.this.finish();
				}
				
			});
			return view;
		}
		
	}

	
	
}

package com.cn.travel.activity.first.food;

import java.util.ArrayList;
import java.util.List;

import com.cn.travle.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodListActivity extends Activity {
	TextView title;
	private ArrayList<FoodBean> foodList = new ArrayList<FoodBean>();
	//private ArrayList<Food> foodList = new ArrayList<Food>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		
		/*
		 * if(intent1.getExtras()==null){ FoodService.FoodList(this); }
		 */
		 
		Intent intent1 = getIntent();
		
		String d = intent1.getStringExtra("aaa");
		
		Gson gson = new Gson();
		foodList = new ArrayList<FoodBean>();
		foodList = gson.fromJson(d, new TypeToken<ArrayList<FoodBean>>() {
		}.getType());
		//Toast.makeText(FoodActivity.this, foodList.toString(),
				//Toast.LENGTH_SHORT).show();
       
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				//WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_food);
		title = (TextView) this.findViewById(R.id.head_center_text);
		title.setText("东莞美食");
		// intFood();
		FoodAdapter adapter = new FoodAdapter(FoodListActivity.this,
				R.layout.food_listitem, foodList);
		//final FoodAdapter1 adapter = new FoodAdapter1(FoodActivity.this,
			//foodList);
		ListView listView = (ListView) findViewById(R.id.foodlistView);
		listView.setAdapter(adapter);
		
	}
	/*
	 * private void intFood() { Food a=new Food("百年老店",
	 * R.drawable.activity_food1); foodList.add(a); }
	 */

}

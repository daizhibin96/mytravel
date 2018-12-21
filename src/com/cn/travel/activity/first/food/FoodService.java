package com.cn.travel.activity.first.food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class FoodService {
	static boolean falg=false;
	private static ArrayList<FoodBean> foodList=new ArrayList<FoodBean>();
	private static FoodBean food=new FoodBean();
	
	public FoodService(){
		super();
	}
	
	public  void FoodList(final Context context)
	{
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_food;
		
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						try {
							int resCode = 100;
							//Toast.makeText(context, "成功。。。。。",Toast.LENGTH_SHORT).show();
							//JSONObject jsonObject = new JSONObject(s);
							JSONArray jsonArray = new JSONArray(s.toString().trim());
							if(jsonArray != null){
								resCode = 200;
							}
						if(resCode==200){
						Intent intent = new Intent(context,FoodListActivity.class);
						Log.i("食物表", s);
						intent.putExtra("aaa", s);
						context.startActivity(intent);}
						else {
							Toast.makeText(context, "获取数据失败。。。。。",
									Toast.LENGTH_LONG).show();
						}}
						catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(context, "失败。。。。。",
									Toast.LENGTH_LONG).show();
						}
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				return null;

			}
		};
		// 3 将post请求添加到队列中
				requestQueue.add(stringRequest);
		
	}

}

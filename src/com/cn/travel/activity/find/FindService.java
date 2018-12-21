package com.cn.travel.activity.find;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.travel.activity.first.food.Comment1;

import com.cn.travel.fragment.FindFragment;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class FindService {
	
	public  void getmsg(final Context context){
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url=Tools.url_forum;
		
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						
						Intent intenta = new Intent();
						
						//intent.putExtra("msg1", food.getTitle());//介绍信息
						//intent.putExtra("pic", food.getImage());//封面图片
						Log.i("传过来的游记", s);
						intenta.putExtra("aaa", s);//
						//context.startActivity(intent);
						intenta.setAction("com.youji");
						context.sendBroadcast(intenta);
						
						
						
						
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
	
	public static void login(final forumbean user, final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_insertForum;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						try {
							JSONObject jsonObject = new JSONObject(s);

							int resCode = jsonObject.getInt("resCode");

							if (resCode == 200) {
								Toast.makeText(context, "上传成功",
										Toast.LENGTH_LONG).show();
								
							} else {
								Toast.makeText(context, "上传失败",
										Toast.LENGTH_LONG).show();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				}) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				Map<String,String >map = new HashMap<String,String>();
				Gson gson=new Gson ();
				String json=gson.toJson(user, forumbean.class);
				map.put("json",json );
				return map;//传输整个javabean

				
			}

		};

		requestQueue.add(stringRequest);
	}

}

package com.cn.travel.activity.find;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class FindforumService {
	
	
	
	public   static void sendforum(final forumbean food,final Context context){
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url=Tools.url_foodcomments;
		
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						
						Intent intent = new Intent(context,FindContentActivity.class);
						intent.putExtra("id", food.getUserid());//获取用户Id
						intent.putExtra("title", food.getTitle());//获取标题
						intent.putExtra("pic", food.getImage());//封面图片
						intent.putExtra("aaa", food.getUserimage());//用户头像
						intent.putExtra("name", food.getUsername());//用户
						intent.putExtra("time", food.getTime());
						intent.putExtra("inputString", food.getInputStr());//获取帖子文本信息
						intent.putExtra("image", food.getImagePath());//获取文本图片地址
						context.startActivity(intent);
						
						
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				 Map<String,String >map = new HashMap<String,String>();
				Gson gson=new Gson ();
				String json=gson.toJson(food,forumbean.class);
				map.put("json",json );
				return map;//传输整个javabean
				
				
			}
		};
		// 3 将post请求添加到队列中
				requestQueue.add(stringRequest);
		
	}
	

}

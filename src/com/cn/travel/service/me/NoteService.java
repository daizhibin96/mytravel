package com.cn.travel.service.me;

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
import com.cn.travel.activity.find.NoteActivity;
import com.cn.travel.bean.UserBean;
import com.cn.travel.info.InfoActivity;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class NoteService {
	private static UserBean us=new UserBean();
	public static void sendNote(final Object user,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_updateUser;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject =  new JSONObject(s);
					
					int resCode = jsonObject.getInt("resCode");
					
					if(resCode == 200){
						
						
						Toast.makeText(context, "发布成功", Toast.LENGTH_LONG).show();
						
						Intent intent = new Intent(context,NoteActivity.class);
						
						context.startActivity(intent);
						
						
					}else{
						Toast.makeText(context, "发布失败", Toast.LENGTH_LONG).show();
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
				Gson gson=new Gson();
				String str=gson.toJson(user);
//				JSONObject json = JSONObject.fromObject(user);//将java对象转换为json对象
//				String str = json.toString();//将Json对象转换为字符串
                Map<String,String >map = new HashMap<String,String>();
				
				map.put("json", str);
				
				
				return map;
				
			}
		};
		requestQueue.add(stringRequest);
	}
	
	
	public static UserBean base(final UserBean Id,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_base;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
			//	Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
				Gson gson=new Gson();
				us = gson.fromJson(s,UserBean.class);
				Intent intent = new Intent(context,InfoActivity.class);
				intent.putExtra("aaa", new Gson().toJson(us));
				context.startActivity(intent);
			//	Toast.makeText(context, us.getAddress(), Toast.LENGTH_SHORT).show();
			
				
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
				
				map.put("id",Id.getId());
				
				return map;
				
			}
		};
		requestQueue.add(stringRequest);
	
		return us;
	}
	
}

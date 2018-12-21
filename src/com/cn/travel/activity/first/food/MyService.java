package com.cn.travel.activity.first.food;

import java.util.ArrayList;
import java.util.HashMap;
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

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyService {
	 boolean falg1 = false;
	  int resCode;
     boolean falg=false;
	public ArrayList<Comment1> commentlist=new ArrayList<Comment1>();
	private Comment1 comment1=new Comment1();
	
	public MyService(){
		super();
	}
	//获取美食信息
	public static  void commentlist(final FoodBean food,final Context context){
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url=Tools.url_foodcomments;
		
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						
						Intent intent = new Intent(context,FirstXiangqingActivity.class);
						intent.putExtra("id", food.getId());//获取美食Id
						intent.putExtra("msg1", food.getMessage());//介绍信息
						intent.putExtra("title", food.getName());
						intent.putExtra("pic", food.getImageID());//封面图片
						intent.putExtra("aaa", s);//图片
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
				String json=gson.toJson(food,FoodBean.class);
				map.put("json",json );
				return map;//传输整个javabean
				
				
			}
		};
		// 3 将post请求添加到队列中
				requestQueue.add(stringRequest);
		
	}
	
	//获取评论信息
	public static void login(final Comment1 user, final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_insertfoodcomments;

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
				String json=gson.toJson(user, Comment1.class);
				map.put("json",json );
				return map;//传输整个javabean

				
			}

		};

		requestQueue.add(stringRequest);
	}
	
	public static void shoucang(final CollectBean user, final Context context) {
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_insertCollect;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						try {
							JSONObject jsonObject = new JSONObject(s);

							int resCode = jsonObject.getInt("resCode");

							if (resCode == 200) {
								Toast.makeText(context, "收藏成功",
										Toast.LENGTH_LONG).show();
								
							} else {
								Toast.makeText(context, "收藏失败",
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
				String json=gson.toJson(user, CollectBean.class);
				map.put("json",json );
				return map;//传输整个javabean

				
			}

		};

		requestQueue.add(stringRequest);
	}
	//删除收藏信息
	
	public static void deleteshoucang(final CollectBean user, final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_deleteCollect;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						try {
							JSONObject jsonObject = new JSONObject(s);

							int resCode = jsonObject.getInt("resCode");

							if (resCode == 200) {
								Toast.makeText(context, "取消收藏成功",
										Toast.LENGTH_LONG).show();
								
							} else {
								Toast.makeText(context, "取消收藏失败",
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
				String json=gson.toJson(user, CollectBean.class);
				map.put("json",json );
				return map;//传输整个javabean

				
			}

		};

		requestQueue.add(stringRequest);
	}
	
	//获取用户收藏信息，用于查�?
	public   boolean getcollect(final CollectBean user,final Context context)
	{
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_existfood;
		
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(s);
							resCode = jsonObject.getInt("resCode");
							Log.i("rescode", resCode+"");
							falg1 = true;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
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
				String json=gson.toJson(user, CollectBean.class);
				map.put("json",json );
				return map;//传输整个javabean

			}
		};
		// 3 将post请求添加到队列中
				requestQueue.add(stringRequest);
				if(falg1 == true){
					if(resCode == 200){
						Toast.makeText(context, "美食被收藏", Toast.LENGTH_LONG).show();
						return true;
						
					}else{
						
						Log.i("rescode2", resCode+"");
						Toast.makeText(context, "美食没被收藏", Toast.LENGTH_LONG).show();
						return false;
						
					}
				}
				return falg1;
		
	}

}

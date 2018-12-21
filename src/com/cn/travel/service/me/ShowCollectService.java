package com.cn.travel.service.me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cn.travel.activity.me.CollectActivity;
import com.cn.travel.domain.IDclass;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class ShowCollectService {

	public ShowCollectService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void showCollect(final int i ,final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_showCollect;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						
						if(i==1){
								Intent inte = new Intent();
								inte.setClass(context, CollectActivity.class);
								
								
								inte.putExtra("s", s);
								context.startActivity(inte);
						}else{
								
								Intent intent = new Intent();
								intent.putExtra("s", s);
							    intent.setAction("location.refresh");
								context.sendBroadcast(intent);
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
				Map<String, String> map = new HashMap<String, String>();

				
				map.put("json", IDclass.UserName);

				return map;
			}

		};

		requestQueue.add(stringRequest);
		
	}
	
	public void deletesCollect(final ArrayList<Integer> list,final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_deletesCollect;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						


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
				Map<String, String> map = new HashMap<String, String>();
                
				Gson gson = new Gson();
				String  json = gson.toJson(list);
				map.put("json", json);

				return map;
			}

		};

		requestQueue.add(stringRequest);
		
	}


}

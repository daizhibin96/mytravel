package com.cn.travel.service.me;

import java.util.HashMap;
import java.util.Map;

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
import com.cn.travel.activity.navigation.Pay;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class UpdateOrderState {

	String type;
	
	public UpdateOrderState(){
		super();
	}
	
	public void updateOrder(final Context context,final String sbean,final int num){
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_updateorder;
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
				if(num==1){
					type = "¾Æµê";
				}
				if(num==2){
					type = "¾°µã";
				}
				//map.put("address", address);
				map.put("json", sbean);
				map.put("type", type);
				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
}

package com.cn.travel.service.me;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.travel.activity.navigation.Pay;
import com.cn.travel.bean.HistoryBean;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HistoryService {

	public HistoryService(){
		super();
	}
	
	public void history(final Context context,final HistoryBean historybean){
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_history;
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

				//map.put("address", address);
				Gson gson = new Gson();
				String json = gson.toJson(historybean,HistoryBean.class);
				map.put("json", json);
				Log.e("浏览历史记录","成功");
				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
}

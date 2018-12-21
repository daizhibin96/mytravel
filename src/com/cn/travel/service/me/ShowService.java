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

import com.cn.travel.activity.first.food.FoodBean;
import com.cn.travel.activity.first.food.MyService;

import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.PointBean;
import com.cn.travel.domain.IDclass;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;


public class ShowService {

	public ShowService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void show(final String type,final String name,final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_show;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						switch (type) {
						case "æ∆µÍ":
							
							HotelBean hbean = new HotelBean();
							hbean = gson.fromJson(s, HotelBean.class);
							CollectionService collect = new CollectionService();
							collect.collectService(context, IDclass.UserName,hbean);
							
							break;
                        case "æ∞µ„":
                        	
							PointBean pointbean = new PointBean();
							pointbean = gson.fromJson(s, PointBean.class);
							CollectService collect0 = new CollectService();
							collect0.collectService(context, IDclass.UserName,pointbean);
							break;
                        case "√¿ ≥":
                        	FoodBean food = new FoodBean();
							food = gson.fromJson(s, FoodBean.class);
							MyService my = new MyService();
							my.commentlist(food,context);
                        	break;

						default:
							break;
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

				
				map.put("type", type);
				map.put("name", name);

				return map;
			}

		};

		requestQueue.add(stringRequest);
		
	}
	
	

}

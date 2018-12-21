package com.cn.travel.service.me;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
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
import com.cn.travel.activity.navigation.HotelList;
import com.cn.travel.activity.navigation.Pay;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class HotelOrderService {

	public HotelOrderService(){
		super();
	}
	
	public void hotelOrder(final Context context,final HotelOrderBean orderBean){
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_hotelorder;
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
				String json = gson.toJson(orderBean,HotelOrderBean.class);
				map.put("json", json);
				Log.e("....","�ɹ�");
				Intent intent = new Intent(context,Pay.class);
				context.startActivity(intent);
				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
	
}

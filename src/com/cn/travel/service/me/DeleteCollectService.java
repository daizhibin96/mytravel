package com.cn.travel.service.me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.travel.activity.navigation.CollectBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class DeleteCollectService {

	boolean falg = false;
	ArrayList<CollectBean> arrayList = new ArrayList<CollectBean>();
	CollectBean collect = new CollectBean();
	
	public DeleteCollectService(){
		super();
	}
	
	public void addCollection(final Context context,final CollectBean collectbean){
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_deleteCollect;	

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
				String c = gson.toJson(collectbean);
				map.put("json", c);

				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
}

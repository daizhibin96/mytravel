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

import com.cn.travel.activity.me.BrowseActivity;
import com.cn.travel.activity.me.CollectActivity;
import com.cn.travel.domain.IDclass;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class ShowBrowseService {

	public ShowBrowseService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void showBrowse(final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_showBrowse;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						
								Intent inte = new Intent();
								inte.setClass(context, BrowseActivity.class);
								
								
								inte.putExtra("s", s);
								context.startActivity(inte);
								


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

				
				map.put("json", IDclass.ID);

				return map;
			}

		};

		requestQueue.add(stringRequest);
		
	}
	
	public void deletesBrowse(final String show,final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_deletesBrowse;

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
                
				map.put("json", show);

				return map;
			}

		};

		requestQueue.add(stringRequest);
		
	}


}

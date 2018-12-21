package com.cn.travel.service.me;

import java.util.ArrayList;
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
import com.cn.travel.activity.me.HotelOrderPageActivity;
import com.cn.travel.activity.me.MyOrderActivity;
import com.cn.travel.activity.me.TicketOrderPageActivity;
import com.cn.travel.activity.me.UnPayOrderActivity;
import com.cn.travel.activity.navigation.HotelList;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.bean.PointOrderBean;
import com.cn.travel.info.InfoActivity;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderPageService {
	private static HotelBean hotelb=new HotelBean();
	private static PointOrderBean pob=new PointOrderBean();
	private static ArrayList< HotelBean> hotelList= new ArrayList< HotelBean>();
	private static ArrayList<PointOrderBean> pointorderList= new ArrayList< PointOrderBean>();
	
	
	public static void GetHotelOrder(final String Id,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_hotelpageorder;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
				
				Intent intentRe = new Intent();
				intentRe.putExtra("listhotelorder", s);
				intentRe.setAction("location.hotel");
				context.sendBroadcast(intentRe);
				
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
				
				map.put("json",Id);
				
				return map;
				
			}
		};
		requestQueue.add(stringRequest);
	
		//return hob;
	}
	
	public static void GetPointOrder(final String Id,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_touristorder;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
				Intent intentRe = new Intent();
				intentRe.putExtra("listpointorder", s);
				intentRe.setAction("location.point");
				context.sendBroadcast(intentRe);
				
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
				
				map.put("json",Id);
				
				return map;
				
			}
		};
		requestQueue.add(stringRequest);

	}
	
	public static void findHotel(final String address,final Context context){
		
		
		Log.e("hotellist  is ", "....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_hotel;	

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub
						Intent intentRe = new Intent();
						intentRe.putExtra("findhotel", s);
						intentRe.setAction("location.findhotel");
						context.sendBroadcast(intentRe);
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

				map.put("address", address);

				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
	
public static void findPoint(final String address,final Context context){
		
		
		Log.e("pointllist  is ", "....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_point;	

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub
						Intent intentRe = new Intent();
						intentRe.putExtra("findpoint", s);
						intentRe.setAction("location.findpoint");
						context.sendBroadcast(intentRe);
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

				map.put("address", address);

				return map;
			}

		};

		requestQueue.add(stringRequest);
	}

public static void DeleteOrder(final int id,final String type,final Context context){
	
	
	Log.e("pointllist  is ", "....");
	RequestQueue requestQueue = Volley.newRequestQueue(context);

	String url = Tools.url_deleteOrder;	

	StringRequest stringRequest = new StringRequest(Request.Method.POST,
			url, new Response.Listener<String>() {

				@Override
				public void onResponse(String s) {
					// TODO Auto-generated method stub
					Intent Delete=new Intent(context,UnPayOrderActivity.class);
					context.startActivity(Delete);
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
			String temp=type;
			if(temp.equals("¾Æµê")){
				HotelOrderBean hob1=new HotelOrderBean();
				hob1.setId(id);
				map.put("type", type);
				Gson gson=new Gson();
				String str1=gson.toJson(hob1);
				map.put("json",str1 );
			}else{
				PointOrderBean pob1=new PointOrderBean();
				pob1.setId(id);
				map.put("type", type);
				Gson gson=new Gson();
				String str2=gson.toJson(pob1);
				map.put("json",str2 );
			}			
			return map;
		}

	};

	requestQueue.add(stringRequest);
}
}

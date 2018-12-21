package com.cn.travel.service.me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.travel.activity.first.HomeActivity;
import com.cn.travel.activity.navigation.Hotel;
import com.cn.travel.activity.navigation.HotelDetail;
import com.cn.travel.activity.navigation.HotelList;
import com.cn.travel.activity.navigation.HotelSearch;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class HotelService{
	
	boolean falg=false;
	ArrayList<HotelBean> arrayList = new ArrayList<HotelBean>();
	private HotelBean hotelb = new HotelBean();
	
	public HotelService(){
		super();
	}
	
	public void hotellist(final HotelBean hotel,final Context context,final String address){
		
		
		Log.e("hotellist  is ", "....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_hotel;	

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						try {
							int resCode = 100;
							//Toast.makeText(context, "成功。。。。。",Toast.LENGTH_SHORT).show();
							//JSONObject jsonObject = new JSONObject(s);
							JSONArray jsonArray = new JSONArray(s.toString().trim());
							if(jsonArray != null){
								resCode = 200;
							}
							//resCode = jsonObject.getInt("resCode");
							//Toast.makeText(context, resCode, Toast.LENGTH_SHORT).show();
							//int resCode = 200;
							
							if (resCode == 200) {
								falg = true;
								for(int index=0; index< jsonArray.length();index++){
									JSONObject jsonobject = jsonArray.getJSONObject(index);
									hotelb = new HotelBean();
									hotelb.setId(jsonobject.getInt("id"));
									hotelb.setName(jsonobject.getString("name"));
									hotelb.setImage(jsonobject.getString("image"));
									hotelb.setAddress(jsonobject.getString("address"));
									hotelb.setTitle(jsonobject.getString("title"));
									hotelb.setMessage(jsonobject.getString("message"));
									hotelb.setLatlng(jsonobject.getDouble("latlng"));
									hotelb.setLonglng(jsonobject.getDouble("longlng"));
									hotelb.setOneprice(jsonobject.getString("oneprice"));
									hotelb.setTwoprice(jsonobject.getString("twoprice"));
									arrayList.add(hotelb);
								}
								if(address.equals("东莞")){
									Gson gson = new Gson();
									Intent intent = new Intent(context,HotelSearch.class);
									String str = gson.toJson(arrayList);
									intent.putExtra("aaa", str);
									context.startActivity(intent);
								}else{
									Gson gson = new Gson();
									Intent intent = new Intent(context,HotelList.class);
									String str = gson.toJson(arrayList);
									intent.putExtra("aaa", str);
									context.startActivity(intent);
								}
								//onDestroy();
							} else {
								Toast.makeText(context, "获取数据失败。。。。。",
										Toast.LENGTH_LONG).show();
							}
							

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(context, "失败。。。。。",
									Toast.LENGTH_LONG).show();
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

				map.put("address", address);

				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
	
}

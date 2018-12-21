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
import com.cn.travel.activity.navigation.HotelList;
import com.cn.travel.activity.navigation.PointList;
import com.cn.travel.activity.navigation.PointSearch;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.PointBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class PointService {

	private boolean falg=false;
	ArrayList<PointBean> arrayList = new ArrayList<PointBean>();
	private PointBean pointb = new PointBean();
	
	public PointService(){
		super();
	}
	
	public void pointlist(final PointBean point,final Context context,final String address){
		Log.e("pointlist  is ", "....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_point;	

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
									pointb = new PointBean();
									pointb.setId(jsonobject.getInt("id"));
									pointb.setName(jsonobject.getString("name"));
									pointb.setImage(jsonobject.getString("image"));
									pointb.setAddress(jsonobject.getString("address"));
									pointb.setTitle(jsonobject.getString("title"));
									pointb.setMessage(jsonobject.getString("message"));
									pointb.setLatlng(jsonobject.getDouble("latlng"));
									pointb.setLonglng(jsonobject.getDouble("longlng"));
									pointb.setPrice(jsonobject.getString("price"));						
									arrayList.add(pointb);
								}
								if(address.equals("东莞")){
									Gson gson = new Gson();
									Intent intent = new Intent(context,PointSearch.class);
									String str = gson.toJson(arrayList);
									intent.putExtra("aaa", str);
									context.startActivity(intent);
								}else{
									Gson gson = new Gson();
									Intent intent = new Intent(context,PointList.class);
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

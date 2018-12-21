package com.cn.travel.service.me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.travel.activity.navigation.CollectBean;
import com.cn.travel.activity.navigation.HotelDetail;
import com.cn.travel.activity.navigation.PointDetail;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.bean.PointBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class CollectService {

	boolean falg = false;
	ArrayList<CollectBean> arrayList = new ArrayList<CollectBean>();
	CollectBean collect = new CollectBean();
	
	public CollectService(){
		super();
	}
	
	public void collectService(final Context context,final String username,final PointBean pointbean){
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_collect;	

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
									collect = new CollectBean();
									collect.setId(jsonobject.getInt("id"));
									collect.setUsername(jsonobject.getString("username"));
									collect.setImage(jsonobject.getString("image"));
									collect.setTitle(jsonobject.getString("title"));
									collect.setTime(jsonobject.getString("time"));
									collect.setType(jsonobject.getString("type"));
									arrayList.add(collect);
								}
										Gson gson = new Gson();
										String str = gson.toJson(arrayList);
										Intent intent = new Intent(context,PointDetail.class);
										String point = gson.toJson(pointbean);
										CommentService comm = new CommentService();
										comm.collectService(context, str, point, 2);
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
				map.put("json", username);

				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
}

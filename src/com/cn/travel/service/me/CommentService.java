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
import com.cn.travel.bean.OrderCommentBean;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class CommentService {

	boolean falg = false;
	ArrayList<OrderCommentBean> arrayList = new ArrayList<OrderCommentBean>();
	OrderCommentBean cbean = new OrderCommentBean();
	
	public CommentService(){
		super();
	}
	
	public void collectService(final Context context,final String collect,final String sBean,final int type){
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_comment;	

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
									cbean = new OrderCommentBean();
									cbean.setUserimg(jsonobject.getString("userimg"));
									cbean.setUsername(jsonobject.getString("username"));
									cbean.setTime(jsonobject.getString("time"));
									cbean.setMessage(jsonobject.getString("message"));
									cbean.setName(jsonobject.getString("name"));
									arrayList.add(cbean);
								}
								if(type==1){
									Gson gson = new Gson();
									String str = gson.toJson(arrayList);
									Intent intent = new Intent(context,HotelDetail.class);
									intent.putExtra("collection", collect);
									intent.putExtra("hotel", sBean);
									intent.putExtra("comments", str);
									context.startActivity(intent);
								}
								else if(type==2){
									Gson gson = new Gson();
									String str = gson.toJson(arrayList);
									Intent intent = new Intent(context,PointDetail.class);
									intent.putExtra("collection", collect);
									intent.putExtra("point", sBean);
									intent.putExtra("comments", str);
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


				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
}

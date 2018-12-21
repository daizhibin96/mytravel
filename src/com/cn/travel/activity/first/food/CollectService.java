package com.cn.travel.activity.first.food;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

public class CollectService {
	//private ThreadGroup myThreads = new ThreadGroup("ServiceWorker");
	private Timer timer;
	private TimerTask task;
	boolean falg = false;
	int resCode;

	public CollectService() {
		super();

	}

	// 获取用户收藏信息，用于查
	public void getcollect(final CollectBean user, final Context context) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String url = Tools.url_existfood;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {

						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(s);
							resCode = jsonObject.getInt("resCode");
							Log.i("rescode", resCode + "");
                           if(resCode==200){
							falg = true;
                           }
							Intent intent1 = new Intent();
					          
					          intent1.putExtra("biaoji", falg);
					          intent1.setAction("com.shoucang");//用隐式意图来启动广播
				  	          context.sendBroadcast(intent1);
							

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				Map<String, String> map = new HashMap<String, String>();
				Gson gson = new Gson();
				String json = gson.toJson(user, CollectBean.class);
				map.put("json", json);
				return map;// 传输整个javabean

			}
		};
		// 3 将post请求添加到队列中
		requestQueue.add(stringRequest);
		

		

	}

	
	
	
	



	

}

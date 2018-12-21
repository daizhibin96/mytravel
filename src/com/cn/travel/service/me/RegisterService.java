package com.cn.travel.service.me;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cn.travel.bean.UserBean;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.utils.Tools;
import android.R.bool;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.VoicemailContract;
import android.util.Log;
import android.widget.Toast;

public class RegisterService {
	
	boolean falg = false;
	int resCode;
	Handler handler;
	public RegisterService(){
		super();
	}
	
	public void register(final UserBean user,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_register;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject =  new JSONObject(s);
					
					int resCode = jsonObject.getInt("resCode");
					
					if(resCode == 200){
						Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
						
						Intent intent = new Intent(context,LoginActivity.class);
						
						context.startActivity(intent);
					}else{
						Toast.makeText(context, "注册失败", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				
                Map<String,String >map = new HashMap<String,String>();
				
				map.put("phone", user.getPhone());
				map.put("password", user.getPassword());
				map.put("id", user.getId());
				
				return map;
				
			}
		};
		requestQueue.add(stringRequest);
	}
	
	

	public void exist(final UserBean user,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_exist;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject =  new JSONObject(s);
					
					resCode = jsonObject.getInt("resCode");
					if(resCode == 200){
						Toast.makeText(context, "该账户没注册", Toast.LENGTH_LONG).show();
						falg= true;
						
					}else{
						
						Toast.makeText(context, "该账户已注册", Toast.LENGTH_LONG).show();
						falg = false;
						
					}
					Intent intent = new Intent();
					intent.putExtra("falg", falg);
				    intent.setAction("location.reportsucc");
					context.sendBroadcast(intent);

//					falg = true;
//					Message message = new Message();
//					message.obj = falg;
//					message.arg1 = resCode;
//					ExistHandler.falg = true;
//					ExistHandler.we = false;
//					Log.i("....falg", ExistHandler.falg+"");
//					//handler.sendMessage(message);
//					Intent intent = new Intent(context,ExistActivity.class);
//					intent.putExtra("falg", falg);
//					intent.putExtra("phone", user.getPhone());
//					context.startActivity(intent);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				
                Map<String,String >map = new HashMap<String,String>();
				
				map.put("phone", user.getPhone());
				
				return map;
				
			}
		};
	
		requestQueue.add(stringRequest);
		
		if(falg == true){
			if(resCode == 200){
				Toast.makeText(context, "该账户没注册", Toast.LENGTH_LONG).show();
				falg= true;
				
			}else{
				
				Toast.makeText(context, "该账户已注册", Toast.LENGTH_LONG).show();
				falg = false;
				
			}
		} 
		Log.i("....rrrrr", falg+"");
		//return falg;
	}


}

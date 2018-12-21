package com.cn.travel.service.me;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;
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
import com.cn.travel.bean.UserBean;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.domain.LoginOKActivity;
import com.cn.travel.fragment.MeFragment;
import com.cn.travel.info.InfoActivity;
import com.cn.travel.utils.Tools;

public class LoginService {

	public LoginService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void login(final UserBean user, final Context context) {

		Log.e("user  is ", "....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_login;

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub

						try {
							JSONObject jsonObject = new JSONObject(s);

							int resCode = jsonObject.getInt("resCode");
						//	String id = jsonObject.getString("id");
							
//                            String id = (String) jsonObject.get("id")+"";
							if (resCode == 200) {
								//Toast.makeText(context, "登陆成功。。。。。",Toast.LENGTH_LONG).show();
								String userQ = jsonObject.getString("user");								
								//Toast.makeText(context, userQ,Toast.LENGTH_LONG).show();
								Intent inte = new Intent();
								inte.setClass(context, LoginOKActivity.class);
								//inte.putExtra("id", id);
//								inte.putExtra("loginSuccess", s);
//								inte.setAction("location.login");
								inte.putExtra("userQ", userQ);
								context.startActivity(inte);
								

							} else {
								Toast.makeText(context, "登陆失败...",
										Toast.LENGTH_LONG).show();
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
				Map<String, String> map = new HashMap<String, String>();

				map.put("name", user.getUsername());
				map.put("pass", user.getPassword());

				return map;
			}

		};

		requestQueue.add(stringRequest);
		
	}

}

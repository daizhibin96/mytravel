package com.cn.travel.service.me;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.cn.travel.activity.me.UnPayOrderActivity;
import com.cn.travel.bean.HotelOrderBean;
import com.cn.travel.bean.PointOrderBean;
import com.cn.travel.bean.UserBean;
import com.cn.travel.domain.LoginActivity;
import com.cn.travel.info.InfoActivity;
import com.cn.travel.utils.Tools;
import com.google.gson.Gson;

import android.R.bool;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.provider.VoicemailContract;
import android.util.Log;
import android.widget.Toast;


public class InfoService {
	
	boolean falg = false;
	int resCode;
	private static UserBean us=new UserBean();
	public InfoService(){
		super();
	}
	
	public static void sendInfo(final Object user,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_updateUser;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject =  new JSONObject(s);
					
					int resCode = jsonObject.getInt("resCode");
					
					if(resCode == 200){
						
						
						Toast.makeText(context, "修改成功", Toast.LENGTH_LONG).show();
						
						Intent intent = new Intent(context,InfoActivity.class);
						
						context.startActivity(intent);
						
						
					}else{
						Toast.makeText(context, "修改失败", Toast.LENGTH_LONG).show();
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
				Gson gson=new Gson();
				String str=gson.toJson(user);
//				JSONObject json = JSONObject.fromObject(user);//将java对象转换为json对象
//				String str = json.toString();//将Json对象转换为字符串
                Map<String,String >map = new HashMap<String,String>();
				
				map.put("json", str);
				
				
				return map;
				
			}
		};
		requestQueue.add(stringRequest);
	}
	
	

	public boolean exist(final UserBean user,final Context context){
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
					falg = true;
					
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
				Toast.makeText(context, "", Toast.LENGTH_LONG).show();
				return true;
				
			}else{
				
				Toast.makeText(context, "", Toast.LENGTH_LONG).show();
				return false;
				
			}
		}
		return falg;
	}
	
	public static UserBean base(final UserBean Id,final Context context){
		Log.e("user is.....",".....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		String url = Tools.url_base;
		
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

			@Override
			public void onResponse(String s) {
			//	Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
				Gson gson=new Gson();
				us = gson.fromJson(s,UserBean.class);
				Intent intent = new Intent(context,InfoActivity.class);
				intent.putExtra("aaa", new Gson().toJson(us));
				context.startActivity(intent);
			//	Toast.makeText(context, us.getAddress(), Toast.LENGTH_SHORT).show();
			
				
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
				
				map.put("id",Id.getId());
				
				return map;
				
			}
		};
		requestQueue.add(stringRequest);
	
		return us;
	}
	
	public static String upload(String post_url, ArrayList<String> files, Context context) {

        StringBuffer stringBuffer = new StringBuffer();

        Log.e("file upload..url is ",post_url+"====");
        try {
            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线

            URL url = new URL(post_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            //连接服务器后是否关闭连接（常连接）
            //conn.setRequestProperty("connection", "Keep-Alive");
            //浏览器类型
            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            //conn.setRequestProperty("richmj_token", DateUtil.getToken(context));

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
            int leng = files.size();
            int j = 1;
            for (int i = 0; i < leng; i++) {
                String fname = files.get(i);
                File file = new File(fname);
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(BOUNDARY);
                sb.append("\r\n");

                sb.append("Content-Disposition: form-data;name=\"picfile" + j + "\";filename=\"" + file.getName() + "\"\r\n");
                j++;
                sb.append("Content-Type:application/octet-stream\r\n\r\n");

                byte[] data = sb.toString().getBytes();
                out.write(data);
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个
                in.close();
            }
            out.write(end_data);
            out.flush();
            out.close();

            Log.e("上传文件返回===》","上传文件返回信息");
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }
	public static void FindPass(final String  ph,final String pa,final Context context){
		
		
		Log.e("pointllist  is ", "....");
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		String url = Tools.url_updateUserPassword;	

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						// TODO Auto-generated method stub
						Intent lg=new Intent(context,LoginActivity.class);
						context.startActivity(lg);
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
//					Gson gson=new Gson();
//					String str1=gson.toJson(hob1);
					map.put("phone",ph);
					map.put("password",pa);		
				return map;
			}

		};

		requestQueue.add(stringRequest);
	}
}

package com.cn.travel.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Utils {
	public static boolean saveUserInfo(Context context, String UserName,
			String passWord) {
		SharedPreferences sp = context.getSharedPreferences("data",
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("userName", UserName);
		edit.putString("passWord", passWord);
		edit.commit();
		return true;
	}

	public static Map<String, String> getUserInfo(Context context) {
		SharedPreferences sp = context.getSharedPreferences("data",
				Context.MODE_PRIVATE);
		String userName = sp.getString("userName", null);
		String pwd = sp.getString("passWord", null);
		Map<String, String> userMap = new HashMap<String, String>();
		userMap.put("userName", userName);
		userMap.put("passWord", pwd);
		return userMap;
	}
	
	 public static String postFileUp(String requestUrl, String file_name, File file) {
	        String end = "\r\n";
	        String twoHyphens = "--";
	        String boundary = "*****";
	        String newName = file_name;
	        StringBuffer sb = new StringBuffer();
	        try {
	            URL url = new URL(requestUrl);
	            HttpURLConnection con = (HttpURLConnection) url.openConnection();
	            // 允许Input、Output，不使用Cache
	            con.setDoInput(true);
	            con.setDoOutput(true);
	            con.setUseCaches(false);
	            // 设置以POST方式进行传送
	            con.setRequestMethod("POST");
	            // 设置RequestProperty
	            con.setRequestProperty("Connection", "Keep-Alive");
	            con.setRequestProperty("Charset", "UTF-8");
	            con.setRequestProperty("Content-Type",
	                    "multipart/form-data;boundary=" + boundary);
	            // 构造DataOutputStream流
	            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
	            ds.writeBytes(twoHyphens + boundary + end);
	            ds.writeBytes("Content-Disposition: form-data; "
	                    + "name=\"picfile\";filename=\"" + newName + "\"" + end);
	            ds.writeBytes(end);
	            // 构造要上传文件的FileInputStream流
	            FileInputStream fis = new FileInputStream(file);
	            // 设置每次写入1024bytes
	            int bufferSize = 1024;
	            byte[] buffer = new byte[bufferSize];
	            int length = -1;
	            // 从文件读取数据至缓冲区
	            while ((length = fis.read(buffer)) != -1) {
	                // 将资料写入DataOutputStream中
	                ds.write(buffer, 0, length);
	            }


	            ds.writeBytes(end);
	            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	            // 关闭流
	            fis.close();
	            ds.flush();
	            // 获取响应流
	            InputStream is = con.getInputStream();

	            BufferedReader in = new BufferedReader(new InputStreamReader(is));
	            //StringBuffer stringBuffer = new StringBuffer();
	            String line = "";
	            while ((line = in.readLine()) != null) {
	                sb.append(line);
	            }

//	            int ch;
//	            while ((ch = is.read()) != -1) {
//	                sb.append((char) ch);
//	            }
	            // 关闭DataOutputStream
	            ds.close();
	            return sb.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	    
	        
	        return sb.toString();
	    }
	
	 
	
		    private static final String TAG = "uploadFile";
		    private static final int TIME_OUT = 10 * 1000;//超时时间
		    private static final String CHARSET = "utf-8";//设置编码
		 
		    /**
		     * android上传文件到服务器
		     *
		     * @param file       需要上传的文件
		     * @param RequestURL  请求的url
		     * @return 返回响应的内容
		     */
		    public static String uploadImage(File file, String RequestURL) {
		       String result = "error";
		       String BOUNDARY = UUID.randomUUID().toString();//边界标识 随机生成
		        String PREFIX = "--", LINE_END = "\r\n";
		       String CONTENT_TYPE = "multipart/form-data";//内容类型
		        try {
		           URL url = new URL(RequestURL);
		           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		           conn.setReadTimeout(TIME_OUT);
		           conn.setConnectTimeout(TIME_OUT);
		           conn.setDoInput(true);//允许输入流
		            conn.setDoOutput(true);//允许输出流
		            conn.setUseCaches(false);//不允许使用缓存
		            conn.setRequestMethod("POST");//请求方式
		            conn.setRequestProperty("Charset", CHARSET);//设置编码
		            conn.setRequestProperty("connection", "keep-alive");
		           conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
		           conn.connect();
		 
		            if (file != null) {
		                //当文件不为空，把文件包装并且上传
		                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		                /*StringBuilder sb = new StringBuilder();
		                sb.append(PREFIX);
		                sb.append(BOUNDARY);
		                sb.append(LINE_END);
		                *//*
		                 * 这里重点注意：
		                 * name里面的值为服务器端需要key,只有这个key才可以得到对应的文件
		                 * filename是文件的名字，包含后缀名的。比如:abc.png 
		                 *//*
		                sb.append("Content-Disposition: form-data; name=\"inputName\"; filename=\"" + file.getName() + "\"" + LINE_END);
		                //sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
		                //sb.append("Content-Type: " + getMIMEType(file) + LINE_END);
		                sb.append(LINE_END);
		                dos.write(sb.toString().getBytes());*///此写法会导致无法上传
		                  dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
		                dos.writeBytes("Content-Disposition: form-data; " + "name=\"inputName\";filename=\"" + file.getName() + "\"" + LINE_END);
		                dos.writeBytes(LINE_END);
		 
		                FileInputStream is = new FileInputStream(file);
		                byte[] bytes = new byte[1024];
		                int len = -1;
		                while ((len = is.read(bytes)) != -1) {
		                    dos.write(bytes, 0, len);
		                }
		                is.close();
		                dos.write(LINE_END.getBytes());
		 
		                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
		                dos.write(end_data);
		                dos.flush();
		                /*
		                 * 获取响应码  200=成功
		                 * 当响应成功，获取响应的流  
		                 */
		                int res = conn.getResponseCode();
		                if (res == 200) {
		                    InputStream input = conn.getInputStream();
		                    StringBuilder sbs = new StringBuilder();
		                    int ss;
		                    while ((ss = input.read()) != -1) {
		                        sbs.append((char) ss);
		                    }
		                    result = sbs.toString();
		                    Log.i(TAG, "result------------------>>" + result);
		                }
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return result;
		    }
		 
		    private static String getMIMEType(File file) {
		        String fileName = file.getName();
		        if (fileName.endsWith("png") || fileName.endsWith("PNG")) {
		            return "image/png";
		        } else {
		            return "image/jpg";
		        }
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

	  
}
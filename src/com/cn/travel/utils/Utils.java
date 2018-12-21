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
	            // ����Input��Output����ʹ��Cache
	            con.setDoInput(true);
	            con.setDoOutput(true);
	            con.setUseCaches(false);
	            // ������POST��ʽ���д���
	            con.setRequestMethod("POST");
	            // ����RequestProperty
	            con.setRequestProperty("Connection", "Keep-Alive");
	            con.setRequestProperty("Charset", "UTF-8");
	            con.setRequestProperty("Content-Type",
	                    "multipart/form-data;boundary=" + boundary);
	            // ����DataOutputStream��
	            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
	            ds.writeBytes(twoHyphens + boundary + end);
	            ds.writeBytes("Content-Disposition: form-data; "
	                    + "name=\"picfile\";filename=\"" + newName + "\"" + end);
	            ds.writeBytes(end);
	            // ����Ҫ�ϴ��ļ���FileInputStream��
	            FileInputStream fis = new FileInputStream(file);
	            // ����ÿ��д��1024bytes
	            int bufferSize = 1024;
	            byte[] buffer = new byte[bufferSize];
	            int length = -1;
	            // ���ļ���ȡ������������
	            while ((length = fis.read(buffer)) != -1) {
	                // ������д��DataOutputStream��
	                ds.write(buffer, 0, length);
	            }


	            ds.writeBytes(end);
	            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	            // �ر���
	            fis.close();
	            ds.flush();
	            // ��ȡ��Ӧ��
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
	            // �ر�DataOutputStream
	            ds.close();
	            return sb.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	    
	        
	        return sb.toString();
	    }
	
	 
	
		    private static final String TAG = "uploadFile";
		    private static final int TIME_OUT = 10 * 1000;//��ʱʱ��
		    private static final String CHARSET = "utf-8";//���ñ���
		 
		    /**
		     * android�ϴ��ļ���������
		     *
		     * @param file       ��Ҫ�ϴ����ļ�
		     * @param RequestURL  �����url
		     * @return ������Ӧ������
		     */
		    public static String uploadImage(File file, String RequestURL) {
		       String result = "error";
		       String BOUNDARY = UUID.randomUUID().toString();//�߽��ʶ �������
		        String PREFIX = "--", LINE_END = "\r\n";
		       String CONTENT_TYPE = "multipart/form-data";//��������
		        try {
		           URL url = new URL(RequestURL);
		           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		           conn.setReadTimeout(TIME_OUT);
		           conn.setConnectTimeout(TIME_OUT);
		           conn.setDoInput(true);//����������
		            conn.setDoOutput(true);//���������
		            conn.setUseCaches(false);//������ʹ�û���
		            conn.setRequestMethod("POST");//����ʽ
		            conn.setRequestProperty("Charset", CHARSET);//���ñ���
		            conn.setRequestProperty("connection", "keep-alive");
		           conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
		           conn.connect();
		 
		            if (file != null) {
		                //���ļ���Ϊ�գ����ļ���װ�����ϴ�
		                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		                /*StringBuilder sb = new StringBuilder();
		                sb.append(PREFIX);
		                sb.append(BOUNDARY);
		                sb.append(LINE_END);
		                *//*
		                 * �����ص�ע�⣺
		                 * name�����ֵΪ����������Ҫkey,ֻ�����key�ſ��Եõ���Ӧ���ļ�
		                 * filename���ļ������֣�������׺���ġ�����:abc.png 
		                 *//*
		                sb.append("Content-Disposition: form-data; name=\"inputName\"; filename=\"" + file.getName() + "\"" + LINE_END);
		                //sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
		                //sb.append("Content-Type: " + getMIMEType(file) + LINE_END);
		                sb.append(LINE_END);
		                dos.write(sb.toString().getBytes());*///��д���ᵼ���޷��ϴ�
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
		                 * ��ȡ��Ӧ��  200=�ɹ�
		                 * ����Ӧ�ɹ�����ȡ��Ӧ����  
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
		            String BOUNDARY = "---------7d4a6d158c9"; // �������ݷָ���

		            URL url = new URL(post_url);
		            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		            // ����POST�������������������
		            conn.setDoOutput(true);
		            conn.setDoInput(true);
		            conn.setUseCaches(false);
		            conn.setRequestMethod("POST");
		            //���ӷ��������Ƿ�ر����ӣ������ӣ�
		            //conn.setRequestProperty("connection", "Keep-Alive");
		            //���������
		            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		            conn.setRequestProperty("Charsert", "UTF-8");
		            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		            //conn.setRequestProperty("richmj_token", DateUtil.getToken(context));

		            OutputStream out = new DataOutputStream(conn.getOutputStream());
		            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// ����������ݷָ���
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
		                out.write("\r\n".getBytes()); //����ļ�ʱ�������ļ�֮��������
		                in.close();
		            }
		            out.write(end_data);
		            out.flush();
		            out.close();

		            Log.e("�ϴ��ļ�����===��","�ϴ��ļ�������Ϣ");
		            // ����BufferedReader����������ȡURL����Ӧ
		            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                stringBuffer.append(line);
		            }

		        } catch (Exception e) {
		            System.out.println("����POST��������쳣��" + e);
		            e.printStackTrace();
		        }

		        return stringBuffer.toString();
		    }

	  
}
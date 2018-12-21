package com.cn.travel.domain;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class MyActivityManager{

    public static List<Activity>  mActivities = new ArrayList<>();

	    public static void addActivity(Activity act){ //��activity��ʵ������list�н���ά��
	
	    	mActivities.add(act);
	
	    }

	   public static void removeActivity(Activity act){
	
		   mActivities.remove(act);//���Ѿ����ٵ�activity��list���������ʵ�ʵ�activity���������.һ��
	
	   }



	   public static void finishAll(){
		   for(Activity act:mActivities){	
	           if(!act.isFinishing()){//�����ж�act�Ƿ��������٣����û�о͵���finish��������	
	                 act.finish();
            }
		   }
	   }
}
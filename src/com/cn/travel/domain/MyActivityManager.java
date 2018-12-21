package com.cn.travel.domain;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class MyActivityManager{

    public static List<Activity>  mActivities = new ArrayList<>();

	    public static void addActivity(Activity act){ //将activity的实例放入list中进行维护
	
	    	mActivities.add(act);
	
	    }

	   public static void removeActivity(Activity act){
	
		   mActivities.remove(act);//将已经销毁的activity在list中清除，与实际的activity活动个数保持.一致
	
	   }



	   public static void finishAll(){
		   for(Activity act:mActivities){	
	           if(!act.isFinishing()){//这里判断act是否正在销毁，如果没有就调用finish进行销毁	
	                 act.finish();
            }
		   }
	   }
}
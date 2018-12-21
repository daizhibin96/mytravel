package com.cn.travel.activity.navigation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientationListener implements SensorEventListener{

	private SensorManager mSensorManager;
	private Context context;
	private Sensor mSensor;
	private float lastX;
	
	public MyOrientationListener(Context context) {
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	public void start() {
		//���ϵͳ����
		mSensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		if(mSensorManager!=null){
			//��÷��򴫸���
			mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}
		if(mSensor!=null){
			mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_UI);//����������Ϊ����
		}
	}

	public void stop() {
		//ֹͣ��λ
		mSensorManager.unregisterListener(this);
	}

	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
			float x=event.values[SensorManager.DATA_X];
			//����仯����һ��
			if(Math.abs(x-lastX)>1.0){
				//֪ͨ��������лص�
				if(mOnOrientationListener!=null){
					mOnOrientationListener.onOrientationChanged(x);
				}
			}
			lastX=x;
		}

	}

	private OnOrientationListener mOnOrientationListener;
	
	public void setmOnOrientationListener(
			OnOrientationListener mOnOrientationListener) {
		this.mOnOrientationListener = mOnOrientationListener;
	}
 
	//�ص��ӿ�
	public interface OnOrientationListener{
		void onOrientationChanged(float x);
	}

}

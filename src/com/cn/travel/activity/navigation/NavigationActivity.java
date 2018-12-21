package com.cn.travel.activity.navigation;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cn.travel.activity.navigation.MyOrientationListener.OnOrientationListener;
import com.cn.travel.util.overlayutil.BusLineOverlay;
import com.cn.travel.util.overlayutil.PoiOverlay;
import com.cn.travel.util.overlayutil.TransitRouteOverlay;
import com.cn.travel.util.overlayutil.WalkingRouteOverlay;
import com.cn.travle.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NavigationActivity extends Activity {

	public LocationClient mLocationClient;
	public BDLocationListener myListener = new MyLocationListener();
	private BaiduMap mBaiduMap;
	private MapView mapview;
	private LatLng latLng;
	private LatLng markerllg;
	private double latitude,longitude;
	private boolean isFirstLoc = true;
	private MyOrientationListener mOrientationListener;
	private float mCurrentX;
	private BitmapDescriptor mIconLocation;
	private LocationMode mLocationMode;
	private ImageView imageview;
	private Button walkBtn;
	private EditText cityET;
	private EditText keyET;
	private Button searchBtn;
	private Button nearbySearch;
	private Button nearbySpot;
	private Button busSearch;

	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //ȥ��Activity�����״̬��
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_navigation);

        //·�߼���
        final RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener(){
			@Override
			public void onGetBikingRouteResult(BikingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetIndoorRouteResult(IndoorRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetMassTransitRouteResult(MassTransitRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
				if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
					MyTransitOverlay transitOverlay = new MyTransitOverlay(mBaiduMap);
                    transitOverlay.setData(transitRouteResult.getRouteLines().get(0));// ����һ��·�߷���
                    transitOverlay.addToMap();
                    transitOverlay.zoomToSpan();
                    mBaiduMap.setOnMarkerClickListener(transitOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "�Ѳ������õĹ���·��", Toast.LENGTH_SHORT).show();
                }
			}

			//����·�ߵ���
			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
				if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
					WalkingRouteOverlay walkingOverlay = new WalkingRouteOverlay(mBaiduMap);
                    walkingOverlay.setData(walkingRouteResult.getRouteLines().get(0));// ����һ��·�߷���
                    walkingOverlay.addToMap();
                    walkingOverlay.zoomToSpan();
                    mBaiduMap.setOnMarkerClickListener(walkingOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "�Ѳ�����", Toast.LENGTH_SHORT).show();
                }
			}
        	
        });
        initView();
        initMap();
        
        //�ص��ҵ�λ��
        imageview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tomylocation();
			}
        	
        });
        
        //��ͼ����¼�
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
			
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
				String MapPoiName = arg0.getName();
				markerllg = arg0.getPosition();
				setMarker();
				Toast ts = Toast.makeText(NavigationActivity.this, MapPoiName, Toast.LENGTH_SHORT);
				ts.show();
				return false;
			}
			
			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
			}
		});
        
        //���е�����ť����¼�
        walkBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng startPoint = new LatLng(latitude,longitude);
				LatLng endPoint = new LatLng(markerllg.latitude,markerllg.longitude);
				WalkingRoutePlanOption walkingSearch = new WalkingRoutePlanOption();
				PlanNode fromNode =  PlanNode.withLocation(startPoint);
				PlanNode endNode = PlanNode.withLocation(endPoint);
				walkingSearch.from(fromNode).to(endNode);
				routePlanSearch.walkingSearch(walkingSearch);
			}
        	
        });
    
        //��������
        final PoiSearch   search = PoiSearch.newInstance();
        OnGetPoiSearchResultListener resultListener = new OnGetPoiSearchResultListener(){

			@Override
			public void onGetPoiDetailResult(PoiDetailResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailResult) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetPoiResult(PoiResult poiResult) {
				// TODO Auto-generated method stub
				//����������Ľ����Ϊ�գ�����û�д���
	            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
	                MyOverLay overlay = new MyOverLay(mBaiduMap, search);//�⴫��search������Ϊһ���������󣬵��ʱ���㷢����ϸ����
	                //��������,����ֻ��Ҫһ����
	                overlay.setData(poiResult);
	                //��ӵ���ͼ
	                overlay.addToMap();
	                //����ʾ��ͼ�������ÿ��Կ�������POI��Ȥ������ŵȼ�
	                overlay.zoomToSpan();//���㹤��
	                //���ñ����ĵ�������¼�
	                mBaiduMap.setOnMarkerClickListener(overlay);
	            } else {
	                Toast.makeText(getApplication(), "������������Ҫ����Ϣ��", Toast.LENGTH_SHORT).show();
	            }
			}
        	
        };
        search.setOnGetPoiSearchResultListener(resultListener);
        
        //��������¼�
        searchBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
				String city = cityET.getText().toString();
				String key = keyET.getText().toString();
				Toast ts = Toast.makeText(NavigationActivity.this, city+" "+key, Toast.LENGTH_SHORT);
				ts.show();
				PoiCitySearchOption poiCity = new PoiCitySearchOption();
				poiCity.keyword(key).city(city);//���ﻹ��������ʾ�ĸ�����Ĭ����ʾ10��
			    search.searchInCity(poiCity);//ִ������������������������������������ķ����ᱻ�ص�
			}
        	
        });
        //�ܱ���������¼�
        nearbySearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng point = new LatLng(latitude, longitude);
				String key = keyET.getText().toString();
				PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
		        nearbySearchOption.location(point);
		        nearbySearchOption.keyword(key);
		        nearbySearchOption.radius(1000);// �����뾶����λ����
		        nearbySearchOption.pageNum(0);//����һҳ
		        search.searchNearby(nearbySearchOption);// ���𸽽���������
			}
        	
        });
        
        //�ܱ߾������¼�
        nearbySpot.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng point = new LatLng(latitude, longitude);
				PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
		        nearbySearchOption.location(point);
		        nearbySearchOption.keyword("����");
		        nearbySearchOption.radius(1000);// �����뾶����λ����
		        nearbySearchOption.pageNum(0);
		        nearbySearchOption.pageCapacity(10);//����һҳ
		        search.searchNearby(nearbySearchOption);// ���𸽽���������
			}
        
        });
    
        //�����������
        busSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng startPoint = new LatLng(latitude,longitude);
				LatLng endPoint = new LatLng(markerllg.latitude,markerllg.longitude);
				TransitRoutePlanOption busSearch = new TransitRoutePlanOption();
				PlanNode fromNode =  PlanNode.withLocation(startPoint);
				PlanNode endNode = PlanNode.withLocation(endPoint);
				busSearch.city("��ݸ").from(fromNode).to(endNode);
				routePlanSearch.transitSearch(busSearch);
			}
        
        });
    }

    public void initView(){
    	mapview = (MapView)this.findViewById(R.id.bmapView);
    	imageview = (ImageView)this.findViewById(R.id.map_currentLocation);
    	walkBtn = (Button)this.findViewById(R.id.map_walkingButton);
    	cityET = (EditText)this.findViewById(R.id.map_city);
    	keyET = (EditText)this.findViewById(R.id.map_keyword);
    	searchBtn = (Button)this.findViewById(R.id.map_search);
    	nearbySearch = (Button)this.findViewById(R.id.map_nearbySearch);
    	nearbySpot = (Button)this.findViewById(R.id.map_nearbyspot);
    	busSearch = (Button)this.findViewById(R.id.map_busSearch);
    }
    
    @SuppressWarnings("deprecation")
	public void initMap(){
    	//��ȡ��ͼ�ؼ�����
        mBaiduMap = mapview.getMap();
        //Ĭ����ʾ��ͨ��ͼ
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //������ͨͼ
        //mBaiduMap.setTrafficEnabled(true);
        //��������ͼ
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        // ������λͼ��
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
        //���ö�λSDK����
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //ע���������
        //������λ
        mLocationClient.start();
        //ͼƬ����¼����ص���λ��
        mLocationClient.requestLocation();
    }
    
    public void initLocation(){
    	mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
    	LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
        int span = 1000;
        option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // ��gps

        //��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ
        mLocationClient.setLocOption(option);
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.map_arrow);
        
		mOrientationListener = new MyOrientationListener(this);
 
		mOrientationListener
				.setmOnOrientationListener(new OnOrientationListener() {
 
					@Override
					public void onOrientationChanged(float x) {
						mCurrentX = x;
					}
				});

    }
    
    //���˶�λ
    public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // ���춨λ����
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
                    .direction(mCurrentX).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            	// ���ö�λ����
            	mBaiduMap.setMyLocationData(locData);
            	latitude = location.getLatitude();
                longitude = location.getLongitude();
            if(isFirstLoc){
                isFirstLoc = false;
                LatLng lagl = new LatLng(latitude,longitude);
        		MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(lagl).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS��λ���
                    Toast.makeText(NavigationActivity.this, location.getAddrStr()+" gps", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // ���綨λ���
                    Toast.makeText(NavigationActivity.this, location.getAddrStr()+" net", Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // ���߶�λ���
                    Toast.makeText(NavigationActivity.this, location.getAddrStr()+" line", Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(NavigationActivity.this, "��������������", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(NavigationActivity.this, "�����������", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(NavigationActivity.this, "�ֻ�ģʽ���������Ƿ����", Toast.LENGTH_SHORT).show();
                }
           }
            MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);
			
		}
    	
    }
    
    protected void onStart(){
    	mBaiduMap.setMyLocationEnabled(true);
    	if(!mLocationClient.isStarted()){
    		mLocationClient.start();
    		mOrientationListener.start();
    	}
    	super.onStart();
    }
    protected void onStop(){
    	mBaiduMap.setMyLocationEnabled(false);
    	mLocationClient.stop();
    	mOrientationListener.stop();
    	super.onStop();
    }
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        mapview.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mapview.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        mapview.onPause();  
        } 
    
    //�ص��ҵ�λ��
    public void tomylocation(){
    	LatLng lagl = new LatLng(latitude,longitude);
		MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(lagl);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    
    //���ñ�־��
    public void setMarker(){
    	Marker marker;
    	BitmapDescriptor markermap = BitmapDescriptorFactory.fromResource(R.drawable.map_mark1);
    	OverlayOptions option = new MarkerOptions().position(markerllg).icon(markermap);
    	marker = (Marker) (mBaiduMap.addOverlay(option));
    }
    
    //����Ŀ�긲���ﶨ��
    public class MyOverLay extends PoiOverlay {
        // ���캯��
        PoiSearch poiSearch;
        public MyOverLay(BaiduMap baiduMap, PoiSearch poiSearch) {
            super(baiduMap);
            this.poiSearch = poiSearch;
        }
         //�����ﱻ���ʱ
        @Override
        public boolean onPoiClick(int i) {
            //��ȡ����ı���������
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            String poiName=poiInfo.name;
            Toast.makeText(getApplication(), poiName,
                    Toast.LENGTH_LONG).show();
            return true;
        }
    }
    
    //����·�߸�����
    public class MyTransitOverlay extends TransitRouteOverlay{

		public MyTransitOverlay(BaiduMap baiduMap) {
			super(baiduMap);
			// TODO Auto-generated constructor stub
		}
		public boolean onRouteNodeClick(int i) {
			if (mRouteLine.getAllStep() != null
	                && mRouteLine.getAllStep().get(i) != null) {
	            Toast.makeText(NavigationActivity.this, mRouteLine.getAllStep().get(i).getInstructions(), Toast.LENGTH_SHORT).show();
	        }
	        return false;
	    }
    	
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
}

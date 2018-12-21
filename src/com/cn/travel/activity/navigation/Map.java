package com.cn.travel.activity.navigation;

import java.util.ArrayList;

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
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
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
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.cn.travel.activity.navigation.Hotel.MyOverLay;
import com.cn.travel.activity.navigation.Hotel.MyTransitOverlay;
import com.cn.travel.activity.navigation.HotelDetail.MyLocationListener;
import com.cn.travel.activity.navigation.MyOrientationListener.OnOrientationListener;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.util.overlayutil.DrivingRouteOverlay;
import com.cn.travel.util.overlayutil.PoiOverlay;
import com.cn.travel.util.overlayutil.TransitRouteOverlay;
import com.cn.travel.util.overlayutil.WalkingRouteOverlay;
import com.cn.travle.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends Activity {

	private MapView mapView;
	private BaiduMap baiduMap;
	public LocationClient mLocationClient;
	public BDLocationListener myListener = new MyLocationListener();
	private LocationMode mLocationMode;
	private BitmapDescriptor mIconLocation;//����ͼ��
	private MyOrientationListener mOrientationListener;//���򴫸���
	private float mCurrentX;//�ҵĵ�ǰ����
	public LatLng latlng;//�ҵ�λ�þ�γ��
	public boolean isFirstLoc = true;//�Ƿ�Ϊ��һ�ν����ͼ
	private ImageView imageview;
	private ImageView topoint;
	private ArrayList<String> Route;
	private HotelBean hotelbean = new HotelBean();
	LatLng markerllg;
	double la;
	double ln;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_navigation_map);
		Intent intent = getIntent();
		Gson gson = new Gson();
		String lats = intent.getStringExtra("latlng");
		String lngs = intent.getStringExtra("longlng");
		initView();
		initMap();
		initLocation();
		initListener();
		la = gson.fromJson(lats, double.class);
		ln = gson.fromJson(lngs, double.class);
		markerllg = new LatLng(la,ln);
	}

	//��ʼ����ͼ
	public void initView(){
		mapView = (MapView)this.findViewById(R.id.hotel_mapView);
		imageview = (ImageView)this.findViewById(R.id.hotel_myLocation);
		topoint = (ImageView)this.findViewById(R.id.hotel_point);
	}
	//��ʼ����ͼ
	public void initMap(){
    	//��ȡ��ͼ�ؼ�����
        baiduMap = mapView.getMap();
        //Ĭ����ʾ��ͨ��ͼ
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // ������λͼ��
        baiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());//����LocationClient��
        //���ö�λSDK����
        initLocation();
        mLocationClient.registerLocationListener(myListener);//ע���������
        //������λ
        mLocationClient.start();
        //ͼƬ����¼����ص���λ��
        mLocationClient.requestLocation();
    }
	//�ҵ�λ�ó�ʼ��
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
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.map_arrow);//�ҵ�λ��ͼ������
		mOrientationListener = new MyOrientationListener(this);//���򴫸�������
		mOrientationListener.setmOnOrientationListener(new OnOrientationListener() {
					@Override
					public void onOrientationChanged(float x) {
						mCurrentX = x;
					}
				});

    }
	//��ʼ������
	public void initListener(){
		//�ص��ҵ�λ��
		imageview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tomylocation();
			}
			
		});
		topoint.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				gotopoint();
			}
		
		});
		//Ŀ�������
		final PoiSearch poiSearch = PoiSearch.newInstance();
		final OnGetPoiSearchResultListener resultListener = new OnGetPoiSearchResultListener(){

			@Override
			public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
				// TODO Auto-generated method stub
				
				if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {			
					Toast.makeText(Map.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();} 
				else {
					String name = poiDetailResult.name;
					String p = poiDetailResult.telephone;
		            double dis = DistanceUtil.getDistance(latlng, poiDetailResult.location);
		            String distance = Integer.toString((int) dis);
		            LatLng location = poiDetailResult.location;
		            String add = poiDetailResult.address;
		            double o = poiDetailResult.overallRating;
		            double f = poiDetailResult.facilityRating;
		            double h = poiDetailResult.hygieneRating;
		            setDialog(name,p,distance,add,o,f,h,location);
					}
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
	                MyOverLay overlay = new MyOverLay(baiduMap, poiSearch);//�⴫��search������Ϊһ���������󣬵��ʱ���㷢����ϸ����
	                //��������,����ֻ��Ҫһ����
	                overlay.setData(poiResult);
	                Toast.makeText(Map.this, "", Toast.LENGTH_SHORT).show();
	                //��ӵ���ͼ
	                overlay.addToMap();
	                //����ʾ��ͼ�������ÿ��Կ�������POI��Ȥ������ŵȼ�
	                overlay.zoomToSpan();//���㹤��
	                //���ñ����ĵ�������¼�
	                baiduMap.setOnMarkerClickListener(overlay);
	            } else {
	                Toast.makeText(getApplication(), "������������Ҫ����Ϣ��", Toast.LENGTH_SHORT).show();
	            }
			}
			
		};
		poiSearch.setOnGetPoiSearchResultListener(resultListener);
		//��������¼�
        
        //��ͼ���
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
			
			@Override
			public boolean onMapPoiClick(MapPoi poi) {
				// TODO Auto-generated method stub
	            poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.getUid()));
	            return true;
			}
			
			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				baiduMap.clear();
			}
		});
        
	}
	//���˶�λ
	public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			latlng = new LatLng(location.getLatitude(), location.getLongitude());
            // ���춨λ����
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
                    .direction(mCurrentX).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            	// ���ö�λ����
            	baiduMap.setMyLocationData(locData);
            if(isFirstLoc){
                isFirstLoc = false;
                LatLng lagl = new LatLng(latlng.latitude,latlng.longitude);
        		MapStatus.Builder builder = new MapStatus.Builder();
        		setMarker();
                builder.target(markerllg).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            }
            MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode, true, mIconLocation);
			baiduMap.setMyLocationConfigeration(config);
		
		}
	}
	//��������ʱ
	protected void onStart(){
    	baiduMap.setMyLocationEnabled(true);
    	if(!mLocationClient.isStarted()){
    		mLocationClient.start();
    		mOrientationListener.start();
    	}
    	super.onStart();
    }
	//�������ʱ
    protected void onStop(){
    	baiduMap.setMyLocationEnabled(false);
    	mLocationClient.stop();
    	mOrientationListener.stop();
    	super.onStop();
    }
    //�ص��ҵ�λ��
    public void tomylocation(){
    	LatLng lagl = new LatLng(latlng.latitude,latlng.longitude);
		MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(lagl);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    //�ص�Ŀ���λ��
    public void gotopoint(){
    	MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(markerllg);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    //����������
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
            poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poiInfo.uid));
            return true;
        }
    }
    //poi��Ϣdialog
    private void setDialog(String n,String p,String d,String add,double o,double f,double h,final LatLng ll) {
        Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_navigation_dialog, null);
        TextView name = (TextView)root.findViewById(R.id.poi_name);
        TextView phone = (TextView)root.findViewById(R.id.poi_phone);
        TextView distance = (TextView)root.findViewById(R.id.poi_distance);
        TextView address = (TextView)root.findViewById(R.id.poi_address);
        TextView environment = (TextView)root.findViewById(R.id.poi_overall);
        TextView facility = (TextView)root.findViewById(R.id.poi_facility);
        TextView hygiene = (TextView)root.findViewById(R.id.poi_hygiene);
        TextView car = (TextView)root.findViewById(R.id.poi_car);
        TextView bus = (TextView)root.findViewById(R.id.poi_bus);
        TextView walk = (TextView)root.findViewById(R.id.poi_walk);
        name.setText(n);
        phone.setText("�绰����:"+p);
        distance.setText(d+"m ");
        address.setText("| "+add);
        environment.setText("������:"+o);
        facility.setText(" | �Ǽ�����:"+f);
        hygiene.setText(" | ��������:"+h);
        //��ʼ����ͼ
        root.findViewById(R.id.poi_name);
        root.findViewById(R.id.poi_distance);
        root.findViewById(R.id.poi_address);
        root.findViewById(R.id.poi_overall);
        root.findViewById(R.id.poi_facility);
        root.findViewById(R.id.poi_hygiene);
        root.findViewById(R.id.poi_car);
        root.findViewById(R.id.poi_bus);
        root.findViewById(R.id.poi_walk);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
        lp.x = 0; // ��λ��X����
        lp.y = 0; // ��λ��Y����
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // ���
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // ͸����
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
        final RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener(){

			@Override
			public void onGetBikingRouteResult(BikingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
				// TODO Auto-generated method stub
				baiduMap.clear();
				if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
					DrivingRouteOverlay drivingOverlay = new DrivingRouteOverlay(baiduMap);
                    drivingOverlay.setData(drivingRouteResult.getRouteLines().get(0));// ����һ��·�߷���
                    drivingOverlay.addToMap();
                    drivingOverlay.zoomToSpan();
                    baiduMap.setOnMarkerClickListener(drivingOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "�Ѳ�������·��", Toast.LENGTH_SHORT).show();
                }
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
				baiduMap.clear();
				if(transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
					MyTransitOverlay transitOverlay = new MyTransitOverlay(baiduMap);
					transitOverlay.setData(transitRouteResult.getRouteLines().get(0));// ����һ��·�߷���
					String route = null;
					Route = new ArrayList<String>();
					for(int i=0;i<transitRouteResult.getRouteLines().get(0).getAllStep().size();i++){
						route = transitRouteResult.getRouteLines().get(0).getAllStep().get(i).getInstructions();
						Route.add(route+"\n");
					}
					RouteDialog(Route.toString().substring(1,Route.toString().length()-1));
                    transitOverlay.addToMap();
                    transitOverlay.zoomToSpan();
                    baiduMap.setOnMarkerClickListener(transitOverlay);
                } else {
                	 Toast.makeText(getBaseContext(), "�Ѳ�������·��", Toast.LENGTH_SHORT).show();
                }
			}

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
				// TODO Auto-generated method stub
				baiduMap.clear();
				if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
					WalkingRouteOverlay walkingOverlay = new WalkingRouteOverlay(baiduMap);
                    walkingOverlay.setData(walkingRouteResult.getRouteLines().get(0));// ����һ��·�߷���
                    walkingOverlay.addToMap();
                    walkingOverlay.zoomToSpan();
                    baiduMap.setOnMarkerClickListener(walkingOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "�Ѳ�������·��", Toast.LENGTH_SHORT).show();
                }
			}
        	
        });
        bus.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				baiduMap.clear();
				LatLng startPoint = latlng;
				LatLng endPoint = ll;
				TransitRoutePlanOption busSearch = new TransitRoutePlanOption();
				PlanNode fromNode =  PlanNode.withLocation(startPoint);
				PlanNode endNode = PlanNode.withLocation(endPoint);
				busSearch.city("��ݸ").from(fromNode).to(endNode);
				routePlanSearch.transitSearch(busSearch);
			}
        
        });
        walk.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng startPoint = latlng;
				LatLng endPoint = ll;
				WalkingRoutePlanOption walkingSearch = new WalkingRoutePlanOption();
				PlanNode fromNode =  PlanNode.withLocation(startPoint);
				PlanNode endNode = PlanNode.withLocation(endPoint);
				walkingSearch.from(fromNode).to(endNode);
				routePlanSearch.walkingSearch(walkingSearch);
			}
        	
        });
        car.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng startPoint = latlng;
				LatLng endPoint = ll;
				DrivingRoutePlanOption drivingSearch = new DrivingRoutePlanOption();
				PlanNode fromNode =  PlanNode.withLocation(startPoint);
				PlanNode endNode = PlanNode.withLocation(endPoint);
				drivingSearch.from(fromNode).to(endNode);
				routePlanSearch.drivingSearch(drivingSearch);
			}
        
        });
    }
    //����������
    public class MyTransitOverlay extends TransitRouteOverlay{

		public MyTransitOverlay(BaiduMap baiduMap) {
			super(baiduMap);
			// TODO Auto-generated constructor stub
		}
		public boolean onRouteNodeClick(int i) {
			if (mRouteLine.getAllStep() != null
	                && mRouteLine.getAllStep().get(i) != null) {
	            Toast.makeText(Map.this, mRouteLine.getAllStep().get(i).getInstructions(), Toast.LENGTH_SHORT).show();
	        }
	        return false;
	    }
    	
    } 
    
    private void RouteDialog(String Route){
    	 Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
         RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
                 R.layout.activity_navigation_routedialog, null);
         TextView route = (TextView)root.findViewById(R.id.route);
         route.setText(Route);
         root.findViewById(R.id.route);
         mCameraDialog.setContentView(root);
         Window dialogWindow = mCameraDialog.getWindow();
         dialogWindow.setGravity(Gravity.BOTTOM);
         WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
         lp.x = 0; // ��λ��X����
         lp.y = 0; // ��λ��Y����
         lp.width = (int) getResources().getDisplayMetrics().widthPixels; // ���
         root.measure(0, 0);
         lp.height = root.getMeasuredHeight();
         lp.alpha = 9f; // ͸����
         dialogWindow.setAttributes(lp);
         mCameraDialog.show();
    }
    
    public void setMarker(){
    	Marker marker;
    	//PoiSearch poiSearch = PoiSearch.newInstance();
    	BitmapDescriptor markermap = BitmapDescriptorFactory.fromResource(R.drawable.map_mark1);
    	OverlayOptions option = new MarkerOptions().position(markerllg).icon(markermap);
    	marker = (Marker) (baiduMap.addOverlay(option));
    	//poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(option.getUid()));
    }
    
}

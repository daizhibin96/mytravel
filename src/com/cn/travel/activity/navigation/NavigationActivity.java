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
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_navigation);

        //路线监听
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
                    transitOverlay.setData(transitRouteResult.getRouteLines().get(0));// 设置一条路线方案
                    transitOverlay.addToMap();
                    transitOverlay.zoomToSpan();
                    mBaiduMap.setOnMarkerClickListener(transitOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "搜不到可用的公交路线", Toast.LENGTH_SHORT).show();
                }
			}

			//步行路线导航
			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
				// TODO Auto-generated method stub
				mBaiduMap.clear();
				if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
					WalkingRouteOverlay walkingOverlay = new WalkingRouteOverlay(mBaiduMap);
                    walkingOverlay.setData(walkingRouteResult.getRouteLines().get(0));// 设置一条路线方案
                    walkingOverlay.addToMap();
                    walkingOverlay.zoomToSpan();
                    mBaiduMap.setOnMarkerClickListener(walkingOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "搜不到！", Toast.LENGTH_SHORT).show();
                }
			}
        	
        });
        initView();
        initMap();
        
        //回到我的位置
        imageview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tomylocation();
			}
        	
        });
        
        //地图点击事件
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
        
        //步行导航按钮点击事件
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
    
        //搜索监听
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
				//如果搜索到的结果不为空，并且没有错误
	            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
	                MyOverLay overlay = new MyOverLay(mBaiduMap, search);//这传入search对象，因为一般搜索到后，点击时方便发出详细搜索
	                //设置数据,这里只需要一步，
	                overlay.setData(poiResult);
	                //添加到地图
	                overlay.addToMap();
	                //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
	                overlay.zoomToSpan();//计算工具
	                //设置标记物的点击监听事件
	                mBaiduMap.setOnMarkerClickListener(overlay);
	            } else {
	                Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
	            }
			}
        	
        };
        search.setOnGetPoiSearchResultListener(resultListener);
        
        //搜索点击事件
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
				poiCity.keyword(key).city(city);//这里还能设置显示的个数，默认显示10个
			    search.searchInCity(poiCity);//执行搜索，搜索结束后，在搜索监听对象里面的方法会被回调
			}
        	
        });
        //周边搜索点击事件
        nearbySearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng point = new LatLng(latitude, longitude);
				String key = keyET.getText().toString();
				PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
		        nearbySearchOption.location(point);
		        nearbySearchOption.keyword(key);
		        nearbySearchOption.radius(1000);// 检索半径，单位是米
		        nearbySearchOption.pageNum(0);//搜索一页
		        search.searchNearby(nearbySearchOption);// 发起附近检索请求
			}
        	
        });
        
        //周边景点点击事件
        nearbySpot.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng point = new LatLng(latitude, longitude);
				PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
		        nearbySearchOption.location(point);
		        nearbySearchOption.keyword("景点");
		        nearbySearchOption.radius(1000);// 检索半径，单位是米
		        nearbySearchOption.pageNum(0);
		        nearbySearchOption.pageCapacity(10);//搜索一页
		        search.searchNearby(nearbySearchOption);// 发起附近检索请求
			}
        
        });
    
        //公交搜索点击
        busSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng startPoint = new LatLng(latitude,longitude);
				LatLng endPoint = new LatLng(markerllg.latitude,markerllg.longitude);
				TransitRoutePlanOption busSearch = new TransitRoutePlanOption();
				PlanNode fromNode =  PlanNode.withLocation(startPoint);
				PlanNode endNode = PlanNode.withLocation(endPoint);
				busSearch.city("东莞").from(fromNode).to(endNode);
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
    	//获取地图控件引用
        mBaiduMap = mapview.getMap();
        //默认显示普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        //开启热力图
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
    }
    
    public void initLocation(){
    	mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
    	LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
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
    
    //个人定位
    public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentX).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            	// 设置定位数据
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
                    // GPS定位结果
                    Toast.makeText(NavigationActivity.this, location.getAddrStr()+" gps", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(NavigationActivity.this, location.getAddrStr()+" net", Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(NavigationActivity.this, location.getAddrStr()+" line", Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(NavigationActivity.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(NavigationActivity.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(NavigationActivity.this, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
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
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mapview.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mapview.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mapview.onPause();  
        } 
    
    //回到我的位置
    public void tomylocation(){
    	LatLng lagl = new LatLng(latitude,longitude);
		MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(lagl);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    
    //设置标志点
    public void setMarker(){
    	Marker marker;
    	BitmapDescriptor markermap = BitmapDescriptorFactory.fromResource(R.drawable.map_mark1);
    	OverlayOptions option = new MarkerOptions().position(markerllg).icon(markermap);
    	marker = (Marker) (mBaiduMap.addOverlay(option));
    }
    
    //搜索目标覆盖物定义
    public class MyOverLay extends PoiOverlay {
        // 构造函数
        PoiSearch poiSearch;
        public MyOverLay(BaiduMap baiduMap, PoiSearch poiSearch) {
            super(baiduMap);
            this.poiSearch = poiSearch;
        }
         //覆盖物被点击时
        @Override
        public boolean onPoiClick(int i) {
            //获取点击的标记物的数据
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            String poiName=poiInfo.name;
            Toast.makeText(getApplication(), poiName,
                    Toast.LENGTH_LONG).show();
            return true;
        }
    }
    
    //公交路线覆盖类
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

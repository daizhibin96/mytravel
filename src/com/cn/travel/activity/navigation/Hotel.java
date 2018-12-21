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
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
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
import com.cn.travel.activity.navigation.MyOrientationListener;
import com.cn.travel.activity.navigation.Hotel;
import com.cn.travel.activity.navigation.MyOrientationListener.OnOrientationListener;
import com.cn.travel.bean.HotelBean;
import com.cn.travel.service.me.HotelService;
import com.cn.travel.util.overlayutil.DrivingRouteOverlay;
import com.cn.travel.util.overlayutil.PoiOverlay;
import com.cn.travel.util.overlayutil.TransitRouteOverlay;
import com.cn.travel.util.overlayutil.WalkingRouteOverlay;
import com.cn.travle.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Hotel extends Activity {

	private MapView mapView;
	private BaiduMap baiduMap;
	public LocationClient mLocationClient;
	public BDLocationListener myListener = new MyLocationListener();
	private LocationMode mLocationMode;
	private BitmapDescriptor mIconLocation;//设置图标
	private MyOrientationListener mOrientationListener;//方向传感器
	private float mCurrentX;//我的当前方向
	public LatLng latlng;//我的位置经纬度
	public boolean isFirstLoc = true;//是否为第一次进入地图
	private ImageView imageview;
	private Button search;
	private Button nearby;
	private ArrayList<String> Route;
	private HotelBean hotelbean = new HotelBean();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_navigation_hotel);
		initView();
		initMap();
		initLocation();
		initListener();
		
	}

	//初始化视图
	public void initView(){
		mapView = (MapView)this.findViewById(R.id.hotel_mapView);
		imageview = (ImageView)this.findViewById(R.id.hotel_myLocation);
		search = (Button)this.findViewById(R.id.hotel_search);
		nearby = (Button)this.findViewById(R.id.hotel_nearby);
	}
	//初始化地图
	public void initMap(){
    	//获取地图控件引用
        baiduMap = mapView.getMap();
        //默认显示普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);//注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
    }
	//我的位置初始化
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
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.map_arrow);//我的位置图标设置
		mOrientationListener = new MyOrientationListener(this);//方向传感器监听
		mOrientationListener.setmOnOrientationListener(new OnOrientationListener() {
					@Override
					public void onOrientationChanged(float x) {
						mCurrentX = x;
					}
				});

    }
	//初始化监听
	public void initListener(){
		//回到我的位置
		imageview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tomylocation();
			}
			
		});
		//目标点搜索
		final PoiSearch poiSearch = PoiSearch.newInstance();
		final OnGetPoiSearchResultListener resultListener = new OnGetPoiSearchResultListener(){

			@Override
			public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
				// TODO Auto-generated method stub
				
				if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {			
					Toast.makeText(Hotel.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();} 
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
				//如果搜索到的结果不为空，并且没有错误
	            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
	                MyOverLay overlay = new MyOverLay(baiduMap, poiSearch);//这传入search对象，因为一般搜索到后，点击时方便发出详细搜索
	                //设置数据,这里只需要一步，
	                overlay.setData(poiResult);
	                
	                Toast.makeText(Hotel.this, "", Toast.LENGTH_SHORT).show();
	                //添加到地图
	                overlay.addToMap();
	                //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
	                overlay.zoomToSpan();//计算工具
	                //设置标记物的点击监听事件
	                baiduMap.setOnMarkerClickListener(overlay);
	            } else {
	                Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
	            }
			}
			
		};
		poiSearch.setOnGetPoiSearchResultListener(resultListener);
		//搜索点击事件
        search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				baiduMap.clear();
				PoiCitySearchOption poiCity = new PoiCitySearchOption();
				poiCity.keyword("酒店").city("东莞");//这里还能设置显示的个数，默认显示10个
			    poiSearch.searchInCity(poiCity);//执行搜索，搜索结束后，在搜索监听对象里面的方法会被回调
			}
        	
        });
        //周边搜索
        nearby.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LatLng point = new LatLng(latlng.latitude, latlng.longitude);
				PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
		        nearbySearchOption.location(point);
		        nearbySearchOption.keyword("酒店");
		        nearbySearchOption.radius(5000);// 检索半径，单位是米
		        nearbySearchOption.pageNum(0);//搜索一页
		        poiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求
			}
        	
        });

	
        //地图点击
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
	//个人定位
	public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			latlng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentX).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            	// 设置定位数据
            	baiduMap.setMyLocationData(locData);
            if(isFirstLoc){
                isFirstLoc = false;
                LatLng lagl = new LatLng(latlng.latitude,latlng.longitude);
        		MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(lagl).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(Hotel.this, location.getAddrStr()+" gps", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(Hotel.this, location.getAddrStr()+" net", Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(Hotel.this, location.getAddrStr()+" line", Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(Hotel.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(Hotel.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(Hotel.this, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }
            MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode, true, mIconLocation);
			baiduMap.setMyLocationConfigeration(config);
		
		}
	}
	//程序运行时
	protected void onStart(){
    	baiduMap.setMyLocationEnabled(true);
    	if(!mLocationClient.isStarted()){
    		mLocationClient.start();
    		mOrientationListener.start();
    	}
    	super.onStart();
    }
	//程序结束时
    protected void onStop(){
    	baiduMap.setMyLocationEnabled(false);
    	mLocationClient.stop();
    	mOrientationListener.stop();
    	super.onStop();
    }
    //回到我的位置
    public void tomylocation(){
    	LatLng lagl = new LatLng(latlng.latitude,latlng.longitude);
		MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(lagl);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    //搜索覆盖类
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
            poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poiInfo.uid));
            return true;
        }
    }
    //poi信息dialog
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
        phone.setText("电话号码:"+p);
        distance.setText(d+"m ");
        address.setText("| "+add);
        environment.setText("总评分:"+o);
        facility.setText(" | 星级评分:"+f);
        hygiene.setText(" | 卫生评分:"+h);
        //初始化视图
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
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
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
                    drivingOverlay.setData(drivingRouteResult.getRouteLines().get(0));// 设置一条路线方案
                    drivingOverlay.addToMap();
                    drivingOverlay.zoomToSpan();
                    baiduMap.setOnMarkerClickListener(drivingOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "搜不到可用路线", Toast.LENGTH_SHORT).show();
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
					transitOverlay.setData(transitRouteResult.getRouteLines().get(0));// 设置一条路线方案
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
                	 Toast.makeText(getBaseContext(), "搜不到可用路线", Toast.LENGTH_SHORT).show();
                }
			}

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
				// TODO Auto-generated method stub
				baiduMap.clear();
				if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
					WalkingRouteOverlay walkingOverlay = new WalkingRouteOverlay(baiduMap);
                    walkingOverlay.setData(walkingRouteResult.getRouteLines().get(0));// 设置一条路线方案
                    walkingOverlay.addToMap();
                    walkingOverlay.zoomToSpan();
                    baiduMap.setOnMarkerClickListener(walkingOverlay);
                } else {
                    Toast.makeText(getBaseContext(), "搜不到可用路线", Toast.LENGTH_SHORT).show();
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
				busSearch.city("东莞").from(fromNode).to(endNode);
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
    //公交覆盖类
    public class MyTransitOverlay extends TransitRouteOverlay{

		public MyTransitOverlay(BaiduMap baiduMap) {
			super(baiduMap);
			// TODO Auto-generated constructor stub
		}
		public boolean onRouteNodeClick(int i) {
			if (mRouteLine.getAllStep() != null
	                && mRouteLine.getAllStep().get(i) != null) {
	            Toast.makeText(Hotel.this, mRouteLine.getAllStep().get(i).getInstructions(), Toast.LENGTH_SHORT).show();
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
         WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
         lp.x = 0; // 新位置X坐标
         lp.y = 0; // 新位置Y坐标
         lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
         root.measure(0, 0);
         lp.height = root.getMeasuredHeight();
         lp.alpha = 9f; // 透明度
         dialogWindow.setAttributes(lp);
         mCameraDialog.show();
    }
    
}

package com.cn.overlayutil;


import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.poi.PoiIndoorResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示indoorpoi的overly
 */
public class IndoorPoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;

    private PoiIndoorResult mIndoorPoiResult = null;

    /**
     * 构�?�函�?
     *
     * @param baiduMap
     *            �? IndoorPoiOverlay 引用�? BaiduMap 对象
     */
    public IndoorPoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    /**
     * 设置IndoorPoi数据
     *
     * @param indoorpoiResult
     *            设置indoorpoiResult数据
     */
    public void setData(PoiIndoorResult indoorpoiResult) {
        this.mIndoorPoiResult = indoorpoiResult;
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mIndoorPoiResult == null || mIndoorPoiResult.getmArrayPoiInfo() == null) {
            return null;
        }
        List<OverlayOptions> markerList = new ArrayList<OverlayOptions>();
        int markerSize = 0;
        for (int i = 0; i < mIndoorPoiResult.getmArrayPoiInfo().size()
                && markerSize < MAX_POI_SIZE; i++) {
            if (mIndoorPoiResult.getmArrayPoiInfo().get(i).latLng == null) {
                continue;
            }
            markerSize++;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            markerList.add(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark"
                            + markerSize + ".png")).extraInfo(bundle)
                    .position(mIndoorPoiResult.getmArrayPoiInfo().get(i).latLng));

        }
        return markerList;
    }

    /**
     * 获取�? IndoorPoiOverlay �? indoorpoi数据
     *
     * @return
     */
    public PoiIndoorResult getIndoorPoiResult() {
        return mIndoorPoiResult;
    }

    /**
     * 覆写此方法以改变默认点击行为
     *
     * @param i
     *            被点击的poi�?
     *            {@link com.baidu.mapapi.search.poi.PoiIndoorResult#getmArrayPoiInfo()} } 中的索引
     * @return
     */
    public boolean onPoiClick(int i) {
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }
        if (marker.getExtraInfo() != null) {
            return onPoiClick(marker.getExtraInfo().getInt("index"));
        }
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        // TODO Auto-generated method stub
        return false;
    }
}


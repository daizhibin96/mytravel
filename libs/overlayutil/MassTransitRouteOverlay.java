/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.cn.overlayutil;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.search.route.MassTransitRouteLine;

import android.graphics.Color;
import android.os.Bundle;

public class MassTransitRouteOverlay extends OverlayManager {

    private MassTransitRouteLine mRouteLine;
    private boolean isSameCity;

    /**
     * 构�?�函�?
     *
     * @param baiduMap
     *            该TransitRouteOverlay引用�? BaiduMap 对象
     */
    public MassTransitRouteOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }


    /**
     * 设置路线数据
     *
     * @param routeOverlay
     *            路线数据
     */
    public void setData(MassTransitRouteLine routeOverlay) {
        this.mRouteLine = routeOverlay;
    }

    public void setSameCity( boolean sameCity ) {
        isSameCity = sameCity;
    }
    /**
     * 覆写此方法以改变默认起点图标
     *
     * @return 起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认终点图标
     *
     * @return 终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }

    public int getLineColor() {
        return 0;
    }
    @Override
    public List<OverlayOptions> getOverlayOptions() {
        if (mRouteLine == null) {
            return null;
        }

        List<OverlayOptions> overlayOptionses = new ArrayList<OverlayOptions>();
        List<List<MassTransitRouteLine.TransitStep>> steps = mRouteLine.getNewSteps();
        if (isSameCity ) {
            // 同城 (同城时，每个steps的get(i)对应的List是一条step的不同方案，此处都�?�第�?条进行绘制，即get�?0））

            // step node
            for ( int i = 0; i < steps.size(); i++ ) {

                MassTransitRouteLine.TransitStep step = steps.get(i).get(0);
                Bundle b = new Bundle();
                b.putInt("index", i + 1);

                if (step.getStartLocation() != null) {
                    overlayOptionses.add((new MarkerOptions()).position(step.getStartLocation())
                            .anchor(0.5f, 0.5f).zIndex(10).extraInfo(b).icon(getIconForStep(step)));
                }

                // �?后一个终�?
                if ( (i == steps.size() - 1) &&  (step.getEndLocation() != null)) {
                    overlayOptionses.add((new MarkerOptions()).position(step.getEndLocation())
                            .anchor(0.5f, 0.5f).zIndex(10)
                            .icon(getIconForStep(step))
                    );
                }

            }

            // polyline
            for ( int i = 0; i < steps.size(); i++ ) {
                MassTransitRouteLine.TransitStep step = steps.get(i).get(0);
                int color = 0;
                if (step.getVehileType() != MassTransitRouteLine.TransitStep
                        .StepVehicleInfoType.ESTEP_WALK) {
                    // color = Color.argb(178, 0, 78, 255);
                    color = getLineColor() != 0 ? getLineColor() : Color.argb(178, 0, 78, 255);
                } else {
                    // color = Color.argb(178, 88, 208, 0);
                    color = getLineColor() != 0 ? getLineColor() : Color.argb(178, 88, 208, 0);
                }
                overlayOptionses.add(new PolylineOptions()
                        .points(step.getWayPoints()).width(10).color(color)
                        .zIndex(0));
            }

        } else {
            // 跨城 （跨城时，每个steps的get(i)对应的List是一条step的子路线sub_step，需要将它们全部拼接才是�?条完整路线）
            int stepSum = 0;
            for (int i = 0; i < steps.size(); i++ ) {
                stepSum +=  steps.get(i).size();
            }

            // step node
            int k = 1;
            for ( int i = 0; i < steps.size(); i++ ) {

                for (int j = 0; j < steps.get(i).size(); j++ ) {
                    MassTransitRouteLine.TransitStep step = steps.get(i).get(j);
                    Bundle b = new Bundle();
                    b.putInt("index", k);

                    if (step.getStartLocation() != null) {
                        overlayOptionses.add((new MarkerOptions()).position(step.getStartLocation())
                                .anchor(0.5f, 0.5f).zIndex(10).extraInfo(b).icon(getIconForStep(step)));
                    }

                    // �?后一个终�?
                    if ( (k ==  stepSum ) &&  (step.getEndLocation() != null)) {
                        overlayOptionses.add((new MarkerOptions()).position(step.getEndLocation())
                                .anchor(0.5f, 0.5f).zIndex(10).icon(getIconForStep(step)));
                    }

                    k++;
                }
            }


            // polyline
            for ( int i = 0; i < steps.size(); i++ ) {

                for (int j = 0; j < steps.get(i).size(); j++ ) {
                    MassTransitRouteLine.TransitStep step = steps.get(i).get(j);
                    int color = 0;
                    if (step.getVehileType() != MassTransitRouteLine.TransitStep
                            .StepVehicleInfoType.ESTEP_WALK) {
                        // color = Color.argb(178, 0, 78, 255);
                        color = getLineColor() != 0 ? getLineColor() : Color.argb(178, 0, 78, 255);
                    } else {
                        // color = Color.argb(178, 88, 208, 0);
                        color = getLineColor() != 0 ? getLineColor() : Color.argb(178, 88, 208, 0);
                    }
                    if (step.getWayPoints() != null ) {
                        overlayOptionses.add(new PolylineOptions()
                                            .points(step.getWayPoints()).width(10).color(color)
                                            .zIndex(0));
                    }
                }
            }

        }

        // 起点
        if (mRouteLine.getStarting() != null && mRouteLine.getStarting().getLocation() != null) {
            overlayOptionses.add((new MarkerOptions()).position(mRouteLine.getStarting().getLocation())
                    .icon(getStartMarker() != null
                            ? getStartMarker() : BitmapDescriptorFactory.fromAssetWithDpi("Icon_start.png"))
                    .zIndex(10));
        }
        // 终点
        if (mRouteLine.getTerminal() != null && mRouteLine.getTerminal().getLocation() != null) {
            overlayOptionses
                    .add((new MarkerOptions())
                            .position(mRouteLine.getTerminal().getLocation())
                            .icon(getTerminalMarker() != null ? getTerminalMarker() :
                                    BitmapDescriptorFactory
                                            .fromAssetWithDpi("Icon_end.png"))
                            .zIndex(10));
        }

        return overlayOptionses;

    }

    private BitmapDescriptor getIconForStep(MassTransitRouteLine.TransitStep step) {
        switch (step.getVehileType()) {
            case ESTEP_WALK:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_walk_route.png");
            case ESTEP_TRAIN:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_subway_station.png");
            case ESTEP_DRIVING:
            case ESTEP_COACH:
            case ESTEP_PLANE:
            case ESTEP_BUS:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_bus_station.png");
            default:
                return null;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}

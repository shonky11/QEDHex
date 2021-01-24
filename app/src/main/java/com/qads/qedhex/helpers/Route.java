package com.qads.qedhex.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Route {


    private List<Double> mLocation;
    private Map<String, Object> mResponse;
    private Double mTime_to_walk;
    private Double mWalk_speed;
    private Double mDistance;
    private Double mTime;
    private Map<String, Double> mCenter;
    private Map<String, Object> mPOI;
    private String mStatus;
    private String mName;

    public Route(){
    }

    public Route(List<Double> location, Map<String, Object> response, Double time_to_walk, Double walk_speed){

        mLocation = location;
        mResponse = response;
        mTime_to_walk = time_to_walk;
        mWalk_speed = walk_speed;

    }


    public List<Double> getLocation() {
        return mLocation;
    }

    public Map<String, Object> getResponse() {
        return mResponse;
    }

    public Double getResponseDistance() {
        mDistance = (Double) mResponse.get("actual_distance");
        return mDistance;
    }

    public Double getResponseTime() {
        mTime = (Double) mResponse.get("actual_time");
        return mTime;
    }

    public Map<String, Double> getResponseCenter() {
        mCenter = (Map<String, Double>) mResponse.get("map_center");
        return mCenter;
    }

    public Map<String, Object> getResponsePOI() {
        mPOI = (Map<String, Object>) mResponse.get("place_of_interest");
        return mPOI;
    }

    public String getResponsePOIStatus() {
        mStatus = (String) getResponsePOI().get("business_status");
        return mStatus;
    }

    public String getPOIName() {
        Map<String, Object> mGeom  = (Map<String, Object>) getResponsePOI().get("business_geometry");
        mName = (String) mGeom.get("name");
        return mName;
    }

    public Double getTime_to_walk() {
        return mTime_to_walk;
    }

    public Double getWalk_speed() {
        return mWalk_speed;
    }

    public void setLocation(List<Double> mLocation) {
        this.mLocation = mLocation;
    }

    public void setResponse(Map<String, Object> mResponse) {
        this.mResponse = mResponse;
    }

    public void setTime_to_walk(Double mTime_to_walk) {
        this.mTime_to_walk = mTime_to_walk;
    }

    public void setWalk_speed(Double mWalk_speed) {
        this.mWalk_speed = mWalk_speed;
    }
}

package com.qads.qedhex.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Route {


    private List<Long> mLocation;
    private Map<String, Object> mResponse;
    private Long mTime_to_walk;
    private Long mWalk_speed;
    private Long mDistance;
    private Long mTime;
    private Map<String, Number> mCenter;
    private Map<String, Object> mPOI;
    private String mStatus;
    private String mName;
    private List<Object> mSteps;

    public Route(){
    }

    public Route(List<Long> location, Map<String, Object> response, Long time_to_walk, Long walk_speed){

        mLocation = location;
        mResponse = response;
        mTime_to_walk = time_to_walk;
        mWalk_speed = walk_speed;

    }


    public List<Long> getLocation() {
        return mLocation;
    }

    public Map<String, Object> getResponse() {
        return mResponse;
    }

    public Long getResponseDistance() {
        mDistance = (Long) mResponse.get("actual_distance");
        return mDistance;
    }

    public Long getResponseTime() {
        mTime = (Long) mResponse.get("actual_time");
        return mTime;
    }

    public Map<String, Number> getResponseCenter() {
        mCenter = (Map<String, Number>) mResponse.get("map_center");
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

    public List<Object> getSteps() {


        mSteps = (List<Object>) mResponse.get("steps");
        return mSteps;
    }

    public Long getTime_to_walk() {
        return mTime_to_walk;
    }

    public Long getWalk_speed() {
        return mWalk_speed;
    }

    public void setLocation(List<Long> mLocation) {
        this.mLocation = mLocation;
    }

    public void setResponse(Map<String, Object> mResponse) {
        this.mResponse = mResponse;
    }

    public void setTime_to_walk(Long mTime_to_walk) {
        this.mTime_to_walk = mTime_to_walk;
    }

    public void setWalk_speed(Long mWalk_speed) {
        this.mWalk_speed = mWalk_speed;
    }
}

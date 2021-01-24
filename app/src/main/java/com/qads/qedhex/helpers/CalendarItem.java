package com.qads.qedhex.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalendarItem {

    private String mStartTime;
    private String mEndTime;
    private Map<String, Map<String, Object>> mOptionsTemp;
    public static List<Map<String, Object>> mOptions = new ArrayList<>();
    private List<String> mSize;

    public CalendarItem(){

    }

    public CalendarItem(String starttime, String endtime, Map<String, Map<String, Object>> optionstemp, List<String> size) {
        mStartTime = starttime;
        mEndTime = endtime;
        mOptionsTemp = optionstemp;
        mSize = size;
    }

    private void optionsPopulate(){

    }

    public String getStartTime(){return mStartTime;}
    public String getEndTime(){return mEndTime;}
    public Map<String, Map<String, Object>> getOptions(){return mOptionsTemp;}
    public List<Map<String, Object>> getOptionsList(){
        for(String opt : mOptionsTemp.keySet()){
            mOptions.add(mOptionsTemp.get(opt));
        }
        return (mOptions);
    }
    public List<String> getSize(){return mSize;}

    public void setStartTime(String startTime) {this.mStartTime = startTime;}
    public void setEndTime(String endTime) {this.mEndTime = endTime;}
    public void setOptions(Map<String, Map<String, Object>> optionsTemp) { this.mOptionsTemp = optionsTemp; }
    public void setSize(List<String> size) { this.mSize = size; }
}

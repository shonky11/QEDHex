package com.qads.qedhex.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalendarItem {

    private ArrayList<Map<String, Object>> mTimesList;

    public CalendarItem(){

    }


    public CalendarItem(ArrayList<Map<String, Object>> timesList) {
        mTimesList = timesList;
    }


    public ArrayList<Map<String, Object>> getResponse() {
        return mTimesList;
    }

    public void setResponse(ArrayList<Map<String, Object>> mTimesList) {
        this.mTimesList = mTimesList;
    }

}

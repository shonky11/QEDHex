package com.qads.qedhex.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalendarItem {

    private List<Map<String, Object>> mTimesList;

    public CalendarItem(){

    }


    public CalendarItem(List<Map<String, Object>> timesList) {
        mTimesList = timesList;
    }

    private void optionsPopulate(){

    }

    public List<Map<String, Object>> getResponse() {
        return mTimesList;
    }

    public void setResponse(List<Map<String, Object>> mTimesList) {
        this.mTimesList = mTimesList;
    }

}

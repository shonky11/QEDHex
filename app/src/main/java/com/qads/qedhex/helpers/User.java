package com.qads.qedhex.helpers;

import java.util.ArrayList;

public class User {

    private String mFirstname;
    private String mLastname;
    private String mAge;
    private String mUid;
    private String mInterests;
    private ArrayList<String> mMyInterests;
    private String mAccessToken;

    public User(){

    }

    public User(String firstname, String lastname, String age, String uid, ArrayList<String> interests, String accesstoken) {
        mFirstname = firstname;
        mLastname = lastname;
        mAge = age;
        mUid = uid;
        mMyInterests = interests;
        mAccessToken = accesstoken;
    }

    public String getFirstname() {
        return mFirstname;
    }

    public String getLastname() {
        return mLastname;
    }

    public String getAge() {
        return mAge;
    }

    public String getUid() {
        return mUid;
    }

    public String getInterests() {
        return mInterests;
    }

    public ArrayList<String> getmMyInterests() {
        return mMyInterests;
    }

    public String getAccesstoken(){ return mAccessToken;}

    public void setAccesstoken(String accesstoken){this.mAccessToken = accesstoken;}

    public void setmMyInterests(ArrayList<String> interests) {
        this.mMyInterests = interests;
    }

    public void setFirstname(String firstname) {
        this.mFirstname = firstname;
    }

    public void setLastname(String lastname) {
        this.mLastname = lastname;
    }

    public void setAge(String age) {
        this.mAge = age;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public void setInterests(String interests) {
        this.mInterests = interests;
    }
}

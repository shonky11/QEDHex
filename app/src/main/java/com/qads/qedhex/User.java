package com.qads.qedhex;

public class User {

    private String mName;
    private String mUid;

    public User(){

    }

    public User(String mName, String uid) {
        this.mName = mName;
        mUid = uid;
    }

    public String getmName() {
        return mName;
    }
    public String getmUid(){return mUid;}

    public void setmName(String mName) {
        this.mName = mName;
    }
    public void setUid(String uid) {
        this.mUid = uid;
    }
}

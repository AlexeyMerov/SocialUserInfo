package com.testapp.socialuserinfo.data.repository.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    public static final String ID_KEY = "mId";

    @PrimaryKey
    private long mId;

    private String mAvatar;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPhoneNumber;
    private String mGender;
    private String mBirtday;

    public User() {
    }

    public User(String avatar, String firstName, String lastName, String email, String phoneNumber, String gender, String birtday) {
        mId = System.currentTimeMillis() / 1000;
        mAvatar = avatar;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mGender = gender;
        mBirtday = birtday;
    }

    public long getId() {
        return mId;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getGender() {
        return mGender;
    }

    public String getBirtday() {
        return mBirtday;
    }
}

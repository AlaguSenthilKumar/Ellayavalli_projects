package com.alwar.elayavalli.Others;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RetrieveBean extends RealmObject implements Serializable {

    private String adiyenNameStr, aachariyarNameStr, adiyenAgeStr, nativePlaceStr,
            jobStr, mobileNoStr;


    @PrimaryKey
    public String profileId;

    public String getAdiyenNameStr() {
        return adiyenNameStr;
    }

    public void setAdiyenNameStr(String adiyenNameStr) {
        this.adiyenNameStr = adiyenNameStr;
    }

    public String getAachariyarNameStr() {
        return aachariyarNameStr;
    }

    public void setAachariyarNameStr(String aachariyarNameStr) {
        this.aachariyarNameStr = aachariyarNameStr;
    }

    public String getAdiyenAgeStr() {
        return adiyenAgeStr;
    }

    public void setAdiyenAgeStr(String adiyenAgeStr) {
        this.adiyenAgeStr = adiyenAgeStr;
    }

    public String getNativePlaceStr() {
        return nativePlaceStr;
    }

    public void setNativePlaceStr(String nativePlaceStr) {
        this.nativePlaceStr = nativePlaceStr;
    }

    public String getJobStr() {
        return jobStr;
    }

    public void setJobStr(String jobStr) {
        this.jobStr = jobStr;
    }

    public String getMobileNoStr() {
        return mobileNoStr;
    }

    public void setMobileNoStr(String mobileNoStr) {
        this.mobileNoStr = mobileNoStr;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}

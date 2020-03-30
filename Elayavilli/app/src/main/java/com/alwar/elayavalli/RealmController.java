package com.alwar.elayavalli;

import com.alwar.elayavalli.Others.RetrieveBean;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class  RealmController {

    private Realm realm;
    private static RealmController instance;

    public RealmController() {
            realm = Realm.getDefaultInstance();
    }

    public static RealmController with() {

        if (instance == null)
            instance = new RealmController();
        return instance;
    }

    public String insertProfileDetail(RetrieveBean retrieveBean) {

        String profileId = null;

        int totalData = realm.where(RetrieveBean.class).findAll().size();

//        if (totalData != 0) {
//            RetrieveBean retrieveBeanModel = realm.where(RetrieveBean.class).sort(Constants.PROFILE_ID, Sort.DESCENDING).findFirst();
//            if (retrieveBeanModel != null) {
//                profileId = retrieveBeanModel.profileId;
//            }
//        }

//        if (profileId != null) {
//            profileId = retrieveBean.getAdiyenDOBStr().replaceAll("/", "-") + "_00"+ (lastValue + 1);
//            retrieveBean.profileId = profileId;
//        } else {
          //  profileId = retrieveBean.getAdiyenDOBStr().replaceAll("/", "-")  + "_00"+ (totalData + 1);
            retrieveBean.profileId = profileId;
//        }

        realm.beginTransaction();
        realm.insert(retrieveBean);
        realm.commitTransaction();
        return profileId;
    }

    public void insertSyncDetail(RetrieveBean retrieveBean) {
        realm.beginTransaction();
        realm.insert(retrieveBean);
        realm.commitTransaction();
    }

    public List<RetrieveBean> getAllProfile() {
        RealmResults<RetrieveBean> profileModels = realm.where(RetrieveBean.class).findAll();

        if (profileModels != null) {
            return realm.copyFromRealm(profileModels);
        } else {
            return new ArrayList<>();
        }
    }

    public RetrieveBean getAllProfileById(String profileId) {
        RetrieveBean retrieveBean = realm.where(RetrieveBean.class).equalTo(Constants.PROFILE_ID, profileId).findFirst();
        if (retrieveBean != null)
            return  realm.copyFromRealm(retrieveBean);
        else
            return null;

    }

    //Match Pictures

    public void insertUpdateProfilePictures(String profileId, String picturePath) {
        RetrieveBean retrieveBean = realm.where(RetrieveBean.class).equalTo(Constants.PROFILE_ID, profileId).findFirst();
        realm.beginTransaction();
        if (retrieveBean != null) {
         //   retrieveBean.setImagePath(picturePath);
            realm.insertOrUpdate(retrieveBean);
        }
        realm.commitTransaction();
    }
}

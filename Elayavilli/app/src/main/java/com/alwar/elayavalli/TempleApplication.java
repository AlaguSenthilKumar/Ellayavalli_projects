package com.alwar.elayavalli;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TempleApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("temple.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.getInstance(config);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}

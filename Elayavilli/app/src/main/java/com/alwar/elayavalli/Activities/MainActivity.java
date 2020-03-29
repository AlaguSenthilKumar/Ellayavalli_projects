package com.alwar.elayavalli.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alwar.elayavalli.FireBaseController;
import com.alwar.elayavalli.Others.RetrieveBean;
import com.alwar.elayavalli.Others.Utility;
import com.alwar.elayavalli.R;
import com.alwar.elayavalli.RealmController;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   // FireBaseController fireBaseController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.toolbar_text);
            TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
            textView.setText(R.string.app_name);
        }

        //fireBaseController = new FireBaseController();
        //fireBaseController.setFireBaseReference();

       // if (Utility.checkPermission(this))
          //fireBaseController.syncUserDetails();

        //To Add Realm Data into Firebase without image.
        /*RealmController reallmController = new RealmController();
        List<RetrieveBean> allData =  reallmController.getAllProfile();
        for (RetrieveBean retrieveBean : allData) {
            fireBaseController.addNewData(retrieveBean.profileId, retrieveBean);
        }
*/

        Button btnRegister = findViewById(R.id.btn_register);
        Button btnList = findViewById(R.id.btn_list);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //fireBaseController.syncUserDetails();
                    break;
                } else {
                    finish();
                }
        }
    }
}

package com.alwar.elayavalli.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwar.elayavalli.CustomViews.HomeRoundedLayout;
import com.alwar.elayavalli.Others.AppCommUtils;
import com.alwar.elayavalli.R;
import com.balysv.materialripple.MaterialRippleLayout;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "HomeActivity::";
    HomeRoundedLayout img1, img2;
    int ALL_PERMISSIONS = 101;
    final String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CONTACTS, Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        if (isPermissionGranted()) {
            initUI();
        } /*else {
            AppCommUtils.showAlertDialog(this, "Please Enable All Permissions after you have to use the all functionality, All Data Will be Store in Your Local So Don't Hesitate");
        }*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.toolbar_text);
            TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
            textView.setText(R.string.app_name);
        }

    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                   ) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);
            }
        } else {
            Log.v(TAG, "Permission is granted");
            return true;
        }
        return false;
    }


    private void initUI () {
        img1 = findViewById(R.id.imageButton1);
        img1.setTopLeftCornerRadius(R.dimen.Layout_margin);
        img1.setBottomLeftCornerRadius(R.dimen.Component_margin);


        img2 = findViewById(R.id.imageButton2);
        img2.setTopRightCornerRadius(R.dimen.Layout_margin);
        img2.setBottomRightCornerRadius(R.dimen.Component_margin);

        TextView textView = img1.findViewById(R.id.tv_name);
        ImageView imageView = img1.findViewById(R.id.iv_home_option);
        textView.setText(getString(R.string.demo_name));
        imageView.setImageResource(R.drawable.ic_home_profile_man);


        textView = img2.findViewById(R.id.tv_name);
        imageView = img2.findViewById(R.id.iv_home_option);
        textView.setText(getString(R.string.about));
        imageView.setImageResource(R.drawable.ic_home_sample);

        img1.setOnClickListener(this);


        HomeRoundedLayout[] list_img_buttons={img1,img2};
        for (HomeRoundedLayout list_button : list_img_buttons)
        {
            MaterialRippleLayout.on(list_button)
                    .rippleColor(R.color.colorPrimary)
                    .rippleAlpha(0.2f)
                    .rippleHover(true)
                    .create();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
                boolean isPerpermissionForAllGranted = false;

                if (grantResults.length > 0 && permissions.length == grantResults.length) {
                    isPerpermissionForAllGranted = true;
                }

                if (isPerpermissionForAllGranted) {
                    initUI();
                } else {
                    AppCommUtils.showAlertDialog(this, "Please Enable All Permissions after you have to use the all functionality, All Data Will be Store in Your Local So Don't Hesitate");
                }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageButton1:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
        }
    }
}

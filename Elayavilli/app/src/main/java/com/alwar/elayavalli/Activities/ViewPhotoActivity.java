package com.alwar.elayavalli.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwar.elayavalli.Constants;
import com.alwar.elayavalli.Others.RetrieveBean;
import com.alwar.elayavalli.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ViewPhotoActivity extends AppCompatActivity {

    RetrieveBean retrieveBean;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        retrieveBean = (RetrieveBean) getIntent().getSerializableExtra(Constants.Profile);
        type = getIntent().getStringExtra(Constants.TYPE);

        if (retrieveBean != null) {

            if (type.equals(Constants.PHOTO)) {

                setContentView(R.layout.view_photo);

                ImageView imageView = findViewById(R.id.iv_profile);

//                Picasso.with(this).load(new File(retrieveBean.getImagePath())).into(imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d("ImageViewAdapter::", "onSuccess: ");
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.d("ImageViewAdapter::", "onError: ");
//                    }
//                });
            } else {
                setContentView(R.layout.view_address);

                TextView tvTempAddress = findViewById(R.id.tv_temp_address);
                TextView lbTempAddress = findViewById(R.id.lb_temp_addr);
                TextView tvPerAddress = findViewById(R.id.tv_per_address);

                String address = null;

                // address = retrieveBean.getAddress();

                try {
                    JSONArray jsonArray = new JSONArray(address);

                    JSONObject tempObject = jsonArray.optJSONObject(0);
                    JSONObject perObject = jsonArray.optJSONObject(1);

                    String tempAddr = tempObject.optString("doorNo") + ", " + tempObject.optString("streetName") + ",\n" + tempObject.optString("place") + ",\n" +
                            tempObject.optString("district") + ",\n" + tempObject.optString("state");

                    String perAddr = perObject.optString("doorNo") + ", " + perObject.optString("streetName") + ",\n" + perObject.optString("place") + ",\n" +
                            perObject.optString("district") + ",\n" + perObject.optString("state");

                    if ("".equals(tempObject.optString("doorNo")) && "".equals(tempObject.optString("streetName")) && "".equals(tempObject.optString("place")) && "".equals(tempObject.optString("district"))&& "".equals(tempObject.optString("state"))) {
                        tvTempAddress.setVisibility(View.GONE);
                        lbTempAddress.setVisibility(View.GONE);
                    } else {
                        tvTempAddress.setText(tempAddr);
                    }

                    tvPerAddress.setText(perAddr);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } else {
            onBackPressed();
        }

        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.toolbar_text);
            TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
            if (retrieveBean != null) {
             //   textView.setText(retrieveBean.getNameStr());
            } else {
                textView.setText(R.string.app_name);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewPhotoActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}

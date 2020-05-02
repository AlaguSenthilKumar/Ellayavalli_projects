package com.alwar.elayavalli.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alwar.elayavalli.Constants;
import com.alwar.elayavalli.Others.RetrieveBean;
import com.alwar.elayavalli.R;

public class ViewDetailsActivity extends AppCompatActivity {

    RetrieveBean retrieveBean;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        retrieveBean = (RetrieveBean) getIntent().getSerializableExtra(Constants.Profile);
        type = getIntent().getStringExtra(Constants.TYPE);

        if (retrieveBean != null) {

                setContentView(R.layout.view_address);

                TextView tvTDetails = findViewById(R.id.tv_details);

                String detailsStr =
                                    retrieveBean.getAdiyenNameStr() + "\n" +
                                    retrieveBean.getAachariyarNameStr() + "\n" +
                                    retrieveBean.getNativePlaceStr() + "\n" +
                                    retrieveBean.getJobStr() + "\n" +
                                    retrieveBean.getMobileNoStr() + "\n" +
                                    retrieveBean.getEmailIDStr() + "\n";

/*
            "Name               :" + retrieveBean.getAdiyenNameStr() + "\n" +
                    "Acharyan Tirunamam :" + retrieveBean.getAachariyarNameStr() + "\n" +
                    "Place              :" + retrieveBean.getNativePlaceStr() + "\n" +
                    "Occupation         :" + retrieveBean.getJobStr() + "\n" +
                    "Whatsapp No        :" + retrieveBean.getMobileNoStr() + "\n" +
                    "Email Id           :" + retrieveBean.getEmailIDStr() + "\n";
*/

            tvTDetails.setText(detailsStr);

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
        Intent intent = new Intent(ViewDetailsActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}

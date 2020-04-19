package com.alwar.elayavalli.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alwar.elayavalli.Constants;
import com.alwar.elayavalli.Others.AppCommUtils;
import com.alwar.elayavalli.Others.RetrieveBean;
import com.alwar.elayavalli.R;
import com.alwar.elayavalli.RealmController;


public class ProfileActivity extends AppCompatActivity {

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

  //  RealmController realmController;
    Context context;
    boolean isSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_page);
        context = this;
        initUI();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.toolbar_text);
            TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
            textView.setText(R.string.app_name);
        }

       // realmController = RealmController.with();

        Button btnSubmit = findViewById(R.id.btn_submit);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.create);
        item.setVisible(true);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.create:
                create();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void create() {
        if (retriveData() != null) {
          //  String profileId = realmController.insertProfileDetail(retriveData());
           // if (profileId != null) {
             //   Constants.insertInBuildImage(realmController, profileId);
               // updateDataInFirebase(profileId);
            //    onBackPressed();
            }
            else {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
       // }
    }

    private void initUI() {
        renderCreatePage();
    }

    private void updateDataInFirebase(String profileId) {
  //      FireBaseController fireBaseController = new FireBaseController();
    //    fireBaseController.setFireBaseReference();
      //  fireBaseController.addNewData(profileId, realmController.getAllProfileById(profileId));
    }

    public void renderCreatePage() {

        LinearLayout llayFirst = findViewById(R.id.first);
        TextView name = llayFirst.findViewById(R.id.tv_name);
        name.setText(R.string.string_name);

        LinearLayout llaySecond = findViewById(R.id.second);
        TextView tvAachariyarName = llaySecond.findViewById(R.id.tv_name);
        tvAachariyarName.setText(R.string.string_aachariyar_name);

        LinearLayout llayThree = findViewById(R.id.third);
        TextView tvAge = llayThree.findViewById(R.id.tv_name);
        EditText etAge = llayThree.findViewById(R.id.et_username);
        etAge.setInputType(InputType.TYPE_CLASS_NUMBER);
        tvAge.setText(R.string.string_age);

        LinearLayout llayFour = findViewById(R.id.four);
        TextView tvNativePlace = llayFour.findViewById(R.id.tv_name);
        tvNativePlace.setText(R.string.string_nativeplace);

        LinearLayout llayFive = findViewById(R.id.five);
        TextView tvJob = llayFive.findViewById(R.id.tv_name);
        tvJob.setText(R.string.string_occupation);

        LinearLayout llaySix = findViewById(R.id.six);
        TextView mobileNo = llaySix.findViewById(R.id.tv_name);
        EditText etMobileNo = llaySix.findViewById(R.id.et_username);
        etMobileNo.setInputType(InputType.TYPE_CLASS_PHONE);
        mobileNo.setText(R.string.string_mobileno);

        LinearLayout llaySeven = findViewById(R.id.seven);
        TextView tvEmailId = llaySeven.findViewById(R.id.tv_name);
        tvEmailId.setText("Email ID:");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public RetrieveBean retriveData() {

        isSuccess = true;

        String adiyenNameStr, aachariyarNameStr, adiyenAgeStr, nativePlaceStr,
                jobStr, mobileNoStr, emailIDStr;


        RetrieveBean retrieveBean = new RetrieveBean();

        LinearLayout llayFirst = findViewById(R.id.first);
        EditText etName = llayFirst.findViewById(R.id.et_username);
        adiyenNameStr = etName.getText().toString().trim();
        validation(llayFirst);
        retrieveBean.setAdiyenNameStr(adiyenNameStr);

        LinearLayout llaySecond = findViewById(R.id.second);
        EditText etAachariyarName = llaySecond.findViewById(R.id.et_username);
        aachariyarNameStr = etAachariyarName.getText().toString().trim();
        validation(llaySecond);
        retrieveBean.setAachariyarNameStr(aachariyarNameStr);

        LinearLayout llayThird = findViewById(R.id.third);
        EditText EtAdiyenAge = llayThird.findViewById(R.id.et_username);
        adiyenAgeStr = EtAdiyenAge.getText().toString().trim();
        validation(llayThird);
        retrieveBean.setAdiyenAgeStr(adiyenAgeStr);

        LinearLayout llayFour = findViewById(R.id.four);
        EditText etNativePlace = llayFour.findViewById(R.id.et_username);
        nativePlaceStr = etNativePlace.getText().toString().trim();
        validation(llayFour);
        retrieveBean.setNativePlaceStr(nativePlaceStr);

        LinearLayout llayFive = findViewById(R.id.five);
        EditText etDOB = llayFive.findViewById(R.id.et_username);
        jobStr = etDOB.getText().toString().trim();
        validation(llayFive);
        retrieveBean.setJobStr(jobStr);

        LinearLayout llaySix = findViewById(R.id.six);
        EditText etMobileNo = llaySix.findViewById(R.id.et_username);
        mobileNoStr = etMobileNo.getText().toString().trim();
        validation(llaySix);
        retrieveBean.setMobileNoStr(mobileNoStr);

        LinearLayout llaySeven = findViewById(R.id.seven);
        EditText etEmailID = llaySeven.findViewById(R.id.et_username);
        emailIDStr = etEmailID.getText().toString().trim();
        validation(llaySeven);
        retrieveBean.setEmailIDStr(  emailIDStr);


        if (isSuccess) {
            return retrieveBean;
        } else {
            return null;
        }

    }


    private void validation(LinearLayout linearLayout) {

        EditText editText = linearLayout.findViewById(R.id.et_username);
        TextView textView = linearLayout.findViewById(R.id.tv_name);

        if (isSuccess && "".equals(editText.getText().toString().trim())) {
            String alertMessage = "Please enter " + textView.getText().toString();
            AppCommUtils.showAlertDialog(context, alertMessage);
            isSuccess = false;
        }

    }
    /**
     * To  hide the Keyboard
     *
     * @param activity Activity
     */
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

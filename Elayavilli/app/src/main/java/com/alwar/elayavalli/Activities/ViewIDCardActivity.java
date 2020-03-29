package com.alwar.elayavalli.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alwar.elayavalli.Constants;
import com.alwar.elayavalli.Others.RetrieveBean;
import com.alwar.elayavalli.Others.Utility;
import com.alwar.elayavalli.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewIDCardActivity extends AppCompatActivity {


    RetrieveBean retrieveBean;
    private LinearLayout llMainLay;
    private Bitmap bitmap;
    String profileId;
    File folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_card);

        ActionBar actionBar = getSupportActionBar();
         llMainLay = findViewById(R.id.mainLay);

        TextView tvName = findViewById(R.id.tv_id_name);
        TextView tvNativePlace = findViewById(R.id.tv_id_nativeplace);
        TextView tvCellNo = findViewById(R.id.tv_id_cellno);
        TextView tvID = findViewById(R.id.tv_id);
        TextView tvAddress = findViewById(R.id.tv_per_address);
        ImageView ivProfile = findViewById(R.id.iv_profile_image);

        retrieveBean = (RetrieveBean)  getIntent().getSerializableExtra(Constants.Profile);

        if (retrieveBean != null) {
            setTextValue(retrieveBean.getAdiyenNameStr(), tvName, " : ");
            setTextValue(retrieveBean.getNativePlaceStr(), tvNativePlace, "   : ");
            setTextValue(retrieveBean.getMobileNoStr(), tvCellNo, "  : ");
            setTextValue(retrieveBean.profileId, tvID, "   : ");

//            String address = retrieveBean.getAddress();
            profileId = retrieveBean.profileId;

//            Picasso.with(this).load(new File(retrieveBean.getImagePath())).into(ivProfile, new Callback() {
//                @Override
//                public void onSuccess() {
//                    Log.d("ImageViewAdapter::", "onSuccess: ");
//                }
//
//                @Override
//                public void onError() {
//                    Log.d("ImageViewAdapter::", "onError: ");
//                }
//            });

            try {

                String address = null;
                JSONArray jsonArray = new JSONArray(address);

//                JSONObject tempObject = jsonArray.optJSONObject(0);
                JSONObject perObject = jsonArray.optJSONObject(1);

//                String tempAddr = tempObject.optString("doorNo") + ", " + tempObject.optString("streetName") + ",\n" + tempObject.optString("place") + ",\n" +
//                        tempObject.optString("district") + ",\n" + tempObject.optString("state");

                String perAddr = perObject.optString("doorNo") + ", " + perObject.optString("streetName") + ",\n" + perObject.optString("place") + ",\n" +
                        perObject.optString("district") + ",\n" + perObject.optString("state");

                tvAddress.setText(perAddr);

//                Spannable spanText = Spannable.Factory.getInstance().newSpannable(perAddr);
//                spanText.setSpan(new BackgroundColorSpan(0x80F4FFFF), 0, perAddr.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                tvAddress.setText(spanText);

            } catch (JSONException e) {
                e.printStackTrace();
            }
   }

        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.toolbar_text);
            TextView textView = getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
            if (retrieveBean != null) {
                //textView.setText(retrieveBean.getNameStr());
            } else {
                textView.setText(R.string.app_name);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.create);
        item.setIcon(R.drawable.ic_action_download);
        item.setVisible(true);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.create:
                Log.d("size"," "+ llMainLay.getWidth() +"  "+ llMainLay.getWidth());
                bitmap = loadBitmapFromView(llMainLay, llMainLay.getWidth(), llMainLay.getHeight());
                Constants.showAlertDialog(this);
                boolean result = Utility.checkPermission(ViewIDCardActivity.this);
                if (result)
                    createPdf();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewIDCardActivity.this , ListActivity.class);
        startActivity(intent);
        finish();
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        float hight = 1380 ;
//        float width = 1024 ;

//86 * 55 CM
// 6772 w * 4331 h // 200 resolutions

//        float hight = 1380;
//        float width = 1024;
        float hight = 4331;
        float width = 6772;

        int convertHighet = (int) hight, convertWidth = (int) width;


        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        folder = new File(Environment.getExternalStorageDirectory(), Constants.AppNewFolderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String targetPdf = folder.getAbsolutePath()+ "/" +  profileId + ".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "ID Card downloaded successfully!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        document.close();
        if (Constants.dum_dialog != null)
            Constants.dum_dialog.hide();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){

        File file = new File(folder.getAbsolutePath()+ "/" +  profileId + ".pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(ViewIDCardActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setTextValue(String retrieveBean, TextView textView, String space) {

        String nameStr = textView.getText().toString().trim() + space + retrieveBean;

        textView.setText(nameStr);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createPdf();
                    break;
                } else {
                    if (Constants.dum_dialog != null)
                        Constants.dum_dialog.hide();

                }
        }
    }
}

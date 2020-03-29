package com.alwar.elayavalli;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class Constants {

    public static String PROFILE_ID = "profileId";
    public static Dialog dum_dialog;

    public static final String AppNewFolderName="Temple";
    public static final String Profile = "profile";
    public static final String TYPE = "type";
    public static final String PHOTO = "photo";
    public static final String ADDRESS = "address";

    public static GradientDrawable getGradientDrawable(int startColor, int endColor, GradientDrawable.Orientation orientation, float radius) {
        GradientDrawable gd = new GradientDrawable(orientation, new int[] {Color.parseColor("#"+Integer.toHexString(startColor)), Color.parseColor("#"+Integer.toHexString(endColor))});
        gd.setCornerRadius(radius);
        return gd;
    }

    public static void addImageToRealm(String profileId, RealmController realmController) {

        File folder = new File(Environment.getExternalStorageDirectory(), Constants.AppNewFolderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        realmController.insertUpdateProfilePictures(profileId, getFilePath(folder, profileId));
    }

    private static String getFilePath(File src, String profileId) {
        String path = null;
        if (src.isDirectory()) {
            File[] files = src.listFiles();
            for (File file : files) {
                if (file.getAbsoluteFile().getName().equalsIgnoreCase(profileId + ".jpeg"))
                    path = file.getAbsolutePath();
            }
        }
        return path;
    }

    public static void insertInBuildImage(RealmController realmController, String profileId) {

        try {
            File folder = new File(Environment.getExternalStorageDirectory(), Constants.AppNewFolderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String targetPdf = folder.getAbsolutePath() + "/" +  profileId + ".jpeg";
            File filePath = new File(targetPdf);
            FileOutputStream  fOut = new FileOutputStream(filePath);
            fOut.flush();
            fOut.close();
            Constants.addImageToRealm(profileId, realmController);
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
        }
    }

    public static void showAlertDialog(Context context) {

        if (Constants.dum_dialog != null) {
            Constants.dum_dialog.hide();
        }

        Constants.dum_dialog = new Dialog(context);
        Constants.dum_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(Constants.dum_dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Constants.dum_dialog.setContentView(R.layout.dialog_progressbar);
        Constants.dum_dialog.setCancelable(false);
        Constants.dum_dialog.show();
    }
}

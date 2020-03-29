package com.alwar.elayavalli;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.alwar.elayavalli.Others.RetrieveBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class FireBaseController {

    private static DatabaseReference databaseReference;
    private static StorageReference storageReference;
    String TAG = "FireBaseLog";

    public void setFireBaseReference() {
        if (databaseReference == null)
            databaseReference = FirebaseDatabase.getInstance().getReference();

        if (storageReference == null)
            storageReference = FirebaseStorage.getInstance().getReference();

    }

    public void addNewData(String profileId, RetrieveBean retrieveBean) {
        databaseReference.child(profileId).setValue(retrieveBean, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.v(TAG, "Data could not be saved " + databaseError.getMessage());
                } else {
                    Log.v(TAG, "Data saved successfully.");
                }
            }
        });
        Log.v(TAG, "Data saved successfully.");
    }

    /**
     * Add New Profile Image to Firebase
     * @param context   Context
     * @param profileId String
     * @param imagePath String
     */
    public void addNewProfileImage(final Context context, String profileId, String imagePath) {
        if (imagePath != null) {
            Uri file = Uri.fromFile(new File(imagePath));
            StorageReference imageRef = storageReference.child("images/" + profileId + ".jpg");
            Constants.showAlertDialog(context);

            imageRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (Constants.dum_dialog != null)
                                Constants.dum_dialog.hide();
                            Log.v(TAG, "Upload Success.");
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            if (Constants.dum_dialog != null)
                                Constants.dum_dialog.hide();

                            Log.v(TAG, "Upload Failed." + exception.getMessage());
                            Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    /**
     * User data change listener
     */
    public void syncUserDetails() {
        final RealmController realmController = new RealmController();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RetrieveBean retrieveBean = postSnapshot.getValue(RetrieveBean.class);
                    if (realmController.getAllProfileById(retrieveBean.profileId) == null) {
                        realmController.insertSyncDetail(retrieveBean);
                        syncUserProfileImage(retrieveBean.profileId);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    /**
     * Sync Profile Image to Local Storage
     * @param profileId    String
     */
    public void syncUserProfileImage(String profileId) {

        StorageReference  myStorage = storageReference.child("images/" + profileId + ".jpg"); // Use this and provide the reference
        File folder = new File(Environment.getExternalStorageDirectory(), Constants.AppNewFolderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String targetPdf = folder.getAbsolutePath() + "/" +  profileId + ".jpeg";
        final File localFile = new File(targetPdf);

        myStorage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.v(TAG, "Firebase local tem file created  created " +  localFile.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.v(TAG, ";local tem file not created  created " + exception.toString());
            }
        });

    }

}

package com.aabhishekpatel.jj.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aabhishekpatel.jj.R;
import com.aabhishekpatel.jj.Tab.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final int GalleryPick = 1;
    String userid, username, phoneNo, otp_id, profile;
    private Uri imageUri;
    private String myUri = "";
    FirebaseDatabase rootNode;
    private CircleImageView profile_image;
    private EditText profile_name;
    private Button add_name;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private DatabaseReference mDatabaseref, databaseReference;
    private FirebaseStorage storage;
    private StorageTask mUploadTask;

    Bitmap bitmap;

    private Intent ProfileActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        ProfileActivity = new Intent(this, MainActivity.class);

        rootNode = FirebaseDatabase.getInstance();
        mDatabaseref = rootNode.getReference("Users");
        storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pics");


        Initialization();

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //openFileChooser();
            }
        });

        add_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addname();

            }
        });
    }


    public void addname() {
        //String setName =

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String userid = firebaseUser.getUid();
        final String phoneNo = firebaseUser.getPhoneNumber();


        if (profile_name.length() == 0) {
            profile_name.setError("Name cannot be left Empty!");
            profile_name.requestFocus();
            return;
            //Toast.makeText(this, "Write your name first..", Toast.LENGTH_SHORT).show();

        } else {
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("userid", userid);
            profileMap.put("username", profile_name.getText().toString());
            profileMap.put("phoneNo", phoneNo);
            profileMap.put("profile", "https://firebasestorage.googleapis.com/v0/b/jagahjamin1.appspot.com/o/ic_launcher-playstore.png?alt=media&token=c26a3fc0-6e93-4d2a-a8f1-452e992ac433");


            mDatabaseref.child(phoneNo).setValue(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                sendTopMainActivity();
                                Toast.makeText(ProfileActivity.this, "Profile added with No:" + phoneNo, Toast.LENGTH_SHORT).show();

                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(ProfileActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    /*------------- Initialization Done here---------------*/
    private void Initialization() {

        add_name = (Button) findViewById(R.id.add_name);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        profile_name = (EditText) findViewById(R.id.profile_name);
    }

    /*------------- Send to main Activity from here ---------------*/
    private void sendTopMainActivity() {
        Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
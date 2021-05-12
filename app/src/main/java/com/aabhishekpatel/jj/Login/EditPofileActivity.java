package com.aabhishekpatel.jj.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aabhishekpatel.jj.R;
import com.aabhishekpatel.jj.Tab.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPofileActivity extends AppCompatActivity {

    private CircleImageView profile_image;
    private EditText profile_name;
    private Button update;
    private TextView phone;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private static final  int GalleryPick =1;
    private StorageReference UserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pofile_edit);

        mAuth = FirebaseAuth.getInstance();
        //currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference("ProfileImages");
        UserProfile = FirebaseStorage.getInstance().getReference("ProfileImages");


        /*------------- Initialization ---------------*/
        Initialization();

        /*------------- to invisivble the name ---------------*/
        //profile_name.setVisibility(View.INVISIBLE);

        /*------------- onclick on Update Butoon ---------------*/
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSettings();
            }
        });
        /*------------- RetrieveUserInformantion ---------------*/
        RetrieveUserInformantion();

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery_Intent = new Intent();
                gallery_Intent.setAction(Intent.ACTION_GET_CONTENT);
                gallery_Intent.setType("image/*");
                startActivityForResult(gallery_Intent,GalleryPick);

            }
        });

    }

    /*------------- Image Cropper ---------------*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode== RESULT_OK && data!=null)
        {
            Uri ImageUri = data.getData();
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (requestCode == RESULT_OK)
            {
                Uri resultUri = result.getUri();

                StorageReference filePath = UserProfile.child(currentUserID + ".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       if (task.isSuccessful())
                       {
                           Toast.makeText(EditPofileActivity.this, "Profile Images Uploaded Successfully", Toast.LENGTH_SHORT).show();
                       }else
                       {
                           String message = task.getException().toString();
                           Toast.makeText(EditPofileActivity.this, "Error : "+ message, Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        }
    }

    /*------------- Update is done from here ---------------*/
    private void UpdateSettings() {
        String setName = profile_name.getText().toString();

        if (TextUtils.isEmpty(setName)) {
            Toast.makeText(this, "Please write your name first.", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("userid", currentUserID);
            profileMap.put("name", setName);

            RootRef.child("Users").setValue(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                sendTopMainActivity();
                                Toast.makeText(EditPofileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(EditPofileActivity.this, "Error "+ message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    /*------------- Send to main Activity from here ---------------*/
    private void sendTopMainActivity()
    {
        Intent mainIntent = new Intent(EditPofileActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    /*------------- Initialization Done here---------------*/
    private void Initialization() {

        update = (Button) findViewById(R.id.update);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        profile_name = (EditText) findViewById(R.id.profile_name);
                phone = (TextView) findViewById(R.id.phone);
    }

    /*------------- RetrieveUserInformantion  is donr from here---------------*/
    private void RetrieveUserInformantion() {
        RootRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("phoneNo"))))
                {
                    String retrieveName =  dataSnapshot.child("name").getValue().toString();
                    String retrievephone =  dataSnapshot.child("phoneNo").getValue().toString();
                    String retrieveProfile =  dataSnapshot.child("profile").getValue().toString();

                    profile_name.setText(retrieveName);
                    phone.setText(retrievephone);
                }

                else  if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name"))&& (dataSnapshot.hasChild("phoneNo")))
                {
                    String retrieveName =  dataSnapshot.child("name").getValue().toString();
                    String retrievephone =  dataSnapshot.child("phoneNo").getValue().toString();

                    profile_name.setText(retrieveName);
                    phone.setText(retrievephone);
                }

                else
                {
                    //profile_name.setVisibility(View.VISIBLE);
                    Toast.makeText(EditPofileActivity.this, "Please set & and Update your profile information...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
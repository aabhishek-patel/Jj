package com.aabhishekpatel.jj.Drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aabhishekpatel.jj.Login.ProfileActivity;
import com.aabhishekpatel.jj.R;
import com.aabhishekpatel.jj.Tab.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {

    private EditText cxname, cxemail, cxphone, cxhouse, cxaddress,
            cxdistrict, cxstate, cxpincode, cxcomments;

    private Button submit;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    StorageTask uploadTask;
    private String currentUserID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        cxname = findViewById(R.id.cxname);
        cxemail = findViewById(R.id.cxemail);
        cxphone = findViewById(R.id.cxphone);
        cxhouse = findViewById(R.id.cxhouseno);
        cxaddress = findViewById(R.id.cxaddress);
        cxdistrict = findViewById(R.id.cxdistric);
        cxstate = findViewById(R.id.cxstate);
        cxpincode = findViewById(R.id.cxpincode);
        cxcomments = findViewById(R.id.cxcomments);
        submit = findViewById(R.id.submit);

        databaseReference = FirebaseDatabase.getInstance().getReference("Comments");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDetails();
            }
        });

    }

    private void uploadDetails() {
        String name = cxname.getText().toString();
        String email = cxemail.getText().toString();
        final String phone = cxphone.getText().toString();
        String house = cxhouse.getText().toString();
        String address = cxaddress.getText().toString();
        String district = cxdistrict.getText().toString();
        final String state = cxstate.getText().toString();
        String pincode = cxpincode.getText().toString();
        String comments = cxcomments.getText().toString();

        //FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String cxid = databaseReference.push().getKey();

        if (cxname.length() == 0) {
            cxname.setError("Name cannot be left Empty!");
            cxname.requestFocus();
            return;
            //Toast.makeText(this, "Write your name first..", Toast.LENGTH_SHORT).show();

        }
        if (cxemail.length() == 0) {
            cxemail.setError("Email cannot be left Empty!");
            cxemail.requestFocus();
            return;
            //Toast.makeText(this, "Write your name first..", Toast.LENGTH_SHORT).show();

        }
        if (cxphone.length() == 0) {
            cxphone.setError("Phone cannot be left Empty!");
            cxphone.requestFocus();
            return;
            //Toast.makeText(this, "Write your name first..", Toast.LENGTH_SHORT).show();

        }
        if (cxhouse.length() == 0) {
            cxhouse.setError("House No / Street  cannot be left Empty!");
            cxhouse.requestFocus();
            return;
        }
        if (cxaddress.length() == 0) {
            cxaddress.setError("Address cannot be left Empty!");
            cxaddress.requestFocus();
            return;
            //Toast.makeText(this, "Write your name first..", Toast.LENGTH_SHORT).show();

        }
        if (cxdistrict.length() == 0) {
            cxdistrict.setError("District cannot be left Empty!");
            cxdistrict.requestFocus();
            return;
        }
        if (cxstate.length() == 0) {
            cxstate.setError("State cannot be left Empty!");
            cxstate.requestFocus();
            return;
        }
        if (cxpincode.length() == 0) {
            cxpincode.setError("Pincode cannot be left Empty!");
            cxpincode.requestFocus();
            return;
        }
        if (cxcomments.length() == 0) {
            cxcomments.setError("Comments & feedback cannot be left Empty!");
            cxcomments.requestFocus();
            return;
        }
        else {
            HashMap<String, String> profileMap = new HashMap<>();
            //profileMap.put("cxid", cxid);
            profileMap.put("cxname", name);
            profileMap.put("cxemail", email);
            profileMap.put("cxphone", phone);
            profileMap.put("cxhouse", house);
            profileMap.put("cxaddress", address);
            profileMap.put("cxdistrict", district);
            profileMap.put("cxstate", state);
            profileMap.put("cxpincode", pincode);
            profileMap.put("cxcomments", comments);

            databaseReference.child(phone).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ContactActivity.this, "feeback send as no:" + phone, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(ContactActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
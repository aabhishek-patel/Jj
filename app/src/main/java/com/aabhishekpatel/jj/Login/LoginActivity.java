package com.aabhishekpatel.jj.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aabhishekpatel.jj.R;
import com.aabhishekpatel.jj.Tab.MainActivity;
import com.aabhishekpatel.jj.Utility.NetworkChangeListerner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNo;
    private CountryCodePicker countryCodePicker;
    private Button get_otp;
    TextView processText;
    NetworkChangeListerner networkChangeListerner = new NetworkChangeListerner();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*------------- hooks ---------------*/
        countryCodePicker = findViewById(R.id.countryCodePicker);
        phoneNo = findViewById(R.id.regPhoneNo);
        get_otp = findViewById(R.id.get_otp);
        processText = findViewById(R.id.text_process);
        countryCodePicker.registerCarrierNumberEditText(phoneNo);

        mAuth = FirebaseAuth.getInstance();

        /*--------------checkPermission ----------------*/
        checkPermission();


        /*------------------------------- go to otp fragment -------------------------------*/
        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });


    }

    /*------------------------------- checkPermission -------------------------------*/
    public void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied())
                {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                if (multiplePermissionsReport.areAllPermissionsGranted())
                {

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).onSameThread().check();
        /*Dexter.withContext(this)
                .withPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        //Normal Funnctional if user allow

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();

                    }
                }).check();*/
    }

    /*------------------------------- sendVerificationCode -------------------------------*/
    private void sendVerificationCode() {
        String str_getUserPhoneNumber = phoneNo.getText().toString().trim();//Get Phone Number
        String str_phoneNo = "+" + countryCodePicker.getFullNumber() + str_getUserPhoneNumber;
        if (phoneNo.length() == 0) {
            phoneNo.setError("Phone number is required!");
            phoneNo.requestFocus();
            return;
            //Toast.makeText(LoginActivity.this, "Enter No...", Toast.LENGTH_SHORT).show();
        } else if (phoneNo.getText().toString().replace("", "").length() != 10) {
            Toast.makeText(LoginActivity.this, "Enter Correct No..", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), Otp_Ver_Activity.class);
            intent.putExtra("phoneNo", countryCodePicker.getFullNumberWithPlus().replace("", ""));
            startActivity(intent);
            finish();
        }
    }


    /*------------------------------- onStart -------------------------------*/
    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListerner, filter);
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            sendTomainActivity();
        }
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListerner);
        super.onStop();
    }

    /*------------------------------- go to Main Activity -------------------------------*/
    private void sendTomainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    /*------------------------------- Back pressed -------------------------------*/
    public void onBackPressed(){
            AlertDialog.Builder alertDailogBuilder = new AlertDialog.Builder(this);
            alertDailogBuilder.setTitle(R.string.leaving_soon);
            alertDailogBuilder.setIcon(R.drawable.ic_exit);
            alertDailogBuilder.setMessage(R.string.login_at);
            alertDailogBuilder.setCancelable(false);
            alertDailogBuilder.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDailogBuilder.setNegativeButton(R.string.login, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(LoginActivity.this, R.string.welcome, Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alertDailogBuilder.create();
            alertDialog.show();
            //super.onBackPressed();
    }
}
package com.aabhishekpatel.jj.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aabhishekpatel.jj.R;
import com.aabhishekpatel.jj.Tab.MainActivity;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Otp_Ver_Activity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    String userid, username, phoneNo, otp_id, profile;
    TextView processText;
    //Variables
    private Button verify_btn;
    private PinView otp;
    private TextView resend;
    private FirebaseAuth mAuth;

    private Intent MainActivity;

    /*String codeBySystem;*/
    private MKLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__ver_);

        /*------------- hooks ---------------*/
        verify_btn = findViewById(R.id.verify_btn);
        otp = findViewById(R.id.code_verification);
        resend = findViewById(R.id.resend);
        loader = findViewById(R.id.loader);

        MainActivity = new Intent(this, MainActivity.class);

        mAuth = firebaseAuth.getInstance();

        /*------------- verify_btn ---------------*/
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(otp.getText().toString())) {
                    Toast.makeText(Otp_Ver_Activity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                } else if (otp.getText().toString().replace("", "").length() != 6) {
                    Toast.makeText(Otp_Ver_Activity.this, "Enter Right OTP", Toast.LENGTH_SHORT).show();
                } else {
                    loader.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp_id, otp.getText().toString().replace("", ""));
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        username = getIntent().getStringExtra("username");
        phoneNo = getIntent().getStringExtra("phoneNo");

        sendVerificationCode();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });
    }


    /*------------------------------- sendVerificationCode -------------------------------*/
    private void sendVerificationCode() {

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long l) {
                resend.setText("" + l / 1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText("Resend");
                resend.setEnabled(true);
            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(String otp_id, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Otp_Ver_Activity.this.otp_id = otp_id;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        processText.setText(e.getMessage());
                        processText.setTextColor(Color.RED);
                        processText.setVisibility(View.VISIBLE);
                        Toast.makeText(Otp_Ver_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }

    /*------------------------------- signInWithPhoneAuthCredential -------------------------------*/
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loader.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                            DatabaseReference reference = rootNode.getReference("Users");


                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("userid", userid);
                            hashMap.put("username", "");
                            hashMap.put("phoneNo", phoneNo);
                            hashMap.put("profile", "");

                            reference.child(phoneNo).setValue(hashMap);
                            FirebaseUser user = task.getResult().getUser();

                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(intent);
                            finish();
                            //Pass all fields to the next activity
                            intent.putExtra("username", "");
                            intent.putExtra("phoneNo", phoneNo);
                            intent.putExtra("profile", "");


                            // ...
                        } else {
                            Toast.makeText(Otp_Ver_Activity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
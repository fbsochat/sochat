package com.sochat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sochat.R;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText otpView;
    Button verify;
    TextView phonebox;

    FirebaseAuth auth;
    String phonenumber, verificationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpView = (EditText) findViewById(R.id.et_Otp);
        verify = (Button) findViewById(R.id.btn_Verify);
        phonebox = (TextView) findViewById(R.id.phoneLbl);

        auth = FirebaseAuth.getInstance();


        phonenumber = getIntent().getStringExtra("PhoneNumber");
        phonebox.setText("Verify" + phonenumber);

        initiateotp();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpView.getText().toString().isEmpty())
                    Toast.makeText(OtpActivity.this, "Blank Fild cannot be Processd", Toast.LENGTH_SHORT).show();
                else if (otpView.getText().toString().length()!=6)
                    Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otpView.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


    }

    private void initiateotp() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60L,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String verificationId , @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

        );

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(OtpActivity.this,"Singin Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
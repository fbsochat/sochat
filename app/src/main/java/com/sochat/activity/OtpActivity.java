package com.sochat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.sochat.R;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    OtpView otpView;
    Button verify;
    TextView phonebox;

    FirebaseAuth auth;
    String phonenumber,verificationId;
    //ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpView=(OtpView)findViewById(R.id.otp_view);
        verify=(Button)findViewById(R.id.btn_Verify);
        phonebox=(TextView)findViewById(R.id.phoneLbl);

        auth=FirebaseAuth.getInstance();

       /* progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Verifying OTP");
        progressDialog.setCancelable(false);
        progressDialog.show();

        */


        phonenumber=getIntent().getStringExtra("PhoneNumber");
        phonebox.setText("Verify"+phonenumber);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OtpActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String VerifyId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(VerifyId, forceResendingToken);
                       // progressDialog.dismiss();
                        verificationId =VerifyId;

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        otpView.requestFocus();

                    }
                }) .build();
            PhoneAuthProvider.verifyPhoneNumber(options);

            otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
                @Override
                public void onOtpCompleted(String otp) {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,otp);
                    auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(OtpActivity.this,"Login Succesfull",Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        }
                        else {
                            Toast.makeText(OtpActivity.this, "Login Faild", Toast.LENGTH_SHORT).show();
                        }

                        }
                    });

                }
            });



    }
}
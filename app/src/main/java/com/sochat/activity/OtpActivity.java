package com.sochat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sochat.R;
import com.sochat.activity.api.UserHelper;
import com.sochat.activity.model.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText otpView;
    Button verify;
    TextView phonebox;

    FirebaseAuth auth;
    String phonenumber;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;



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

        verifyPhoneNumber(phonenumber);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpView.getText().toString().isEmpty())
                    Toast.makeText(OtpActivity.this, "Blank Field cannot be Processd", Toast.LENGTH_SHORT).show();
                else if (otpView.getText().toString().length()!=6)
                    Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otpView.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                    }catch (Exception e){
                        Toast toast = Toast.makeText(OtpActivity.this, "Verification Code is wrong", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }

                }
            }
        });


    }

    public void verifyPhoneNumber(String phoneNumber){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = forceResendingToken;

                        Log.d(OtpActivity.class.getName(), "onCodeSent:" + verificationId);
                        // The corresponding whitelisted code above should be used to complete sign-in.
                        OtpActivity.this.enableUserManuallyInputCode();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        // Sign in with the credential
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Toast.makeText(getApplicationContext(),"Invalid Request", Toast.LENGTH_LONG).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            Toast.makeText(getApplicationContext(),"SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void enableUserManuallyInputCode() {
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = authResult.getUser();
                        createUserInFirestore(user);
                        Log.d(OtpActivity.class.getName(),"User Info:" + user.getDisplayName() + " " + user.getEmail() + "" + user.getUid());
                        Toast.makeText(OtpActivity.this,user.getPhoneNumber(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OtpActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // --------------------
    // REST REQUEST
    // --------------------

    // 1 - Http request that create user in firestore
    private void createUserInFirestore(FirebaseUser firebaseUser){

        String uid = firebaseUser.getUid();
        String username = ""; //firebaseUser.getDisplayName();
        String profilePicUrl = (firebaseUser.getPhotoUrl() != null) ? firebaseUser.getPhotoUrl().toString() : null;
        String phonenumber = this.phonenumber; //firebaseUser.getPhoneNumber();
        String emailAddress = null; //firebaseUser.getEmail();
        Integer badges = 0;
        String country = null;
        Integer fans = 0;
        Integer follow = 0;
        Boolean gender = true;
        Boolean isActive = true;
        Integer visitors = 0;
        ArrayList<String> groupUsersList = null;

        User user = new User(uid, username,profilePicUrl,emailAddress,phonenumber,badges,country,fans,follow,gender,isActive,visitors,groupUsersList);

        UserHelper.createUser(user.getUid(), user.getUsername(),user.getProfilePicUrl(),user.getEmailAddress(),user.getPhoneNumber(),user.getBadges(),user.getCountry(),user.getFans(),user.getFollow(),user.getGender(),user.getIsActive(),user.getVisitors(),user.getGroupUsersList()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
            }
        });
    }

}
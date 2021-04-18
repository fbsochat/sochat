package com.sochat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.sochat.R;

public class PhoneVerificationActivity extends AppCompatActivity {
    EditText phone_number;
    Button btn_continue;
    FirebaseAuth auth;

    //ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        phone_number=(EditText)findViewById(R.id.etPhone);
        btn_continue=(Button)findViewById(R.id.btnContinue);

        /*dialog = new ProgressDialog(this);
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

         */



        auth =FirebaseAuth.getInstance();

        phone_number.requestFocus();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // dialog.dismiss();
                Intent intent = new Intent(PhoneVerificationActivity.this,OtpActivity.class);
                intent.putExtra("PhoneNumber",phone_number.getText().toString());
                startActivity(intent);

            }
        });

    }
}
package com.sochat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.sochat.R;

public class PhoneVerificationActivity extends AppCompatActivity {
    EditText phone_number;
    Button btn_continue;
    CountryCodePicker codePicker;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        phone_number=(EditText)findViewById(R.id.etPhone);
        btn_continue=(Button)findViewById(R.id.btnContinue);
        codePicker= (CountryCodePicker)findViewById(R.id.ccP);

        codePicker.registerCarrierNumberEditText(phone_number);

        auth =FirebaseAuth.getInstance();

        phone_number.requestFocus();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // dialog.dismiss();
                Intent intent = new Intent(PhoneVerificationActivity.this,OtpActivity.class);
                intent.putExtra("PhoneNumber",codePicker.getFullNumberWithPlus().replace("",""));
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}
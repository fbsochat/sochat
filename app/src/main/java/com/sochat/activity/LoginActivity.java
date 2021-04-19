package com.sochat.activity;

import androidx.appcompat.app.AppCompatActivity;
import egolabsapps.basicodemine.videolayout.VideoLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sochat.R;

public class LoginActivity extends AppCompatActivity {
    Button google,phone;
    VideoLayout videoLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone=(Button)findViewById(R.id.btn_Phone);
        videoLayout = new VideoLayout(this);



        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,PhoneVerificationActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoLayout.onDestroyVideoLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoLayout.onPauseVideoLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoLayout.onResumeVideoLayout();
    }

}
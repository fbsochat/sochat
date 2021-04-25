package com.sochat.activity;

import android.app.AlertDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sochat.R;
import com.sochat.activity.adaptors.ViewPagerAdapter;
import com.sochat.activity.api.UserHelper;
import com.sochat.activity.fragments.HomeFragment;
import com.sochat.activity.fragments.InboxFragment;
import com.sochat.activity.fragments.ProfileFragment;
import com.sochat.activity.model.User;
import com.sochat.activity.util.Utility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;

    HomeFragment homeFragment;
    InboxFragment inboxFragment;
    ProfileFragment profileFragment;
    MenuItem prevMenuItem;
    private FirebaseFirestore mFireBaseFireStore;
    private EditText editTextUsername;
    private Button buttonConfirmUsername;
    ConstraintLayout constraintLayoutUserName;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFireBaseFireStore = FirebaseFirestore.getInstance();
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        editTextUsername = findViewById(R.id.edit_text_username);
        buttonConfirmUsername = findViewById(R.id.button_confirm_username);
        constraintLayoutUserName = findViewById(R.id.constraint_username);

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(R.string.loading)
                .setCancelable(false)
                .build();

        String userName = Utility.getSharedPreferencesUserName();
        if(TextUtils.isEmpty(userName)){
            constraintLayoutUserName.setVisibility(View.VISIBLE);
        }else{
            constraintLayoutUserName.setVisibility(View.INVISIBLE);
        }

        buttonConfirmUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseUsername();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_inbox:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_profile:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            if (prevMenuItem!=null){
                prevMenuItem.setChecked(false);
            }
            else{
                bottomNavigationView.getMenu().getItem(position).setChecked(false);
            }
                Log.d("Page","onPageSelected: " +position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem=bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setViewPager(viewPager);

    }
    public void setViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        profileFragment = new ProfileFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(inboxFragment);
        adapter.addFragment(profileFragment);
        this.viewPager.setAdapter(adapter);

    }

    private void chooseUsername() {

        String username = editTextUsername.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            showToastMessage("Please enter a username");
            return;
        } else if (username.length() < 6) {
            showToastMessage("Username must be at least 6 characters");
            return;
        }else if(username.contains(" ")){
            showToastMessage("Username must not with space characters");
            return;
        }

        dialog.show();
        CollectionReference userDetailsPath = UserHelper.getUsersCollection();
        userDetailsPath.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String mCurrentUserId = Utility.getSharedPreferencesUserId();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                User user = document.toObject(User.class);

                                if (!user.getUid().equals(mCurrentUserId)) {

                                    if (username.equals(user.getUsername())) {
                                        showToastMessage("This username is taken");
                                        dialog.dismiss();
                                        return;
                                    }
                                }
                            }

                            DocumentReference userDetailsPath = UserHelper.getUsersCollection().document(mCurrentUserId);
                            userDetailsPath.update("username", username).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Utility.setCurrentUserName(username);
                                    constraintLayoutUserName.setVisibility(View.INVISIBLE);
                                    Utility.hideKeyboard(MainActivity.this);
                                    dialog.dismiss();
//                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showToastMessage("Error, please ensure that there is an active network connection");
                                    dialog.dismiss();
                                }
                            });

                        } else {
                            showToastMessage("Error, please ensure that there is an active network connection");
                            dialog.dismiss();
                        }
                    }
                });
    }
    private void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
package com.sochat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sochat.activity.adaptors.MessageListAdapter;
import com.sochat.activity.adaptors.RoomAdapter;
import com.sochat.activity.api.MessageHelper;
import com.sochat.activity.api.UserHelper;
import com.sochat.activity.fragments.ExitFragment;
import com.sochat.activity.fragments.FollowingFragment;
import com.sochat.activity.interfaces.OnKeyboardVisibilityListener;
import com.sochat.activity.model.Group;
import com.sochat.activity.model.Message;
import com.sochat.activity.model.User;
import com.sochat.activity.model.UserMessage;
import com.sochat.activity.util.Constants;
import com.sochat.activity.util.Utility;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity  implements OnKeyboardVisibilityListener {

    TextView textViewChat,exit;
    CardView cardViewChat;
    Button chatBtton;
    EditText editTextMessage;
    public  String groupid = "";
    public String members = "";
    public String group_room_name;
    String userid = "";
    private RecyclerView mMessageRecycler;
    List<UserMessage> messageList = null;
    private MessageListAdapter mMessageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userid = Utility.getSharedPreferencesUserId();
        // Bundles values
        Bundle bundle = getIntent().getExtras();
        groupid = bundle.getString(Constants.GROUP_ID);
        members = bundle.getString(Constants.MEMBERS);
        group_room_name = bundle.getString(Constants.GROUP_ROOM_NAME);

        messageList = new ArrayList<UserMessage>();

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setVisibility(View.INVISIBLE);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMessageRecycler.setLayoutManager(linearLayoutManager);
        mMessageRecycler.setAdapter(mMessageAdapter);

        textViewChat = findViewById(R.id.tv_chat);
        cardViewChat = findViewById(R.id.card_Chat);
        chatBtton = findViewById(R.id.btn_Send);
        editTextMessage = findViewById(R.id.et_Message);
        exit=findViewById(R.id.tv_ExitIcon);
        setKeyboardVisibilityListener(ChatActivity.this);
        textViewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewChat.setVisibility(View.VISIBLE);
                chatBtton.setVisibility(View.VISIBLE);
                editTextMessage.requestFocus();

                InputMethodManager inputMethodManager =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        cardViewChat.getApplicationWindowToken(),
                        InputMethodManager.SHOW_FORCED, 0);

            }
        });

        chatBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editTextMessage.getText())){
                    messageList.clear();
                    mMessageRecycler.setVisibility(View.VISIBLE);
                    // to get values
                    String msg = editTextMessage.getText().toString().trim();
                    Timestamp sentAt = Timestamp.now();
                    String username = Utility.getSharedPreferencesUserName();
                    String userid = Utility.getSharedPreferencesUserId();

                    // to send message at db
                    MessageHelper.saveMessage(msg,sentAt,userid,username,groupid,true);

                    MessageHelper.getMessages(groupid).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Map> allMessages =new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d("GroupIds: ",document.getId());
                                    allMessages.add(document.getData());
                                }
                                for (int counter = 0; counter < allMessages.size(); counter++) {
                                    System.out.println(allMessages.get(counter));
                                    Map<String,Object> map = allMessages.get(counter);
                                    UserMessage userMessage = new UserMessage();
                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                        Log.d(ChatActivity.class.getName(),entry.getKey() + "/" + entry.getValue());
                                        if(userid.equalsIgnoreCase((String)map.get("sentBy"))){
                                            userMessage.setMsgSent(true);
                                        }else {
                                            userMessage.setMsgSent(false);
                                        }
                                        userMessage.setMessage((String)map.get("message"));
                                        userMessage.setNickname((String)map.get("username"));

                                    }
                                    messageList.add(userMessage);
                                }
                                // your type message to show in chat window
//                                UserMessage userMessage = new UserMessage();
//                                userMessage.setMsgSent(true);
//                                userMessage.setMessage(msg);
//                                userMessage.setNickname(username);
//                                messageList.add(userMessage);
                            } else {
                                Log.d(ChatActivity.class.getName(), "Error getting documents: ", task.getException());
                            }
                        }
                    });


                    if (mMessageRecycler.getAdapter().getItemCount() > 0) {
                        mMessageRecycler.smoothScrollToPosition(mMessageRecycler.getAdapter().getItemCount() - 1);
                    }
                    mMessageAdapter.notifyDataSetChanged();
                    //empty textbox
                    editTextMessage.setText("");
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChatActivity.this, ExitFragment.class);
                startActivity(i);
            }
        });

        MessageHelper.getMessagesNewUpdate(groupid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
//                        messageList.clear();
                        if (e != null) {
                            Log.d(ChatActivity.class.getName(), "listen:error", e);
                            return;
                        }
                        ArrayList<Map> allMessages = new ArrayList<>();
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Log.d(ChatActivity.class.getName(), "New Changes: " + dc.getDocument().getData());
                                allMessages.add(dc.getDocument().getData());

                                for (int counter = 0; counter < allMessages.size(); counter++) {
                                    System.out.println(allMessages.get(counter));
                                    Map<String, Object> map = allMessages.get(counter);
                                    UserMessage userMessage = new UserMessage();
                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                        Log.d(ChatActivity.class.getName(), entry.getKey() + "/" + entry.getValue());
                                        if (userid.equalsIgnoreCase((String) map.get("sentBy"))) {
                                            userMessage.setMsgSent(true);
                                        } else {
                                            userMessage.setMsgSent(false);
                                        }
                                        userMessage.setMessage((String) map.get("message"));
                                        userMessage.setNickname((String) map.get("username"));

                                    }
                                        messageList.add(userMessage);

                                }
                                Log.d("YourTag", "messageList: " + messageList);

                            }
                        }
                        if (mMessageRecycler.getAdapter().getItemCount() > 0) {
                            mMessageRecycler.smoothScrollToPosition(mMessageRecycler.getAdapter().getItemCount() - 1);
                        }
                        mMessageAdapter.notifyDataSetChanged();
                        //empty textbox
                        editTextMessage.setText("");
                    }
                });

//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
//                                        @Nullable FirebaseFirestoreException e) {
//
//                        if (e != null) {
//                            Log.d("YourTag", "Listen failed.", e);
//                            return;
//                        }
//                        ArrayList<Map> allMessages =new ArrayList<>();
//                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
////                                    Log.d("GroupIds: ",document.getId());
//                            allMessages.add(document.getData());
//                        }
//                        for (int counter = 0; counter < allMessages.size(); counter++) {
//                            System.out.println(allMessages.get(counter));
//                            Map<String,Object> map = allMessages.get(counter);
//                            UserMessage userMessage = new UserMessage();
//                            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                                Log.d(ChatActivity.class.getName(),entry.getKey() + "/" + entry.getValue());
//                                if(userid.equalsIgnoreCase((String)map.get("sentBy"))){
//                                    userMessage.setMsgSent(true);
//                                }else {
//                                    userMessage.setMsgSent(false);
//                                }
//                                userMessage.setMessage((String)map.get("message"));
//                                userMessage.setNickname((String)map.get("username"));
//
//                            }
//                            messageList.add(userMessage);
//                        }
////                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
////                            if (doc.exists()){
////                                Message message = doc.toObject(Message.class);
////                                messageList.add(message);
////                                mAdapter.notifyDataSetChanged();
////                            }
////                        }
//                        Log.d("YourTag", "messageList: " + messageList);
//                        if (mMessageRecycler.getAdapter().getItemCount() > 0) {
//                            mMessageRecycler.smoothScrollToPosition(mMessageRecycler.getAdapter().getItemCount() - 1);
//                        }
//                        mMessageAdapter.notifyDataSetChanged();
//                        //empty textbox
//                        editTextMessage.setText("");
//                    }
//                });
    }

    private void setKeyboardVisibilityListener(final OnKeyboardVisibilityListener onKeyboardVisibilityListener) {
        final View parentView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private boolean alreadyOpen;
            private final int defaultKeyboardHeightDP = 100;
            private final int EstimatedKeyboardDP = defaultKeyboardHeightDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect rect = new Rect();

            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, parentView.getResources().getDisplayMetrics());
                parentView.getWindowVisibleDisplayFrame(rect);
                int heightDiff = parentView.getRootView().getHeight() - (rect.bottom - rect.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;

                if (isShown == alreadyOpen) {
                    //Log.i("Keyboard state", "Ignoring global layout change...");
                    return;
                }
                alreadyOpen = isShown;
                onKeyboardVisibilityListener.onVisibilityChanged(isShown);
            }
        });
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        //Toast.makeText(ChatActivity.this, visible ? "Keyboard is active" : "Keyboard is Inactive", Toast.LENGTH_SHORT).show();
        if(visible){

        }else{
            cardViewChat.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cardViewChat.setVisibility(View.GONE);
        editTextMessage.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // hide the keyboard in order to avoid getTextBeforeCursor on inactive InputConnection
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(editTextMessage.getWindowToken(), 0);
        Utility.hideKeyboard(ChatActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.hideKeyboard(ChatActivity.this);
    }
}
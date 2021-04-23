package com.sochat.activity.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sochat.activity.model.Group;
import com.sochat.activity.model.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageHelper {
    private static final String COLLECTION_NAME = "message";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getMessageCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> saveMessage(
            String message,
            Timestamp sentAt,
            String sentBy,
            String currentGroupId
    ) {
        Message msg = new Message(
                 message,
                 sentAt,
                 sentBy
        );

        return MessageHelper.getMessageCollection().document(currentGroupId).set(msg);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getMessages(String groupid) {
        return MessageHelper.getMessageCollection().document(groupid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateMessage(Message msg, String groupid) {
        return MessageHelper.getMessageCollection().document(groupid).update("messages", msg);
    }


    // --- DELETE ---

    public static Task<Void> deleteChat(String groupid) {
        return MessageHelper.getMessageCollection().document(groupid).delete();
    }
}

package com.sochat.activity.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sochat.activity.model.Message;

public class MessageHelper {
    private static final String COLLECTION_NAME = "message";
    private static final String COLLECTION_PATH_NAME = "messages";
    private static final String COLLECTION_ORDER_BY = "sentAt";
    private static final Integer LIMIT_TO_LAST_MSG = 5;

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getMessageCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<DocumentReference> saveMessage(
            String message,
            Timestamp sentAt,
            String sentBy,
            String userName,
            String currentGroupId,
            Boolean isMsgSent
    ) {
        Message msg = new Message(
                 message,
                 sentAt,
                 sentBy,
                userName,
                isMsgSent
        );

        return MessageHelper.getMessageCollection().document(currentGroupId).collection(COLLECTION_PATH_NAME).add(msg);
    }

    // --- GET ---

    public static Task<QuerySnapshot> getMessages(String groupid) {
        return MessageHelper.getMessageCollection().document(groupid).collection(COLLECTION_PATH_NAME).orderBy(COLLECTION_ORDER_BY, Query.Direction.DESCENDING).limit(LIMIT_TO_LAST_MSG).get();
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

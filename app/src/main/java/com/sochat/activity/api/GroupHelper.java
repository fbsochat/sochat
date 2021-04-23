package com.sochat.activity.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sochat.activity.model.Group;
import com.sochat.activity.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupHelper {

    private static final String COLLECTION_NAME = "group";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createGroup(
                                        Timestamp createdAt,
                                        String createdBy,
                                        ArrayList<String> members,
                                        Timestamp modifieddAt,
                                        String name,
                                        HashMap recentMessage,
                                        String groupRoomNo

                                        ) {
        DocumentReference documentReference = GroupHelper.getUsersCollection().document();
        String groupId = documentReference.getId();
        Group groupToCreate = new Group(
                createdAt,
                createdBy,
                groupId,
                members,
                modifieddAt,
                name,
                recentMessage,
                groupRoomNo
        );

        return documentReference.set(groupToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getGroup(String uid) {
        return UserHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String name, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("name", name);
    }



    // --- DELETE --- we will avoid it instead will deactive

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }
}

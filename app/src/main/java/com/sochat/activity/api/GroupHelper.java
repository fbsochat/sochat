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

    public static CollectionReference getGroupCollection() {
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
                                        String groupRoomNo,
                                        String groupPicUrl,
                                        String groupAbout

                                        ) {
        DocumentReference documentReference = GroupHelper.getGroupCollection().document();
        String groupId = documentReference.getId();
        Group groupToCreate = new Group(
                createdAt,
                createdBy,
                groupId,
                members,
                modifieddAt,
                name,
                recentMessage,
                groupRoomNo,
                groupPicUrl,
                groupAbout
        );

        return documentReference.set(groupToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getGroup(String uid) {
        return GroupHelper.getGroupCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String name, String uid) {
        return GroupHelper.getGroupCollection().document(uid).update("name", name);
    }

    // --- DELETE --- we will avoid it instead will deactive

    public static Task<Void> deleteUser(String uid) {
        return GroupHelper.getGroupCollection().document(uid).delete();
    }
}

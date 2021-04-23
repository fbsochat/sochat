package com.sochat.activity.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sochat.activity.model.User;

import java.util.ArrayList;

public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid,
                                        String username,
                                        String profilePicUrl,
                                        String phonenumber,
                                        String emailAddress,
                                        Integer badges,
                                        String country,
                                        Integer fans,
                                        Integer follow,
                                        Boolean gender,
                                        Boolean isActive,
                                        Integer visitors,
                                        ArrayList<String> groups) {
        User userToCreate = new User(
                uid,
                username,
                profilePicUrl,
                phonenumber,
                emailAddress,
                badges,
                country,
                fans,
                follow,
                gender,
                isActive,
                visitors,
                groups
        );
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE Profile ---

    public static Task<Void> updateProfile(String uid,String username, String gender,String dob,String country, String bio) {
        return UserHelper.getUsersCollection().document(uid).update("username", username,"gender",gender,"dob",dob,"country",country,"bio",bio);
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    // --- UPDATE IsActive ---

    public static Task<Void> updateIsActive(Boolean isActive, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("isActive", isActive);
    }


    // --- DELETE --- we will avoid it instead will deactive

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}

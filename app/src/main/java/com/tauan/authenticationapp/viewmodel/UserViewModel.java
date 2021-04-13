package com.tauan.authenticationapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tauan.authenticationapp.data.User;

import java.util.concurrent.atomic.AtomicReference;

public class UserViewModel extends ViewModel {

    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public MutableLiveData<User> getProviderDataFromFireBase(String uid) {
        if (uid != null) {
            databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        userMutableLiveData.setValue(snapshot.getValue(User.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("erro", "onCancelled: " + error.toException());
                }
            });

        }
        return userMutableLiveData;
    }

    public Boolean createUser(String name, String email, String password, String phone) {
        AtomicReference<Boolean> iscreated = new AtomicReference<>(false);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                disconnectLastUser();
                user = auth.getCurrentUser();
            } else {
                Log.w("erro", "createUser: ", task.getException());
            }
        });

        String uid = user.getUid();

        databaseReference.child(uid).setValue(new User(name, email, uid, phone, password)).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                iscreated.getAndSet(true);
            }
        }).addOnFailureListener(e -> {
            Log.e("erro", "onFailure: " + e.getMessage());
        });

        return iscreated.get();
    }

    public void signInUser(String email, String password) {
       auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user = auth.getCurrentUser();
            } else {
                Log.w("erro", "signInUser: " + task.getException());
            }
        });

    }

    public String getUidToLogin() {
        return auth.getUid();
    }

    public void disconnectLastUser(){
        auth.signOut();
    }

    public String getUidToRegister(){
        return  user.getUid();
    }
}

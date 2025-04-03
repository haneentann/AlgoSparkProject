package com.example.algosparkproject.data;


import com.example.algosparkproject.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {
    private FirebaseAuth firebaseAuth;

    public AuthRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public Task<AuthResult> login(User user, String password) {
        return firebaseAuth.signInWithEmailAndPassword(user.getEmail(), password);
    }

    public Task<AuthResult> register(User user, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password);
    }

    public User getUser() {
        com.google.firebase.auth.FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            return new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
        }
        return null;
    }
}
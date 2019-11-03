package com.thealien.topnews.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thealien.topnews.Activities.StartActivity;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private Context context;

    public MutableLiveData<String> loginEmail = new MutableLiveData<>();
    public MutableLiveData<String> loginPassword = new MutableLiveData<>();

    public ObservableBoolean progressVisibility = new ObservableBoolean();

    public void initVars(Context context) {
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

//    public void setLoginEmail(String email) {
//        loginEmail.setValue(email);
//    }
//
//    public void setLoginPassword(String password) {
//        loginPassword.setValue(password);
//    }

    public void performLogin() {
        progressVisibility.set(true);
        if (TextUtils.isEmpty(loginEmail.getValue())) {
            progressVisibility.set(false);
            return;
        }

        if (TextUtils.isEmpty(loginPassword.getValue())) {
            Toast.makeText(context, "enter your password", Toast.LENGTH_SHORT).show();
            progressVisibility.set(false);
            return;
        }

        mAuth.signInWithEmailAndPassword(loginEmail.getValue(), loginPassword.getValue())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressVisibility.set(true);
                            context.startActivity(new Intent(context, StartActivity.class));
                            ((Activity) context).finish();
                        } else {
                            Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show();
                            progressVisibility.set(false);
                        }
                    }
                });
    }

    public void checkForUser() {
        if(mAuth.getCurrentUser() != null){
            context.startActivity(new Intent(context,StartActivity.class));
            ((Activity)context).finish();
        }
    }

}

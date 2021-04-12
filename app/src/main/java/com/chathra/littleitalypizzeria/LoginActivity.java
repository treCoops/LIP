package com.chathra.littleitalypizzeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.chathra.littleitalypizzeria.Helper.AlertBar;
import com.chathra.littleitalypizzeria.Helper.ProgressDialog;
import com.chathra.littleitalypizzeria.Helper.ToolTip;
import com.chathra.littleitalypizzeria.Helper.Validator;
import com.chathra.littleitalypizzeria.Util.ConnectionUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtEmailAddress, txtPassword;
    ImageView imgEmailStatus, imgPasswordStatus;

    private boolean doubleBackToExitPressedOnce = false;

//    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

//    public static UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        txtEmailAddress = findViewById(R.id.txtEmailAddress);
        txtPassword = findViewById(R.id.txtPassword);
        imgEmailStatus = findViewById(R.id.imgEmailStatus);
        imgPasswordStatus = findViewById(R.id.imgPasswordStatus);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtSignUp = findViewById(R.id.txtSignUp);

        activeTextChangeListeners();
//        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog();

        if(ConnectionUtil.isInternetAvailable(getApplicationContext(), LoginActivity.this)){
//            if(mAuth.getCurrentUser() != null){
//                progressDialog.showProgressBar(LoginActivity.this);
//                getUserData(mAuth.getUid());
//            }
        }

        btnLogin.setOnClickListener(v -> {

            if(!ConnectionUtil.isInternetAvailable(getApplicationContext(), LoginActivity.this)){
                return;
            }

            if(Validator.checkEmpty(txtEmailAddress.getText().toString().trim())){
                ToolTip.show(getApplicationContext(), txtEmailAddress, "Email address required!");
                vibrator.vibrate(5);
                return;
            }

            if(!Validator.validateEmail(txtEmailAddress.getText().toString().trim())){
                ToolTip.show(getApplicationContext(), txtEmailAddress, "Email address must be well formatted!");
                vibrator.vibrate(5);
                return;
            }

            if(Validator.checkEmpty(txtPassword.getText().toString().trim())){
                ToolTip.show(getApplicationContext(), txtPassword, "Password required!");
                vibrator.vibrate(5);
                return;
            }

            if(!Validator.textLength(txtPassword.getText().toString().trim(), 6)){
                ToolTip.show(getApplicationContext(), txtPassword, "Password must have more than 6 characters!");
                vibrator.vibrate(5);
                return;
            }

//            login(txtEmailAddress.getText().toString().trim(), txtPassword.getText().toString());


        });

        txtSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            Animatoo.animateSlideLeft(LoginActivity.this);
        });
    }

    void activeTextChangeListeners(){
        txtEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(Validator.checkEmpty(txtEmailAddress.getText().toString().trim())){
                    imgEmailStatus.setImageResource(R.drawable.caution);
                } else if(!Validator.validateEmail(txtEmailAddress.getText().toString().trim())){
                    imgEmailStatus.setImageResource(R.drawable.cancel);
                } else {
                    imgEmailStatus.setImageResource(R.drawable.check);
                }

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Validator.checkEmpty(txtPassword.getText().toString().trim())){
                    imgPasswordStatus.setImageResource(R.drawable.caution);
                } else if(!Validator.textLength(txtPassword.getText().toString().trim(), 6)){
                    imgPasswordStatus.setImageResource(R.drawable.cancel);
                } else {
                    imgPasswordStatus.setImageResource(R.drawable.check);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        AlertBar.notifyBackPressed(LoginActivity.this, "Please click BACK again to exit!");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

//    void login(String email, String password){
//        progressDialog.showProgressBar(LoginActivity.this);
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            getUserData(user.getUid());
//
//                        } else {
//                            AlertBar.notifyDanger(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage());
//                            progressDialog.dismissProgressBar();
//                        }
//                    }
//                });
//    }
//
//    void getUserData(String uID){
//        FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                progressDialog.dismissProgressBar();
//                if(dataSnapshot.exists()){
//                    user = dataSnapshot.getValue(UserModel.class);
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                    Animatoo.animateSlideLeft(LoginActivity.this);
//                    finishAffinity();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                AlertBar.notifyDanger(LoginActivity.this, "No related data found!");
//                progressDialog.dismissProgressBar();
//            }
//        });
//    }
}
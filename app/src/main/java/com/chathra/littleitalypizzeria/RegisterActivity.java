package com.chathra.littleitalypizzeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.chathra.littleitalypizzeria.Helper.AlertBar;
import com.chathra.littleitalypizzeria.Helper.ProgressDialog;
import com.chathra.littleitalypizzeria.Helper.ToolTip;
import com.chathra.littleitalypizzeria.Helper.Validator;
import com.chathra.littleitalypizzeria.Model.UserModel;
import com.chathra.littleitalypizzeria.Util.ConnectionUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText txtFullName, txtRegisterEmailAddress, txtPassword, txtConfirmPassword;
    ImageView imgFullNameStatus, imgEmailStatus, imgPasswordStatus, imgConfirmPasswordStatus;

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        TextView txtSignIn = findViewById(R.id.txtSignIn);
        Button btnRegister = findViewById(R.id.btnRegister);

        txtFullName = findViewById(R.id.txtFullName);
        txtRegisterEmailAddress = findViewById(R.id.txtRegisterEmailAddress);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        imgFullNameStatus = findViewById(R.id.imgFullNameStatus);
        imgEmailStatus = findViewById(R.id.imgEmailStatus);
        imgPasswordStatus = findViewById(R.id.imgPasswordStatus);
        imgConfirmPasswordStatus = findViewById(R.id.imgConfirmPasswordStatus);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog();

        ConnectionUtil.isInternetAvailable(getApplicationContext(), RegisterActivity.this);

        txtSignIn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            Animatoo.animateSlideRight(RegisterActivity.this);
            finishAffinity();
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ConnectionUtil.isInternetAvailable(getApplicationContext(), RegisterActivity.this)){
                    return;
                }

                if(Validator.checkEmpty(txtFullName.getText().toString().trim())){
                    ToolTip.show(getApplicationContext(), txtFullName, "Full name required!");
                    vibrator.vibrate(5);
                    return;
                }
                if(!Validator.validatePersonName(txtFullName.getText().toString().trim())){
                    ToolTip.show(getApplicationContext(), txtFullName, "Full name required without special characters!");
                    vibrator.vibrate(5);
                    return;
                }

                if(Validator.checkEmpty(txtRegisterEmailAddress.getText().toString().trim())){
                    ToolTip.show(getApplicationContext(), txtRegisterEmailAddress, "Email address required!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.validateEmail(txtRegisterEmailAddress.getText().toString().trim())){
                    ToolTip.show(getApplicationContext(), txtRegisterEmailAddress, "Email address must be well formatted!");
                    vibrator.vibrate(5);
                    return;
                }

                if(Validator.checkEmpty(txtPassword.getText().toString())){
                    ToolTip.show(getApplicationContext(), txtPassword, "Password required!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.textLength(txtPassword.getText().toString(), 6)){
                    ToolTip.show(getApplicationContext(), txtPassword, "Password must have more than 6 characters!");
                    vibrator.vibrate(5);
                    return;
                }

                if(Validator.checkEmpty(txtConfirmPassword.getText().toString())){
                    ToolTip.show(getApplicationContext(), txtConfirmPassword, "Confirm password required!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.textLength(txtConfirmPassword.getText().toString(), 6)){
                    ToolTip.show(getApplicationContext(), txtConfirmPassword, "Confirm password must have more than 6 characters!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.checkTwoSame(txtPassword.getText().toString(), txtConfirmPassword.getText().toString())){
                    ToolTip.show(getApplicationContext(), txtPassword, "Password and confirm password are not same!");
                    ToolTip.show(getApplicationContext(), txtConfirmPassword, "Password and confirm password are not same!");
                    vibrator.vibrate(5);
                    return;
                }

                createAccount(txtRegisterEmailAddress.getText().toString().toLowerCase().trim(), txtPassword.getText().toString());
            }
        });

        activeTextChangeListeners();
    }

    void activeTextChangeListeners(){

        txtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(Validator.checkEmpty(txtFullName.getText().toString().trim())){
                    imgFullNameStatus.setImageResource(R.drawable.caution);
                } else if(!Validator.validatePersonName(txtFullName.getText().toString().trim())){
                    imgFullNameStatus.setImageResource(R.drawable.cancel);
                } else {
                    imgFullNameStatus.setImageResource(R.drawable.check);
                }

            }
        });

        txtRegisterEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(Validator.checkEmpty(txtRegisterEmailAddress.getText().toString().trim())){
                    imgEmailStatus.setImageResource(R.drawable.caution);
                } else if(!Validator.validateEmail(txtRegisterEmailAddress.getText().toString().trim())){
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
                }
                else {
                    imgPasswordStatus.setImageResource(R.drawable.check);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Validator.checkEmpty(txtConfirmPassword.getText().toString().trim())){
                    imgConfirmPasswordStatus.setImageResource(R.drawable.caution);
                } else if(!Validator.textLength(txtConfirmPassword.getText().toString().trim(), 6)){
                    imgConfirmPasswordStatus.setImageResource(R.drawable.cancel);
                }
                else {
                    imgConfirmPasswordStatus.setImageResource(R.drawable.check);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        Animatoo.animateSlideRight(RegisterActivity.this);
        finishAffinity();
    }

    void createAccount(String email, String password){
        progressDialog.showProgressBar(RegisterActivity.this);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUser(user.getUid());

                    } else {
                        AlertBar.notifyDanger(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage());
                        progressDialog.dismissProgressBar();
                    }
                });
    }

    void saveUser(String uID){
        FirebaseDatabase.getInstance().getReference().child("Users").child(uID).setValue(new UserModel(txtFullName.getText().toString(), uID, txtRegisterEmailAddress.getText().toString().trim().toLowerCase()))
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismissProgressBar();
                    AlertBar.notifySuccess(RegisterActivity.this, "Account Created Successfully.");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Animatoo.animateSlideRight(RegisterActivity.this);
                            finishAffinity();
                        }
                    },2000);
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismissProgressBar();
                    AlertBar.notifyDanger(RegisterActivity.this, "Operation failed!");
                });
    }
}
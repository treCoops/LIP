package com.chathra.littleitalypizzeria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.developer.kalert.KAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends Fragment {

    EditText txtFullName, txtRegisterEmailAddress, txtPassword, txtConfirmPassword;
    ImageView imgFullNameStatus, imgEmailStatus, imgPasswordStatus, imgConfirmPasswordStatus;

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        Vibrator vibrator = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);

        Button btnRegister = view.findViewById(R.id.btnRegister);

        txtFullName = view.findViewById(R.id.txtFullName);
        txtRegisterEmailAddress = view.findViewById(R.id.txtRegisterEmailAddress);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtConfirmPassword = view.findViewById(R.id.txtConfirmPassword);

        imgFullNameStatus = view.findViewById(R.id.imgFullNameStatus);
        imgEmailStatus = view.findViewById(R.id.imgEmailStatus);
        imgPasswordStatus = view.findViewById(R.id.imgPasswordStatus);
        imgConfirmPasswordStatus = view.findViewById(R.id.imgConfirmPasswordStatus);

        progressDialog = new ProgressDialog();

        activeTextChangeListeners();
        mAuth = FirebaseAuth.getInstance();


        if(ConnectionUtil.isInternetAvailable(view.getContext(), getActivity())){
            if(mAuth.getCurrentUser() != null){
                updateUI(LoginActivity.user);
            }
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ConnectionUtil.isInternetAvailable(view.getContext(), getActivity())){
                    return;
                }

                if(Validator.checkEmpty(txtFullName.getText().toString().trim())){
                    ToolTip.show(view.getContext(), txtFullName, "Full name required!");
                    vibrator.vibrate(5);
                    return;
                }
                if(!Validator.validatePersonName(txtFullName.getText().toString().trim())){
                    ToolTip.show(view.getContext(), txtFullName, "Full name required without special characters!");
                    vibrator.vibrate(5);
                    return;
                }

                if(Validator.checkEmpty(txtRegisterEmailAddress.getText().toString().trim())){
                    ToolTip.show(view.getContext(), txtRegisterEmailAddress, "Email address required!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.validateEmail(txtRegisterEmailAddress.getText().toString().trim())){
                    ToolTip.show(view.getContext(), txtRegisterEmailAddress, "Email address must be well formatted!");
                    vibrator.vibrate(5);
                    return;
                }

                if(Validator.checkEmpty(txtPassword.getText().toString())){
                    ToolTip.show(view.getContext(), txtPassword, "Password required!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.textLength(txtPassword.getText().toString(), 6)){
                    ToolTip.show(view.getContext(), txtPassword, "Password must have more than 6 characters!");
                    vibrator.vibrate(5);
                    return;
                }

                if(Validator.checkEmpty(txtConfirmPassword.getText().toString())){
                    ToolTip.show(view.getContext(), txtConfirmPassword, "Confirm password required!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.textLength(txtConfirmPassword.getText().toString(), 6)){
                    ToolTip.show(view.getContext(), txtConfirmPassword, "Confirm password must have more than 6 characters!");
                    vibrator.vibrate(5);
                    return;
                }

                if(!Validator.checkTwoSame(txtPassword.getText().toString(), txtConfirmPassword.getText().toString())){
                    ToolTip.show(view.getContext(), txtPassword, "Password and confirm password are not same!");
                    ToolTip.show(view.getContext(), txtConfirmPassword, "Password and confirm password are not same!");
                    vibrator.vibrate(5);
                    return;
                }

                updateUser(LoginActivity.user.getuID(), txtRegisterEmailAddress.getText().toString().trim(), txtFullName.getText().toString(), txtConfirmPassword.getText().toString());

            }
        });

        return view;
    }

    void logout(){
        new KAlertDialog(getContext(), KAlertDialog.CUSTOM_IMAGE_TYPE)
                .confirmButtonColor(R.color.colorPrimaryDark)
                .setContentText("You must logout to continue")
                .setConfirmText("OK")
                .setCustomImage(R.drawable.caution)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        Animatoo.animateSplit(getActivity());
                        kAlertDialog.cancel();
                    }
                })
                .show();
    }

    void updateUI(UserModel user){
        txtFullName.setText(user.getFullName());
        txtRegisterEmailAddress.setText(user.getEmail());
    }

    void updateUser(String uid, String email, String fullName, String password){

        progressDialog.showProgressBar(getActivity());

        mAuth.getCurrentUser().updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            mAuth.getCurrentUser().updatePassword(password)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                FirebaseDatabase.getInstance().getReference().child("Users").child(LoginActivity.user.getuID()).setValue(new UserModel(fullName, uid, email))
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.e("MSG", "Success");
                                                                AlertBar.notifySuccess(getActivity(), "Profile has been updated.");
                                                                progressDialog.dismissProgressBar();
                                                                logout();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.e("MSG", "Error");
                                                                AlertBar.notifyDanger(getActivity(), "Operation failed. Please try again later.");
                                                                progressDialog.dismissProgressBar();
                                                            }
                                                        });
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismissProgressBar();
                                    AlertBar.notifyDanger(getActivity(), "Operation failed. Please try again later.");
                                    Log.e("Pas", e.getLocalizedMessage());
                                }
                            });


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Ex", e.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
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
}
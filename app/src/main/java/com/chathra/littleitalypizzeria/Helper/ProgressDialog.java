package com.chathra.littleitalypizzeria.Helper;

import android.app.Activity;
import android.graphics.Color;

import com.chathra.littleitalypizzeria.R;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;


public class ProgressDialog {

    private FlipProgressDialog flipProgressDialog;
    private List<Integer> imgList = new ArrayList<>();

    public ProgressDialog() {
        flipProgressDialog = new FlipProgressDialog();
        imgList.add(R.drawable.fdp);
        imgList.add(R.drawable.fdp_inverse);

        flipProgressDialog.setImageList(imgList);
        flipProgressDialog.setDimAmount(0.8f);
        flipProgressDialog.setCanceledOnTouchOutside(false);
        flipProgressDialog.setBackgroundColor(Color.parseColor("#313131"));
        flipProgressDialog.setImageSize(300);
        flipProgressDialog.setOrientation("rotationY");
    }

    public void showProgressBar(Activity activity){
        flipProgressDialog.show(activity.getFragmentManager(), "Please Wait..");
    }

    public void dismissProgressBar(){
        flipProgressDialog.dismiss();
    }
}

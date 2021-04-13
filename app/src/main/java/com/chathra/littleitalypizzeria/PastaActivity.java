package com.chathra.littleitalypizzeria;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chathra.littleitalypizzeria.Adapter.ProductAdapter;
import com.chathra.littleitalypizzeria.Helper.AlertBar;
import com.chathra.littleitalypizzeria.Model.ProductModel;
import com.chathra.littleitalypizzeria.Util.ConnectionUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PastaActivity extends Fragment {

    RecyclerView recyclePizza;

    RecyclerView.LayoutManager layoutManager;
    ProductAdapter productAdapter;

    Vibrator vibrator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pasta, container, false);

        ConnectionUtil.isInternetAvailable(view.getContext(), getActivity());

        vibrator = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);

        recyclePizza = view.findViewById(R.id.recyclePizza);



        layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL, false);
        recyclePizza.setLayoutManager(layoutManager);

        populateRecycleView();


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    void populateRecycleView(){
        FirebaseDatabase.getInstance().getReference().child("Pasta").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<ProductModel> products = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    products.add(snapshot.getValue(ProductModel.class));
                }

                initRecycleView(products);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                AlertBar.notifyDanger(getActivity(), "No related data found!");
            }
        });
    }

    void initRecycleView(List<ProductModel> productData){
        productAdapter = new ProductAdapter(productData, getContext());
        recyclePizza.setAdapter(productAdapter);
    }
}
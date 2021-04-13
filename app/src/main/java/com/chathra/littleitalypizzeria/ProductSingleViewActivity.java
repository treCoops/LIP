package com.chathra.littleitalypizzeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

public class ProductSingleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_single_view);

        ImageView imgProduct = findViewById(R.id.imgProduct);
        TextView txtProductName = findViewById(R.id.txtProductName);
        TextView txtProductDescription = findViewById(R.id.txtProductDescription);
        TextView txtProductPrice = findViewById(R.id.txtProductPrice);
        ImageView btnBack = findViewById(R.id.btnBack);

        Picasso.get().load(getIntent().getStringExtra("URL")).fit()
                .into(imgProduct, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Meg", "Success");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Error", e.getLocalizedMessage());
                    }
                });

        txtProductName.setText(getIntent().getStringExtra("NAME"));
        txtProductDescription.setText(getIntent().getStringExtra("DESCRIPTION"));
        txtProductPrice.setText(getIntent().getStringExtra("PRICE"));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductSingleViewActivity.this, HomeActivity.class));
                Animatoo.animateSlideRight(ProductSingleViewActivity.this);
                onBackPressed();
            }
        });

    }
}
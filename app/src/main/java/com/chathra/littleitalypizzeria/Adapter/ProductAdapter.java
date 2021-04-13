package com.chathra.littleitalypizzeria.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chathra.littleitalypizzeria.Model.ProductModel;
import com.chathra.littleitalypizzeria.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ProductModel> productDataList;
    Context context;

    public ProductAdapter(List<ProductModel> productDataList, Context context) {
        this.productDataList = productDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cell, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductModel data = productDataList.get(position);
        if(data.getDescription().length() > 50){
            holder.txtDescription.setText(data.getDescription().substring(0,49)+"....");
        }else{
            holder.txtDescription.setText(data.getDescription());
        }


        holder.txtName.setText(data.getName());
        holder.txtPrice.setText(data.getPrice());

        Picasso.get().load(data.getImgUrl()).fit()
                .into(holder.productImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                       Log.e("Meg", "Success");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Error", e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtDescription, txtPrice;
        ImageView productImage;
        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }

}

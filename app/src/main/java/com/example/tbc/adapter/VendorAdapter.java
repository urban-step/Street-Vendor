package com.example.tbc.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.tbc.R;
import com.example.tbc.activity.ShowVendors;
import com.example.tbc.activity.UserHomeActivity;
import com.example.tbc.activity.VendorDetailPage;
import com.example.tbc.fragment.FragmentVendorDetails;
import com.example.tbc.model.VendorToUserModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<VendorToUserModel> productList;

    //getting the context and product list with constructor
    public VendorAdapter(Context mCtx, List<VendorToUserModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.vendor_card_view, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        VendorToUserModel product = productList.get(position);

        Geocoder geocoder;
        List<Address> addresses= new ArrayList<>();
        geocoder = new Geocoder(mCtx, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.valueOf(product.getLat()), Double.valueOf(product.getLng()), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.textViewTitle.setText(product.getVendor_name());
        holder.textViewShortDesc.setText(addresses.get(0).getAddressLine(0));
        if(product.getRating().equalsIgnoreCase("")){
            holder.textViewRating.setText("0");
        }else
            holder.textViewRating.setText(String.valueOf(product.getRating()));
        holder.textViewPayment.setText(String.valueOf(product.getPayment_option()));
        holder.textViewTime.setText(product.getTiming_of_vendor());
        holder.license.setText(product.getLicense_number());

      //  holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        Glide.with(mCtx).load(product.getVendor_img())
                .placeholder(R.drawable.placeholder)
                .disallowHardwareConfig()
                .priority(Priority.NORMAL)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx.getApplicationContext(), VendorDetailPage.class);
                Bundle args = new Bundle();
                args.putSerializable("vendor",(Serializable)product);
                UserHomeActivity.getInstance().loadFragmentbackstack(new FragmentVendorDetails(),args);

            }
        });



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPayment,textViewTime,license;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.vendorcard_name);
            textViewShortDesc = itemView.findViewById(R.id.vendorcard_location);
            textViewRating = itemView.findViewById(R.id.vendorcard_rating);
            textViewPayment = itemView.findViewById(R.id.vendorcard_payment);
            textViewTime = itemView.findViewById(R.id.vendorcard_time);
            imageView = itemView.findViewById(R.id.vendorcard_image);
            license = itemView.findViewById(R.id.vendorcard_licenseno);
        }
    }
}

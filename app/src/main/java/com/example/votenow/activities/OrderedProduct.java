package com.example.votenow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.votenow.R;
import com.example.votenow.adapters.CartAdapter;
import com.example.votenow.databinding.ActivityCheckoutBinding;
import com.example.votenow.databinding.ActivityOrderedProductBinding;
import com.example.votenow.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderedProduct extends AppCompatActivity {

    CartAdapter adapter;
    ArrayList<Product> products;
    Cart cart;
    ActivityOrderedProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderedProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ordered Products");
        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();

        products = new ArrayList<>();

        cart = TinyCartHelper.getCart();
        int i=1;
        String str = "";


        dbroot.collection("metamart").document("orderedProduct")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String str_product = documentSnapshot.getString("Products");
                            binding.productOrder.setText(str_product);

                        }
                    }
                });




    }

    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
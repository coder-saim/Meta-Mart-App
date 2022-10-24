package com.example.votenow.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.votenow.R;
import com.example.votenow.adapters.ProductAdapter;
import com.example.votenow.databinding.ActivityCategoryBinding;
import com.example.votenow.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    ProductAdapter productAdapter;
    ArrayList<Product> products;

    DatabaseReference database;
    RecyclerView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, products);

        int catId = getIntent().getIntExtra("catId", 0);
        String categoryName = getIntent().getStringExtra("categoryName");


        //Firebase for fetching data....
        productList = findViewById(R.id.productList);
        database = FirebaseDatabase.getInstance().getReference("metamart");
        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        productList.setLayoutManager((layoutManager));


        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this,products);
        productList.setAdapter(productAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    if(product.getCategory().equals(categoryName))
                            products.add(product);
                }
                productAdapter.notifyDataSetChanged();
                //Log.i("Saim",products.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getProducts(catId);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void getProducts(int catId) {
    }
}
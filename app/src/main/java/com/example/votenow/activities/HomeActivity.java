package com.example.votenow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.votenow.R;
import com.example.votenow.adapters.CategoryAdapter;
import com.example.votenow.adapters.ProductAdapter;
import com.example.votenow.databinding.ActivityHomeBinding;
import com.example.votenow.model.Category;
import com.example.votenow.model.Product;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;


import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;
    DatabaseReference database;
    RecyclerView productList;

    int cartItem;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Objects.requireNonNull(getSupportActionBar()).hide();


        initCategorise();
        //initProducts();
        initSlider();

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
                    products.add(product);
                }
                productAdapter.notifyDataSetChanged();
                //Log.i("Saim",products.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });


        //Seaching for Product

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {}

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {}
        });



        //Cart Badge added...
        Cart cart = TinyCartHelper.getCart();
        if(cart.getAllItemsWithQty().entrySet().size() !=0 ) {
            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(cart.getAllItemsWithQty().entrySet().size());
        }

        //Clearing Cart
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("check");
            //The key argument here must match that used in the other activity
            if(value.equals("placed")){
                Intent intent = new Intent(HomeActivity.this,OrderedProduct.class);
                startActivity(intent);
                cart.clearCart();
                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
                badgeDrawable.setVisible(false);
            }

        }



//////////////// ..................Bottom Navigation.............../////////////////////////////////////


        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });



    }

    protected void onStart() {

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
    }

    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }


    private void initSlider() {
        binding.carousel.addData(new CarouselItem("https://idestiny.in/wp-content/uploads/2021/04/2-4.png"));
        binding.carousel.addData(new CarouselItem("https://priyoshopbackup.blob.core.windows.net/priyoshop/content/Images/Thumbs/0242520.jpeg"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/premium-vector/halloween-special-offer-banner-flat-style_483832-228.jpg?w=2000"));
        binding.carousel.addData(new CarouselItem("https://img.freepik.com/premium-vector/sale-banner-up-70-percent-mega-discount-only-this-weekend-social-media-poster-website-header-banner-with-special-super-offer-wholesale-limited-time-event-vector-illustration_419341-245.jpg?w=2000"));
        binding.carousel.addData(new CarouselItem("https://www.offershopbd.com/public/60aa1a9fa6879.jpg"));
        binding.carousel.addData(new CarouselItem("https://image.shutterstock.com/image-vector/vector-88-shopping-day-poster-260nw-2012882648.jpg"));
        binding.carousel.addData(new CarouselItem("https://image.shutterstock.com/image-vector/vector-1010-shopping-day-poster-260nw-2034439334.jpg"));
        binding.carousel.addData(new CarouselItem("https://priyoshopbackup.blob.core.windows.net/priyoshop/content/Images/Thumbs/0242520.jpeg"));
        binding.carousel.addData(new CarouselItem("https://c8.alamy.com/comp/WB7EJA/discount-50-off-sale-special-offer-banner-vector-image-discount-sale-banner-business-shop-logo-banner-vector-WB7EJA.jpg"));
        binding.carousel.addData(new CarouselItem("https://www.templaza.com/images/easyblog_articles/563/templaza-black-friday-cyber-monday-sale-2019-2.png"));


    }

    void initCategorise(){
        categories = new ArrayList<>();
        categories.add(new Category("Men's Clothing","https://cdn-icons-png.flaticon.com/128/2474/2474411.png","#d8d8d8","Some text",1));
        categories.add(new Category("Foods and Groceries","https://cdn-icons-png.flaticon.com/128/1261/1261163.png","#d8d8d8","Some text",1));
        categories.add(new Category("Sport and Outdoors","https://cdn-icons-png.flaticon.com/128/857/857455.png","#d8d8d8","Some text",1));
        categories.add(new Category("Electronics and Gadgets","https://cdn-icons-png.flaticon.com/128/2450/2450309.png","#d8d8d8","Some text",1));
        categories.add(new Category("Women's Clothing","https://cdn-icons-png.flaticon.com/128/3893/3893209.png","#d8d8d8","Some text",1));
        categories.add(new Category("Cosmetics","https://cdn-icons-png.flaticon.com/128/1005/1005769.png","#d8d8d8","Some text",1));
        categories.add(new Category("Home and Living","https://cdn-icons-png.flaticon.com/128/1084/1084008.png","#d8d8d8","Some text",1));
        categories.add(new Category("Jewellery","https://cdn-icons-png.flaticon.com/512/3109/3109867.png","#d8d8d8","Some text",1));
        categoryAdapter = new CategoryAdapter(this, categories);

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager((layoutManager));
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void  initProducts(){
        products = new ArrayList<>();
        products.add(new Product("Women's Friends sweatshirt",
                "https://static-01.daraz.com.bd/p/2624a424364633c14b23822185f0e336.jpg",
                "Nothing","Food",300,10,10,1));
        products.add(new Product("Nike Air Jordan",
                "https://static-01.daraz.com.bd/p/2a2b0c51037c8629fb6f41756baa1646.jpg",
                "Nothing","Drink",300,10,10,1));
        products.add(new Product("Men's business suit",
                "https://static-01.daraz.com.bd/p/d2b218655f56899d2e83cb0e129dffb2.jpg",
                "Nothing","Medicine",300,10,10,1));
        productAdapter = new ProductAdapter(this,products);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.productList.setLayoutManager((layoutManager));
        binding.productList.setAdapter(productAdapter);
    }

}
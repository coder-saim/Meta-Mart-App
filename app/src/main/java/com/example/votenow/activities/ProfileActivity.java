package com.example.votenow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votenow.R;
import com.example.votenow.databinding.ActivityProfileBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseUser user;

    private Button signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        signOut = findViewById(R.id.signOut);

        Objects.requireNonNull(getSupportActionBar()).hide();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address
            String name = user.getDisplayName();
            String email = user.getEmail();

            Log.i("Saim",name+"  "+email);
            TextView pname = findViewById(R.id.pname);
            TextView pemail = findViewById(R.id.pemail);
            pname.setText("Md Saim Ahmmed");
            pemail.setText(email);
        }


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Sign Out Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.putExtra("Saim","logout");
                startActivity(intent);
                finish();
            }
        });


        //Cart Badge added...
        Cart cart = TinyCartHelper.getCart();
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(cart.getAllItemsWithQty().entrySet().size());



        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });




    }
}
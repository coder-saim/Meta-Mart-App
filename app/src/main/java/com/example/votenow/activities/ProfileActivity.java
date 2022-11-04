package com.example.votenow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votenow.R;
import com.example.votenow.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;


public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseUser user;
    FirebaseFirestore dbroot;
    ImageView aboutBtn,contactBtn,shareBtn;
    private Button signOut;
    Context context;
    TextView edittProfile;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        contactBtn = findViewById(R.id.contactBtn);
        aboutBtn = findViewById(R.id.aboutBtn);
        shareBtn = findViewById(R.id.shareBtn);
        signOut = findViewById(R.id.signOut);
        edittProfile = findViewById(R.id.editProfile);
        TextView pname = findViewById(R.id.pname);
        TextView pemail = findViewById(R.id.pemail);
        TextView pPhone = findViewById(R.id.pPhone);
        TextView paddress = findViewById(R.id.paddress);

        dbroot = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Data fetching from firestore....

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        if (user != null) {

            dbroot.collection("metamart").document(email)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                String name = documentSnapshot.getString("Name");
                                String phone = documentSnapshot.getString("Phone");
                                String address = documentSnapshot.getString("Address");

                                pname.setText(name);
                                pemail.setText(email);
                                pPhone.setText(phone);
                                paddress.setText(address);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "No Such Data!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        edittProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });


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

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAbout();
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check Out this Cool Application");
                intent.putExtra(Intent.EXTRA_TEXT,email);
                startActivity(Intent.createChooser(intent,"Contact Via"));
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check Out this Cool Application");
                intent.putExtra(Intent.EXTRA_TEXT,"Enjoy This Application!");
                startActivity(Intent.createChooser(intent,"Share Via"));
            }
        });

        binding.orderedProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,OrderedProduct.class));
            }
        });


        //Cart Badge added...
        Cart cart = TinyCartHelper.getCart();
        if(cart.getAllItemsWithQty().entrySet().size() !=0 ) {
            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(cart.getAllItemsWithQty().entrySet().size());
        }

        //Side navigation...



        //Bottom Navigation
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

    void processAbout(){
        new AlertDialog.Builder(this)
                .setTitle("About Us")
                .setCancelable(false)
                .setMessage("This is an Ecommerce App.\nVersion 1.0.0\nHappy Shopping!!!")
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
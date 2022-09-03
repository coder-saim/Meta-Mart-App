package com.example.votenow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votenow.databinding.ActivityBottomNavigationBinding;
import com.google.firebase.auth.FirebaseAuth;

public class BottomNavigation extends AppCompatActivity {

    ActivityBottomNavigationBinding binding;

    private Button singOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.notifications:
                    replaceFragment(new NotificationsFragment());
                    break;
                case R.id.create:
                    replaceFragment(new CreateFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });


//        singOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(BottomNavigation.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                finish();
//            }
//        });
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

}
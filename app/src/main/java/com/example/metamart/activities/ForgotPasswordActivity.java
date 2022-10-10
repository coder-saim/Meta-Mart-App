package com.example.metamart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.votenow.R;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}
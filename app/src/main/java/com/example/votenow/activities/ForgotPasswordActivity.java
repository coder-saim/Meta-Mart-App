package com.example.votenow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.votenow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button resetButton;
    private EditText txtEmail;
    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Objects.requireNonNull(getSupportActionBar()).hide();

        auth = FirebaseAuth.getInstance();

        resetButton = findViewById(R.id.resetButton);
        txtEmail = findViewById(R.id.txtAddressEdit);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(ForgotPasswordActivity.this);
                pd.setMessage("Please wait...");
                pd.show();
                validateData(pd);
            }
        });

    }

    private void validateData(ProgressDialog pd) {
        email = txtEmail.getText().toString();
        if(email.isEmpty()){
            pd.dismiss();
            txtEmail.setText("Email Required");
            Toast.makeText(this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
        }
        else{
            resetPassword(pd);
        }
    }

    private void resetPassword(ProgressDialog pd) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this,SignInActivity.class);
                    startActivity(intent);
                    finish();
                }

                else{
                    pd.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "Some went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


//cobid75372@haboty.com
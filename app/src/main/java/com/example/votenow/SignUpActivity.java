package com.example.votenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtEmail,txtPassword;
    private Button singUpButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        singUpButton = findViewById(R.id.signUpButton);

        auth = FirebaseAuth.getInstance();

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = txtEmail.getText().toString();
                String txt_password  = txtPassword.getText().toString();

                Log.i("Mylog",txt_email+txt_password);

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) ){
                    Toast.makeText(SignUpActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length()<6){
                    Toast.makeText(SignUpActivity.this, "Password is too short!", Toast.LENGTH_SHORT).show();
                }

                else{
                    registerUser(txt_email,txt_password);
                }
            }
        });
    }




    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete())
                    Toast.makeText(SignUpActivity.this, "Sing Up Successful!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
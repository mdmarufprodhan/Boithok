package com.example.virtualstudyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

        FirebaseAuth auth;
        EditText emailBox, passwordBox, nameBox;
        Button loginBtn, signupBtn;

       FirebaseFirestore databse;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

            databse = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();

            emailBox = findViewById(R.id.emailBox);
            nameBox = findViewById(R.id.nameBox);
            passwordBox = findViewById(R.id.passwordBox);

            loginBtn = findViewById(R.id.signUpBtn);
            signupBtn = findViewById(R.id.signUpBtn);


            signupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email, pass, name;
                    email = emailBox.getText().toString();
                    pass = passwordBox.getText().toString();
                    name = nameBox.getText().toString();

                    User user = new User();
                    user.setEmail(email);
                    user.setPass(pass);
                    user.setName(name);

                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                databse.collection("Users")
                                .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                      startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                    }
                                });
                                // Toast.makeText(SignUpActivity.this, "Account created." , Toast.LENGTH_SHORT ).show();

                            } else {
                                Toast.makeText(SignUpActivity.this,task.getException() .getLocalizedMessage(), Toast.LENGTH_SHORT ).show();


                            }
                        }
                    });

                }
            });
        }
    }
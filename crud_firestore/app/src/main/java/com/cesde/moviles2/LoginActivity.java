package com.cesde.moviles2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);
        /*if(!"error".equals(loadPreferences())){ //!"error".equals(loadPreferences())
            Intent intent = new Intent(LoginActivity.this, ListUsersActivity.class);
            startActivity(intent);
            setContentView(R.layout.activity_login);
        }
        else{
            setContentView(R.layout.activity_login);
            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            mAuth = FirebaseAuth.getInstance();
        }*/
    }

    public void login(View view){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Usuario existe", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ListUsersActivity.class);
                            savePreferences();
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "error usuario no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goToListUsers(View view){
        Intent intent = new Intent(LoginActivity.this, ListUsersActivity.class);
        startActivity(intent);
    }

    public void savePreferences(){
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String userState = "error";
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userState", userState);
        editor.commit();
    }

    public String loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String userState = preferences.getString("userState", "error");
        Toast.makeText(this, "userState" + userState, Toast.LENGTH_SHORT).show();
        return userState;
    }



}
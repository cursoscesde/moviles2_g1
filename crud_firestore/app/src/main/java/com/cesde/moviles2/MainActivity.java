package com.cesde.moviles2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etName, etLastname, etYear, etIdentification, etEmail, etPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etLastname = findViewById(R.id.etLastname);
        etYear = findViewById(R.id.etYear);
        etIdentification = findViewById(R.id.etIdentification);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        // Create a new user with a first and last name
    }
    public void registerUser(View view){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "usuario registrado", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "error registrando usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void signin(View view){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Inicio de session correcto", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "error de usuairo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void saveUser(View view){
        Map<String, Object> user = new HashMap<>();
        String name = etName.getText().toString();
        String lastname = etLastname.getText().toString();
        String identification = etIdentification.getText().toString();
        String year = etYear.getText().toString();
        user.put("name", name);
        user.put("lastname", lastname);
        user.put("identification", identification);
        user.put("year", year);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        Log.w("firebase", "Error adding document", e);
                    }
                });
    }
    public void setUser(View view){
        Map<String, Object> user = new HashMap<>();
        String name = etName.getText().toString();
        String lastname = etLastname.getText().toString();
        String identification = etIdentification.getText().toString();
        String year = etYear.getText().toString();
        user.put("name", name);
        user.put("lastname", lastname);
        user.put("identification", identification);
        user.put("year", year);
        db.collection("users").document(""+ identification)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteUser(View view){
        db.collection("users").document("123456")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "documento eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void loginActivity(View view){
        //Intent intent = new Intent(this, LoginActivity.class);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
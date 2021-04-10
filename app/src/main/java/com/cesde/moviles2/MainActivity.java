package com.cesde.moviles2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etName, etLastname, etYear, etIdentification;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etLastname = findViewById(R.id.etLastname);
        etYear = findViewById(R.id.etYear);
        etIdentification = findViewById(R.id.etIdentification);
        // Create a new user with a first and last name
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
    public void loginActivity(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
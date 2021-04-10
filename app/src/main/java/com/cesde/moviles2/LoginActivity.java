package com.cesde.moviles2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView tvCountry, tvCity, tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvCountry = findViewById(R.id.tvCountry);
        tvCity = findViewById(R.id.tvCity);
        tvAddress = findViewById(R.id.tvAddress);
    }

    public void readApartment(View view){
        DocumentReference docRef = db.collection("Apartments").document("IAbITyr90DH06Aja2A");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("firebase", "DocumentSnapshot data: " + document.getData());
                        String country = document.getString("country");
                        String city = document.getString("city");
                        String address = document.getString("address");
                        tvCountry.setText(country);
                        tvCity.setText(city);
                        tvAddress.setText(address);
                    } else {
                        Toast.makeText(LoginActivity.this, "Documento no existe", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "No tienes conexion a internet", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    public void saveApartment(View view){
        Map<String, Object> user = new HashMap<>();
        user.put("city", "Medellín");
        user.put("country", "colombia");
        user.put("adderess", "calle falsa # 123");
        db.collection("Apartments")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(LoginActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        Log.w("firebase", "Error adding document", e);
                    }
                });
    }
}
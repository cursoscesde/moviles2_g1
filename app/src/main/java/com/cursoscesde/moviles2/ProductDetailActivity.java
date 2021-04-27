package com.cursoscesde.moviles2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        String productName = getIntent().getExtras().getString("productName");
        Toast.makeText(this, "Incoming Data: " + productName, Toast.LENGTH_SHORT).show();
    }
}
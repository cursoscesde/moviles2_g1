package com.cursoscesde.moviles2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private  FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirestoreList = findViewById(R.id.firestore_list);
        firebaseFirestore = FirebaseFirestore.getInstance();
        // Query
        Query query = firebaseFirestore.collection("Products");
        // Recycler options
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query, ProductModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ProductModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // retorna el elemento gráfico
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductModel model) {
                // asigna los datos a cada elemento
                holder.tvListName.setText(model.getName());
                holder.tvListPrice.setText(Long.toString(model.getPrice()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                        intent.putExtra("productName", model.getName());
                        startActivity(intent);

                    }
                });
            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder{
        private TextView tvListName;
        private TextView tvListPrice;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListName = itemView.findViewById(R.id.tvListName);
            tvListPrice = itemView.findViewById(R.id.tvListPrice);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
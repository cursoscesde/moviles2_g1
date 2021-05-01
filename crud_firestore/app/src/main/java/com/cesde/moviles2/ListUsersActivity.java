package com.cesde.moviles2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Models.UserModel;

public class ListUsersActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvFirestoreUsersList;
    FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        rvFirestoreUsersList = findViewById(R.id.rvFirestoreUsersList);

        // query a la base de datos ()
        Query query = db.collection("users");

        //configurar opciones del adaptador
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<UserModel, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UserModel model) {
                //asigna los datos a cada elemento
                holder.tvName.setText(model.getName());
                holder.tvLastname.setText(model.getLastname());
                holder.tvIdentification.setText(model.getIdentification());
                holder.tvYear.setText(model.getYear());
                String id = getSnapshots().getSnapshot(position).getId();
                holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListUsersActivity.this, "" + id, Toast.LENGTH_SHORT).show();
                        deleteUser(id);
                    }
                });
                /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListUsersActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ListUsersActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });*/
            }
            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // crea un elemento gráfico por cada item
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new UsersViewHolder(view);
            }
        };
        rvFirestoreUsersList.setHasFixedSize(true);
        rvFirestoreUsersList.setLayoutManager(new LinearLayoutManager(this));
        rvFirestoreUsersList.setAdapter(adapter);
    }

    private class UsersViewHolder extends RecyclerView.ViewHolder{
        TextView tvIdentification, tvLastname, tvName, tvYear;
        Button btnEditUser, btnDeleteUser;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdentification = itemView.findViewById(R.id.tvIdentification);
            tvLastname = itemView.findViewById(R.id.tvLastname);
            tvName = itemView.findViewById(R.id.tvName);
            tvYear = itemView.findViewById(R.id.tvYear);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
            btnEditUser = itemView.findViewById(R.id.btnEditUser);
        }
    }

    public void deleteUser(String id){
        db.collection("users").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ListUsersActivity.this, "documento eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListUsersActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
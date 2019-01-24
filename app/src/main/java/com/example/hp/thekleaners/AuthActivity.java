package com.example.hp.thekleaners;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hp.thekleaners.pojoClass.ForCarService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private MyAdapter adapter;
    private String user_id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notebookRef = db.collection("Users");

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        user_id = FirebaseAuth.getInstance().getUid();
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notebookRef.document(user_id).collection("Services").document("For Car Service").collection("Car Washing")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<ForCarService> notesList = new ArrayList<>();

                            for (DocumentSnapshot document : task.getResult()) {
                                ForCarService p = document.toObject(ForCarService.class);
                                if (p != null) {
                                    p.setDocumentId(document.getId());
                                }
                                notesList.add(p);
                            }
                            adapter = new MyAdapter(AuthActivity.this, (ArrayList<ForCarService>) notesList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(AuthActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


//https://grokonez.com/android/cloud-firestore-android-example-crud-operations-with-recyclerview
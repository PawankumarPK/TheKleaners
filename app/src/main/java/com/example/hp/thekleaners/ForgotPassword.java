package com.example.hp.thekleaners;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hp.thekleaners.pojoClass.ForAddress;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ForgotPassword extends AppCompatActivity {


    private TextView textViewData;
    private String user_id;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        textViewData = findViewById(R.id.textview_data);
        user_id = FirebaseAuth.getInstance().getUid();

        loadNotesData();
    }


    public void loadNotesData() {
        notebookRef.document(user_id)
                .collection("Address").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            ForAddress forAddress = documentSnapshot.toObject(ForAddress.class);
                            forAddress.setDocumentId(documentSnapshot.getId());

                          /*//  String documentId = forAddress.getTitle();

                            data += "ID: " + documentId;
*/

                            data += "\n\n";
                        }
                        textViewData.setText(data);
                    }
                });
    }
}
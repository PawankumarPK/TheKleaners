package com.example.hp.thekleaners;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private EditText editTextTags;
    private TextView textViewData;
    private String user_id;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPriority = findViewById(R.id.edit_text_priority);
        editTextTags = findViewById(R.id.edit_text_tags);
        textViewData = findViewById(R.id.text_view_data);
        user_id = FirebaseAuth.getInstance().getUid();
    }

    public void addNote(View v) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (editTextPriority.length() == 0) {
            editTextPriority.setText("0");
        }

        int priority = Integer.parseInt(editTextPriority.getText().toString());

        String tagInput = editTextTags.getText().toString();
        String[] tagArray = tagInput.split("\\s*,\\s*");
        Map<String, Boolean> tags = new HashMap<>();

        for (String tag : tagArray) {
            tags.put(tag, true);
        }

       /* ForAddress note = new ForAddress(title, description, priority, tags);

        notebookRef.document(user_id)
                .collection("Address").add(note);
*/

        Intent intent = new Intent(AuthActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    public void loadNotes(View v) {
/*        notebookRef.document(user_id)
                .collection("Address").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            ForAddress note = documentSnapshot.toObject(ForAddress.class);
                            note.setDocumentId(documentSnapshot.getId());

                            String documentId = note.getTitle();

                            data += "ID: " + documentId;


                            data += "\n\n";
                        }
                        textViewData.setText(data);
                    }
                });*/

        Intent intent = new Intent(AuthActivity.this, ForgotPassword.class);
        startActivity(intent);

    }
}
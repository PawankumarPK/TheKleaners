package com.example.hp.thekleaners;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private Uri mainImageURI = null;

    private String user_id;
    private boolean isChanged = false;

    private EditText setupName;
    private EditText setupSurname;
    private EditText setupNumber;
    private Button setupBtn;
    private ProgressBar setupProgress;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        setupName = findViewById(R.id.setup_name);
        setupSurname = findViewById(R.id.setup_surname);
        setupNumber = findViewById(R.id.setup_number);
        setupBtn = findViewById(R.id.setup_btn);
        setupProgress = findViewById(R.id.setup_progress);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();


        storageReference = FirebaseStorage.getInstance().getReference();

        setupProgress.setVisibility(View.VISIBLE);
        setupBtn.setEnabled(false);


        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    if (task.getResult().exists()) {

                        String name = task.getResult().getString("name");
                        String surname = task.getResult().getString("surname");
                        String number = task.getResult().getString("number");

                        setupName.setText(name);
                        setupSurname.setText(surname);
                        setupNumber.setText(number);

                    }

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(AuthActivity.this, "(FIRESTORE Retrieve Error) : " + error, Toast.LENGTH_LONG).show();

                }
                setupProgress.setVisibility(View.INVISIBLE);
                setupBtn.setEnabled(true);


            }
        });


        setupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String user_name = setupName.getText().toString();
                final String surname_name = setupSurname.getText().toString();
                final String number_name = setupNumber.getText().toString();
                if (!TextUtils.isEmpty(user_name) || !TextUtils.isEmpty(surname_name) || !TextUtils.isEmpty(number_name)  ) {

                    setupProgress.setVisibility(View.VISIBLE);

                    if (isChanged) {

                        final String user_id = firebaseAuth.getCurrentUser().getUid();


                        StorageReference username = storageReference.child("user");

                        username.putFile(mainImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful()) {

                                    storeFirestore(task, user_name,surname_name,number_name);



                                } else {

                                    String error = task.getException().getMessage();
                                    Toast.makeText(AuthActivity.this, "( Something went wrong) :   " + error, Toast.LENGTH_LONG).show();
                                    setupProgress.setVisibility(View.INVISIBLE);

                                }

                            }

                        });
                    } else {
                        storeFirestore(null, user_name,surname_name,number_name);

                    }
                }

            }
        });


    }


    private void storeFirestore(@NonNull Task<UploadTask.TaskSnapshot> task, String user_name, String surname,String number) {

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", user_name);
        userMap.put("surname", surname);
        userMap.put("number", number);


        firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "User Settings are updated", Toast.LENGTH_SHORT).show();

                    Intent mainIntent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();


                }
            }
        });
    }
}


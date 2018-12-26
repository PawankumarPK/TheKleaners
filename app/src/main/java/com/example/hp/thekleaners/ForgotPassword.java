package com.example.hp.thekleaners;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.actions.NoteIntents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {

    private EditText enterResetEditText;
    private Button enterResetButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        enterResetEditText = findViewById(R.id.mResetPasswordEmail);
        enterResetButton = findViewById(R.id.mResetPasswordEmailButton);

        enterResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = enterResetEditText.getText().toString();
                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ForgotPassword.this, "Please write your email address first", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "Please check your email account ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                            }else {
                                String message = task.getException().getMessage();
                                Toast.makeText(ForgotPassword.this, "Error Occured: "+ message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

}

package com.example.hp.thekleaners;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        editText = findViewById(R.id.editTextPhone);
        button = findViewById(R.id.buttonContinue);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number =  editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10){
                    editText.setError("Valid Number is required");
                    editText.requestFocus();
                    return;
                }

                String phonenumber = number;
                Intent intent  =new Intent(AuthActivity.this,VerifiyPhoneActivity.class);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
            Intent intent = new Intent(AuthActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}

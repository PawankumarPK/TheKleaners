package com.example.hp.thekleaners;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class ForgotPassword extends AppCompatActivity {

    private EditText tv,tv2;
    private TextView resultTextView;
    private Calendar mCurrentDate;
    private Button button;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tv = (EditText) findViewById(R.id.textView1);
        tv2 = (EditText) findViewById(R.id.textView2);
        resultTextView= (TextView) findViewById(R.id.mResultTextview);
        button= (Button) findViewById(R.id.buttonContinue);


        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        tv.setInputType(InputType.TYPE_NULL);
        month = month + 1;
        tv.setText(day + "/" + month + "/" + year );

        int sum = 30 - day;
        int getAmountSum = sum* 16;


        resultTextView.setText(String.valueOf(getAmountSum));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ForgotPassword.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        monthOfYear = monthOfYear + 1;
                        tv.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = Integer.parseInt(tv.getText().toString());
                int num2 = Integer.parseInt(tv2.getText().toString());

                int sum = 500 - day;

                resultTextView.setText(String.valueOf(sum));


            }
        });
*/
    }
}
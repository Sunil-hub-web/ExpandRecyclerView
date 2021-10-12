package com.example.expandrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.Person;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneCall extends AppCompatActivity {

    Button btn_Call;
    EditText edit_Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        btn_Call = findViewById(R.id.call);
        edit_Number = findViewById(R.id.number);

        btn_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting phone number from edit text
                // and changing it to String
                String phone_number
                        = edit_Number.getText().toString();

                // Getting instance of Intent
                // with action as ACTION_CALL
                Intent phone_intent
                        = new Intent(Intent.ACTION_CALL);

                // Set data of Intent through Uri
                // by parsing phone number
                phone_intent
                        .setData(Uri.parse("tel:"
                                + phone_number));

                // start Intent
                startActivity(phone_intent);

            }
        });

    }
}
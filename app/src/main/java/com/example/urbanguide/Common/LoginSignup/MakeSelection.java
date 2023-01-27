package com.example.urbanguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.urbanguide.R;

public class MakeSelection extends AppCompatActivity {

    Button make_selection_sms_btn,make_selection_email_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);
        make_selection_sms_btn = findViewById(R.id.makeselect_sms_btn);
        make_selection_email_btn = findViewById(R.id.makeselect_email_btn);

        callOTPScreen();
        callSetNewPasswordScreen();
    }
    public void callOTPScreen(){
        make_selection_sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ForgetPassword.class);

                startActivity(intent);
            }
        });
    }
    public void callSetNewPasswordScreen(){
        make_selection_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ForgetPassword.class);

                startActivity(intent);
            }
        });
    }
}
package com.example.urbanguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.urbanguide.R;

public class ForgetPasswordSuccessMessage extends AppCompatActivity {

    ImageView backBtn;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_success_message);

        login_btn = findViewById(R.id.password_success_login_btn);
        backBtn = findViewById(R.id.password_success_back_btn);
        callLoginScreen();
        callPreviousScreen();
    }
    public void callLoginScreen(){
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);

                startActivity(intent);
                finish();
            }
        });
    }
    public void callPreviousScreen(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SetNewPassword.class);

                startActivity(intent);
            }
        });
    }
}
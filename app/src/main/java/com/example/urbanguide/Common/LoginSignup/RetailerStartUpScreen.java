package com.example.urbanguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.urbanguide.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RetailerStartUpScreen extends AppCompatActivity {

    //private final DatabaseReference Nodereference = FirebaseDatabase.getInstance().getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_start_up_screen);
        callLoginScreen();
        callSignUpScreen();
        callForgetPassword();
    }

    public void callLoginScreen(){
        Button btn_login = findViewById(R.id.login_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);

                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transition_login");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }
    public void callSignUpScreen(){
        Button btn_login = findViewById(R.id.signup_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),SignUp.class);

                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View,String>(findViewById(R.id.signup_btn),"transition_signUp");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }
    public void callForgetPassword(){
        Button btn_login = findViewById(R.id.forget_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ForgetPassword.class);

                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View,String>(findViewById(R.id.forget_btn),"transition_forgetPw");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }

}
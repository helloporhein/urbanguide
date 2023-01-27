package com.example.urbanguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanguide.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class SignUp3rdClass extends AppCompatActivity {
    //Variables
    ScrollView scrollView;

    CountryCodePicker countryCodePicker;
    ImageView backBtn;
    Button next, login;
    TextView titleText;
    //Get Data Variables
    TextInputLayout phoneNumber;

    String fullName, email, username, password, date, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);

        //Hooks
        scrollView = findViewById(R.id.signup_3rd_scroll_view);
        backBtn = findViewById(R.id.signup_back_btn);
        next = findViewById(R.id.signup_next_btn);
        login = findViewById(R.id.signup_login_btn);
        titleText = findViewById(R.id.signup_title_text);

        //Hooks for getting data
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup3_phone_number);

        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");

        callVerifyOTPScreen();

    }

    public void callVerifyOTPScreen(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatePhoneNumber()){
                   return;
                }//Validation succeeded and now move to next screen to verify phone number and save data

                //Get all values passed from previous screens using Intent
                String _fullName = getIntent().getStringExtra("fullName");
                String _email = getIntent().getStringExtra("email");
                String _username = getIntent().getStringExtra("username");
                String _password = getIntent().getStringExtra("password");
                String _date = getIntent().getStringExtra("date");
                String _gender = getIntent().getStringExtra("gender");

                String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim(); //Get Phone Number

                String _completephoneNo = "+"+countryCodePicker.getSelectedCountryCode()+_getUserEnteredPhoneNumber;

                Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

                intent.putExtra("fullName",_fullName);
                intent.putExtra("email",_email);
                intent.putExtra("username",_username);
                intent.putExtra("password",_password);
                intent.putExtra("date",_date);
                intent.putExtra("gender",_gender);
                intent.putExtra("phoneNo",_completephoneNo);

                //Add Transition
                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View,String>(scrollView,"transition_OTP_screen");
                //pairs[0] = new Pair<View,String>(backBtn,"transition_back_arrow_btn");
                //pairs[1] = new Pair<View,String>(next,"transition_next_btn");
                //pairs[2] = new Pair<View,String>(titleText,"transition_title_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkPassword = "\\d{10}";
                //"Aw{1,20}z";

        if (val.isEmpty()) {
            phoneNumber.setError("Field cannot be empty");
            return false;
        }
          else if (!val.matches(checkPassword)){
                    phoneNumber.setError("No white spaces are allowed!");
                   return false;
               }

        else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

}
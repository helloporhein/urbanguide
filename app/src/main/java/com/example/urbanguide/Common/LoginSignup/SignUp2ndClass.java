package com.example.urbanguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanguide.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {

    //Variables
    ScrollView scrollView;

    ImageView backBtn;
    Button next, login;
    TextView titleText;
    RadioGroup radioGroup;
    RadioButton selectGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2nd_class);

        //Hooks
        scrollView = findViewById(R.id.signup_3rd_scroll_view);

        backBtn = findViewById(R.id.signup_back_btn);
        next = findViewById(R.id.signup_next_btn);
        login = findViewById(R.id.signup_login_btn);
        titleText = findViewById(R.id.signup_title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);


        callNextSignupScreen();
    }

    public void callNextSignupScreen() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateGender() | !validateAge()) {
                    return;
                }
                selectGender = findViewById(radioGroup.getCheckedRadioButtonId());
                String _gender = selectGender.getText().toString();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                String _date = day + "/" + month + "/" + year;

                String _fullName = getIntent().getStringExtra("fullName");
                String _email = getIntent().getStringExtra("email");
                String _username = getIntent().getStringExtra("username");
                String _password = getIntent().getStringExtra("password");

                Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);

                intent.putExtra("fullName", _fullName);
                intent.putExtra("email", _email);
                intent.putExtra("username", _username);
                intent.putExtra("password", _password);
                intent.putExtra("date", _date);
                intent.putExtra("gender", _gender);
                //Add Transition
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
                pairs[1] = new Pair<View, String>(next, "transition_next_btn");
                pairs[2] = new Pair<View, String>(login, "transition_login_btn");
                pairs[3] = new Pair<View, String>(titleText, "transition_title_text");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
                startActivity(intent, options.toBundle());

            }
        });
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "Your are not eligible to apply", Toast.LENGTH_LONG).show();
            return false;
        } else return true;
    }
}
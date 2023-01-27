package com.example.urbanguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.urbanguide.Databases.UserHelperClass;
import com.example.urbanguide.HelperClasses.SessionManager;
import com.example.urbanguide.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RetailerDashboard extends AppCompatActivity {

    //Variables
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboard);

        scrollView = findViewById(R.id.scrollView_retailerdash);
        TextView textView = findViewById(R.id.textView);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailFromSession();

        String fullName = usersDetails.get(SessionManager.KEY_FULLNAME);
        String phoneNumber = usersDetails.get(SessionManager.KEY_PHONENUMBER);

        textView.setText(fullName+ "\n"+ phoneNumber);
    }

}
package com.example.urbanguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanguide.HelperClasses.SessionManager;
import com.example.urbanguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    ImageView backBtn;
    Button btn_createAccount, btn_login, btn_forget;
    TextView titleText;
    ProgressBar progressBar;
    CountryCodePicker countryCodePicker;
    CheckBox rememberMe;
    //Get Data Variables
    TextInputLayout phoneNumber;
    TextInputLayout password;
    EditText phoneNoEditText,passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        btn_createAccount = findViewById(R.id.login_createAccount);
        btn_login = findViewById(R.id.login);
        btn_forget = findViewById(R.id.forget_btn);
        titleText = findViewById(R.id.login_title_text);
        progressBar = findViewById(R.id.login_progressBar);

        rememberMe = findViewById(R.id.remember_me);
        phoneNoEditText = findViewById(R.id.login_phoneNo_editText);
        passwordEditText = findViewById(R.id.login_password_editText);
        progressBar.setVisibility(View.GONE);
        //Hooks for get data
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.login_phoneNo);
        password = findViewById(R.id.login_password);

        callLoginScreen();
        callForgetPassword();
        callCreateAccount();

        SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phoneNoEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }
    }
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilecon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wificon != null && wificon.isConnected()) || (mobilecon != null && mobilecon.isConnected())){
            return true;
        }else {
            return false;
        }
    }
    public void callLoginScreen() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(Login.this)){
                    showCustomDialog();
                }

                String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim(); //Get Phone Number
                String _password = password.getEditText().getText().toString().trim();

                if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
                    _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
                }
                String _completephoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

                progressBar.setVisibility(View.VISIBLE);

                if (rememberMe.isChecked()){

                    SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBERME);
                    sessionManager.createRememberMeSession(_getUserEnteredPhoneNumber,_password);
                }

                //Retrieve Data from Firebase
                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completephoneNo);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            phoneNumber.setError(null);
                            phoneNumber.setErrorEnabled(false);

                            String systemPassword = snapshot.child(_completephoneNo).child("password").getValue(String.class);
                            if (systemPassword.equals(_password)) {
                                password.setError(null);
                                password.setErrorEnabled(false);

                                String _fullname = snapshot.child(_completephoneNo).child("fullName").getValue(String.class);
                                String _username = snapshot.child(_completephoneNo).child("userName").getValue(String.class);
                                String _email = snapshot.child(_completephoneNo).child("email").getValue(String.class);
                                String _phoneNo = snapshot.child(_completephoneNo).child("phoneNo").getValue(String.class);
                                String _password = snapshot.child(_completephoneNo).child("passwordName").getValue(String.class);
                                String _dateOfBirth = snapshot.child(_completephoneNo).child("date").getValue(String.class);
                                String _gender = snapshot.child(_completephoneNo).child("gender").getValue(String.class);

                                //Create Session
                                SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_USERSESSION);
                                sessionManager.createLoginSession(_fullname,_username,_email,_phoneNo,_password,_dateOfBirth,_gender);

                                startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));

                                Toast.makeText(Login.this, _fullname + "\n" + _email + "\n" + _phoneNo + "\n" + _dateOfBirth, Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "User does not exists!", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    public void callForgetPassword() {
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    public void callCreateAccount() {
        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }


    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                } )
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //startActivity(new Intent(getApplicationContext(),RetailerStartUpScreen.class));
                        //finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
package com.example.urbanguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urbanguide.Databases.CheckInternet;
import com.example.urbanguide.R;
import com.example.urbanguide.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {

    //Variables
    ImageView screenIcon;
    TextView title, description;
    TextInputLayout phoneNumberTextField;
    CountryCodePicker countryCodePicker;
    Button nextBtn;
    Animation animation;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //Hooks
        screenIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        description = findViewById(R.id.forget_password_description);
        phoneNumberTextField = findViewById(R.id.forgetpw_phone_number);
        countryCodePicker = findViewById(R.id.forgetpw_country_code_picker);
        nextBtn = findViewById(R.id.forget_next_btn);
        progressBar = findViewById(R.id.forget_password_progressBar);

        progressBar.setVisibility(View.GONE);
        //Animation Hook
        animation = AnimationUtils.loadAnimation(this,R.anim.side_anim);

        //Set animation to all the elements
        screenIcon.setAnimation(animation);
        title.setAnimation(animation);
        description.setAnimation(animation);
        phoneNumberTextField.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        nextBtn.setAnimation(animation);

        callOTPscreen();
    }

    public void callOTPscreen() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckInternet checkInternet = new CheckInternet();
                if (!checkInternet.isConnected(ForgetPassword.this)){
                showCustomDialog();
                return;
            }
                progressBar.setVisibility(View.VISIBLE);
                String _getUserEnteredPhoneNumber = phoneNumberTextField.getEditText().getText().toString().trim(); //Get Phone Number
               // String _password = password.getEditText().getText().toString().trim();
                if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
                    _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
                }
                String _completephoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

                //Retrieve Data from Firebase
                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completephoneNo);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            phoneNumberTextField.setError(null);
                            phoneNumberTextField.setErrorEnabled(false);

                            Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                            intent.putExtra("phoneNo",_completephoneNo);
                            intent.putExtra("whatToDo","updateData");
                            startActivity(intent);
                            finish();

                                progressBar.setVisibility(View.GONE);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgetPassword.this, "Users does not match!", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),RetailerStartUpScreen.class));
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
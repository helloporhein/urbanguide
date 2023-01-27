package com.example.urbanguide.Common.LoginSignup;

import static android.content.ContentValues.TAG;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.urbanguide.Databases.UserHelperClass;
import com.example.urbanguide.R;
import com.example.urbanguide.User.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    private final DatabaseReference Nodereference = FirebaseDatabase.getInstance().getReference().child("Users");

    //Variables
    ScrollView scrollView;
    PinView pinFromUser;
    String codeBySystem;
    FirebaseAuth mAuth;
    Button verify_btn,password_btn;
    String phoneNo, fullName, email, username, password, date, gender, whatToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        mAuth = FirebaseAuth.getInstance();
        //hooks
        scrollView = findViewById(R.id.signup_OTP_scroll_view);
        pinFromUser = findViewById(R.id.pin_view);
        verify_btn = findViewById(R.id.verify_btn);
        password_btn = findViewById(R.id.verify_password_btn);


        phoneNo = getIntent().getStringExtra("phoneNo");
        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        whatToDo = getIntent().getStringExtra("whatToDo");

        callPhoneMessage(phoneNo);

        sendVerificationCodeToUser(phoneNo);

        callNextScreenFromOTP();
        callSetPasswordScreen();

    }


    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (whatToDo.equals("updateData")){
                                updateOldUsersData();
                            }else {
                                storeNewUserData();

                            }
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //Toast.makeText(VerifyOTP.this,"Verification Completed",Toast.LENGTH_LONG).show();

                            //FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUsersData() {
        Intent intent = new Intent(getApplicationContext(),SetNewPassword.class);
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);
        finish();
    }

    private void storeNewUserData() {
        //Nodereference.setValue("First record!");

        UserHelperClass addNewUser = new UserHelperClass(phoneNo,fullName,email, username , password,date,gender);
        Nodereference.child(phoneNo).setValue(addNewUser);
    }

    public void callSetPasswordScreen(){
        password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOldUsersData();
            }
        });
    }
    public void callNextScreenFromOTP() {
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                storeNewUserData();
                String code = pinFromUser.getText().toString();

                if (!code.isEmpty()) {
                    verifyCode(code);
                }
                //Get all values passed from previous screens using Intent
                String _fullName = getIntent().getStringExtra("fullName");
                String _email = getIntent().getStringExtra("email");
                String _username = getIntent().getStringExtra("username");
                String _password = getIntent().getStringExtra("password");
                String _date = getIntent().getStringExtra("date");
                String _gender = getIntent().getStringExtra("gender");
                String _phoneNo = getIntent().getStringExtra("phoneNo");

                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);

                intent.putExtra("fullName", _fullName);
                intent.putExtra("email", _email);
                intent.putExtra("username", _username);
                intent.putExtra("password", _password);
                intent.putExtra("date", _date);
                intent.putExtra("gender", _gender);
                intent.putExtra("phoneNo", _phoneNo);

                startActivity(intent);
                finishAffinity();

            }
        });
    }

    /**
     * This method displays the given text on the screen.
     */
    private void callPhoneMessage(String _phoneNo) {
        String Message = createPhoneNumber(_phoneNo);
        displayMessage(Message);
    }

    /**
     * This method displays the given text on the screen.
     */
    private String createPhoneNumber(String _phoneNo) {
        return getString(R.string.otp_phonenumber_text, _phoneNo);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = findViewById(R.id.phoneNo_text_view);
        priceTextView.setText(message);
    }
}
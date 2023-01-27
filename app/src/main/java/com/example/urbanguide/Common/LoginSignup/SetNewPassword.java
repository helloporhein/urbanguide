package com.example.urbanguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.example.urbanguide.Databases.CheckInternet;
import com.example.urbanguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    private final DatabaseReference Nodereference = FirebaseDatabase.getInstance().getReference().child("Users");

    Button ok_btn;
    String phoneNo;
    //Get Data Variables
    TextInputLayout newpassword,confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        newpassword = findViewById(R.id.set_new_password);
        confirmpassword = findViewById(R.id.set_new_password_confirm);
        ok_btn = findViewById(R.id.set_new_password_btn);

        //String _phoneNumber = getIntent().getStringExtra("phoneNo");
        phoneNo = getIntent().getStringExtra("phoneNo");
        setNewPasswordBtn();
    }

    public void setNewPasswordBtn(){
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckInternet checkInternet = new CheckInternet();
                if (!checkInternet.isConnected(SetNewPassword.this)){
                    showCustomDialog();
                }
                if (!validateConfirm()){
                    return;
                }

                String newPasswordText = newpassword.getEditText().getText().toString().trim();

                //Update Data in Firebase
                //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                Nodereference.child(phoneNo).child("password").setValue(newPasswordText);

                startActivity(new Intent(getApplicationContext(),ForgetPasswordSuccessMessage.class));
                finish();
            }
        });
    }
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
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

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private boolean validateConfirm() {
        String val = newpassword.getEditText().getText().toString().trim();
        String vol = confirmpassword.getEditText().getText().toString().trim();

        if (!val.equals(vol)){
            confirmpassword.setError("Passwords are not same");
            return false;
        }else {
            confirmpassword.setError(null);
            confirmpassword.setErrorEnabled(false);
            return true;
        }
    }
}
package com.example.urbanguide.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class SessionManager {

    //Variables
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session names
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";

    //Users Variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNo";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    //Remember Me Variable
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONPHONENUMBER = "phoneNo";
    public static final String KEY_SESSIONPASSWORD = "password";

    public SessionManager(Context _context,String sessionName){
        context = _context;
        usersSession = context.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }
    public void createLoginSession(String fullName,String username,String email, String phoneNo,String password,String date,String gender){
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_FULLNAME,fullName);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONENUMBER,phoneNo);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_DATE,date);
        editor.putString(KEY_GENDER,gender);

        editor.commit();
    }

    public HashMap<String, String> getUsersDetailFromSession(){
        HashMap<String ,String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, usersSession.getString(KEY_FULLNAME,null));
        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME,null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PHONENUMBER, usersSession.getString(KEY_PHONENUMBER,null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD,null));
        userData.put(KEY_DATE, usersSession.getString(KEY_DATE,null));
        userData.put(KEY_GENDER, usersSession.getString(KEY_GENDER,null));

        return userData;
    }

    public boolean checkLogin(){
        if (usersSession.getBoolean(IS_LOGIN,false)){
            return true;
        }else {
            return false;
        }
    }

    public void logoutUserFromSession(){
        editor.clear();
        editor.commit();
    }
    public void createRememberMeSession(String phoneNo, String password){
        editor.putBoolean(IS_REMEMBERME,true);

        editor.putString(KEY_SESSIONPHONENUMBER,phoneNo);
        editor.putString(KEY_SESSIONPASSWORD,password);

        editor.commit();
    }
    public HashMap<String, String> getRememberMeDetailFromSession(){
        HashMap<String ,String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONPHONENUMBER, usersSession.getString(KEY_SESSIONPHONENUMBER,null));
        userData.put(KEY_SESSIONPASSWORD, usersSession.getString(KEY_SESSIONPASSWORD,null));

        return userData;
    }
    public boolean checkRememberMe(){
        if (usersSession.getBoolean(IS_REMEMBERME,false)){
            return true;
        }else {
            return false;
        }
    }
}

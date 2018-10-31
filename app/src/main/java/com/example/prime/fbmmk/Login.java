package com.example.prime.fbmmk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prime.fbmmk.Helper.Databasehelper;
import com.example.prime.fbmmk.Model.Fb;

import java.util.regex.Pattern;

/**
 * Created by prime on 10/28/18.
 */

public class Login extends AppCompatActivity {

    private EditText etpassword, etemail;
    private Button btnlogin, btnsignup;
    private TextView help;
    private String  email, password;
    private String dataEm;
    private String dataPass;

  private   Databasehelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etpassword = findViewById(R.id.etPassword);
        etemail = findViewById(R.id.etEmail);
        btnlogin = findViewById(R.id.btnLogin);
        btnsignup = findViewById(R.id.btnSignup);
        help =  findViewById(R.id.tvHelp);


        // validates entries when clicked
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }

        });
        //takes you to the registration page
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });
    }
//    public void onClick(View arg0){
//        switch (arg0.getId()){
//            case R.id.btnLogin:
//                break;
//            case  R.id.btnSignup:
//                break;
//            case R.id.tvHelp:
//                break;
//        }

    private boolean validation() {

        password = etpassword.getText().toString().trim();
        email = etemail.getText().toString().trim();

        db = new Databasehelper(getApplicationContext());
        Fb fb = new Fb();
        boolean diditwork = true;
        try{
        db.getUser(email);
         dataEm = fb.getEmail();
         dataPass = fb.getPassword();
//        String dataPh = fb.getPhone();
        db.close();

        if((isNetworkConnectionAvailable())){
            if(password.length() < 8 ){
                etpassword.setError("recall password cannot be less than 8");
                return diditwork;
            }
            if(!isValidEmaillId(email)){
                etemail.setError("please input a valid email");

            }
//            if (dataPass != password){
//               etpassword.setError("wrong password");
//               return;
//            }
//            if (dataEm != email){
//                etemail.setError("Wrong Email");
//            }
            else {
                doLogin();
//                Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show();
            }


        }
        else{
            Toast.makeText(this, "Please turn on data connection", Toast.LENGTH_SHORT).show();
        }

        }catch (Exception e){
            diditwork = false;
            String error = e.toString();
            Dialog d = new Dialog(this);
            d.setTitle("Error");
            TextView tv = new TextView(this);
            tv.setText(error + "   Cant get" );
            d.setContentView(tv);
            d.show();
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    } finally {
            startActivity(new Intent(this, Homepage.class));
        }return diditwork;
        }

    private void doLogin() {
//            if (dataPass != password){
//               etpassword.setError("wrong password");
//               return;
//            }
//            if (dataEm != email){
//                etemail.setError("Wrong Email");
//            }

        if (dataEm == email && dataPass == password) {
            //your intent here
            startActivity(new Intent(this, Homepage.class));

        } else {
            //toast tellinf the user that hbis credentials is wrong
            Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
    }


    // added as an instance method to an Activity
    boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    boolean isValidEmaillId(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

}

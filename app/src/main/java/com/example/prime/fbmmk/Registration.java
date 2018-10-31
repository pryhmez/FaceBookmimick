package com.example.prime.fbmmk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prime.fbmmk.Helper.Databasehelper;
import com.example.prime.fbmmk.Model.Fb;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    Databasehelper db;

    private EditText etFirstname, etLastname, etEmail, etPhone, etPassword, etConfrimPass;
    private Button btnRegister;
    private Spinner spGender;
    private String firstName, lastName, email, phone, password, confirmpass, gender, fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFirstname = findViewById(R.id.etFirstname);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmailAddress);
        etPhone = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etConfirmPassword);
        etConfrimPass = findViewById(R.id.etConfirmPassword);
        spGender = findViewById(R.id.spGender);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

//thi validates and setstext
    public boolean validation() {
        db = new Databasehelper(getApplicationContext());
        Fb fb = new Fb();

        boolean diditwork = true;

        firstName = etFirstname.getText().toString().trim();
        lastName = etLastname.getText().toString().trim();
        fullname = firstName + " " + lastName;
        email = etEmail.getText().toString().toLowerCase().trim();
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmpass = etConfrimPass.getText().toString().trim();
        gender = spGender.getItemAtPosition(spGender.getSelectedItemPosition()).toString();


        if (isNetworkConnectionAvailable()) {
            //do something
            if (TextUtils.isEmpty(firstName)) {
                etFirstname.setError("First name cannot be empty");
                return diditwork;
            } if (TextUtils.isEmpty(lastName)) {
                etLastname.setError("Last name cannot be empty");
                return diditwork;
            } if (!isValidEmaillId(email)) {
                etEmail.setError("Please enter a correct email");
                return diditwork;
            }  if (password.length() < 6) {
                etPassword.setError("Password cannot be less than 6");
                return diditwork;
            } if (confirmpass == password) {
                etConfrimPass.setError("Password does not match");
                return diditwork;
            }  if (!phone.startsWith("0")) {
                etPhone.setError("Invalid phone number");
                return diditwork;

            }  if (phone.length() != 11 ) {
                etPhone.setError("Phone number must be 11 digits");
            } else {

                //this links with the parameters in the insertuser method in databasehelper class
                //and writes to it what it gets
        db.insertUser(firstName, lastName, password, phone, email);
        db.close();
        //this is another method of doing it by that tutorial video
                try {
                    fb.setFirstname(firstName = etFirstname.getText().toString().trim());
                    fb.setLastname(lastName = etLastname.getText().toString().trim());
                    fullname = firstName + " " + lastName;
                    fb.setEmail(email = etEmail.getText().toString().toLowerCase().trim());
                    fb.setPhone(phone = etPhone.getText().toString().trim());
                    fb.setPassword( password = etPassword.getText().toString().trim());
                    confirmpass = etConfrimPass.getText().toString().trim();
                    gender = spGender.getItemAtPosition(spGender.getSelectedItemPosition()).toString();
                }catch (Exception e){
                    //if there is an exception it will make did it work false
                     diditwork = false;
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("error");
                    TextView tv = new TextView(this);
                    tv.setText(error + "interrupting");
                    d.setContentView(tv);
                    d.show();
                }finally {
            /*if there is no exception diditwork will still be true there a
     f        dialogue box with textview will appear*/
                    if (diditwork = true){
                        Dialog d = new Dialog(this);
                        d.setTitle("SUCCESS");
                        TextView tv = new TextView(this);
                        tv.setText("registration succesful");
                        d.setContentView(tv);
                        d.show();
                        startActivity(new Intent(Registration.this , Login.class));
                    }
                }

                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Please put on your data connection",
                    Toast.LENGTH_LONG).show();
        }return diditwork;
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

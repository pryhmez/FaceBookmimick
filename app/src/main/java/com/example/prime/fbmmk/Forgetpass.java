package com.example.prime.fbmmk;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prime.fbmmk.Helper.Databasehelper;
import com.example.prime.fbmmk.Model.Fb;
import com.example.prime.fbmmk.R;

/**
 * Created by prime on 10/29/18.
 */

public class Forgetpass extends AppCompatActivity {

    private EditText etemail, etnewpassword;
    private Button btnupdate;
    private String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpass);

        etemail = findViewById(R.id.etEmail);
        etnewpassword = findViewById(R.id.etnewPassword);
        btnupdate = findViewById(R.id.btnUpdate);

       btnupdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              if( validate()){
                  startActivity(new Intent(Forgetpass.this, Login.class));
               }
           }
       });


    }

    private boolean validate() {
        email = etemail.getText().toString().trim();
        password = etnewpassword.getText().toString().trim();

        Databasehelper db = new Databasehelper(this);
        Fb fb = new Fb();

        db.getUser(email);
        String dataEm = fb.getEmail();

        if(password.length() >= 8){
            fb.setPassword(email);
            db.updateUser(fb);
            Dialog dialog = new Dialog(this);
            dialog.setTitle("Updated");
            TextView textView = new TextView(this);
            textView.setText("password change succesfull");
        }
        return true;
    }
}

package com.ro.maparea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ro.maparea.R;

public class LoginActivity extends AppCompatActivity {

    public ImageView mSignUp,go;
    public TextView textLogin, text1;
    public EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSignUp = (ImageView) findViewById(R.id.singupbtn);
        go = (ImageView) findViewById(R.id.imggo);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(email.getText().toString(), password.getText().toString());
            }
        });



        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void validate(String email, String password){
        if((email.equals("admin@gmail.com")) && (password.equals("admin"))) {

            Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_SHORT).show();
            Intent i =new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();




        } else {


            Toast.makeText(getApplicationContext(),"Check email and password",Toast.LENGTH_SHORT).show();
        }



    }
}

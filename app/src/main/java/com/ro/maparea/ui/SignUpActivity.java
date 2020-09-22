package com.ro.maparea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ro.maparea.R;

public class SignUpActivity extends AppCompatActivity {

    public EditText name, email, password;
    public ImageView go, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.editTextTexname);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        go = (ImageView) findViewById(R.id.imggo) ;
        login = (ImageView) findViewById(R.id.singupbtn) ;

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(email.getText().toString(), password.getText().toString(),name.getText().toString());


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void validate(String email, String password, String name){
        if((email.equals("")) && (password.equals("")) && (name.equals(""))) {

            Toast.makeText(getApplicationContext(),"Enter valid inputs",Toast.LENGTH_SHORT).show();




        } else {
            Toast.makeText(getApplicationContext(),"Account Created Successfully!",Toast.LENGTH_SHORT).show();
            finish();


        }




    }
}

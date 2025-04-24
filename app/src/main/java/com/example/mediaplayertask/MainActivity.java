package com.example.mediaplayertask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Users myDB;

    Button LoginButton, SignUpButton;

    TextView Username, Password;
    SharedPreferences sharedPreferences;
    String LoggedInUser;
    String passwordCheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        myDB = new Users(MainActivity.this);
        Username = findViewById(R.id.usernameEditText);
        Password = findViewById(R.id.passwordEditText);



        LoginButton = findViewById(R.id.loginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordCheck = myDB.getPasswordForUser(Username.getText().toString());
                if (passwordCheck.equals(Password.getText().toString())) {
                    LoggedInUser = Username.getText().toString();

                    sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("LoggedInUser", LoggedInUser);
                    editor.apply();

                    Toast.makeText(MainActivity.this, LoggedInUser , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, URL_HOME.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, passwordCheck + " = " + Password.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        SignUpButton = findViewById(R.id.signUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

    }


}
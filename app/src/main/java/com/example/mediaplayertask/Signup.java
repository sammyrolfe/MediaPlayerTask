package com.example.mediaplayertask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {

    EditText FullName, Username, Password, ConfirmPassword;

    Button CreateAccount;
    SharedPreferences sharedPreferences;
    String LoggedInUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FullName = findViewById(R.id.NewFullNameEditText);
        Username = findViewById(R.id.NewUsernameEditText);
        Password = findViewById(R.id.NewPasswordEditText);
        ConfirmPassword = findViewById(R.id.ConfirmPassordEditText);
        CreateAccount = findViewById(R.id.CreateAccount);
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users  myDB = new Users(Signup.this);
                if (Password.getText().toString().equals(ConfirmPassword.getText().toString())) {
                    myDB.addUser(FullName.getText().toString().trim(), Username.getText().toString().trim(), Password.getText().toString().trim());
                    LoggedInUser = Username.getText().toString();
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Signup.this, "User created", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Signup.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
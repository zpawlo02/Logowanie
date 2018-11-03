package com.catdev.dowork.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.catdev.dowork.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    //Components
    EditText usernameInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Components
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        //Listeners
        passwordInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_ENTER) login(view);
                return false;
            }
        });

        ParseUser user = ParseUser.getCurrentUser();
        if(ParseUser.getCurrentUser() != null && user.getBoolean("emailVerified")) {
            //Log.i("admin", user.getBoolean("isAdmin") + "");
            goToHomePage();
        }
        else if(ParseUser.getCurrentUser() != null && !user.getBoolean("emailVerified")){
            ParseUser.logOut();
            Toast.makeText(LoginActivity.this, "Zweryfikuj swój adres email.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToSignup(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        if(usernameInput.getText().toString().equals("") || passwordInput.getText().toString().equals("")) {
            Toast.makeText(this, "Wszystkie pola są wymagane", Toast.LENGTH_SHORT).show();
        } else {
            ParseUser.logInInBackground(usernameInput.getText().toString(), passwordInput.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        if(user.getBoolean("emailVerified")) {
                            //Log.i("admin", user.getBoolean("isAdmin") + "");
                            goToHomePage();
                        } else {
                            ParseUser.logOut();
                            Toast.makeText(LoginActivity.this, "Zweryfikuj swój adres email.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Nie udało się zalogować. Spróbuj ponownie", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void goToHomePage() {
        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    public void goToResetPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}



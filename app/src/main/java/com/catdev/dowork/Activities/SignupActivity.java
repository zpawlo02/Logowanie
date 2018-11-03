package com.catdev.dowork.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.catdev.dowork.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    //Components
    private EditText userEmailInput;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Components
        userEmailInput = findViewById(R.id.emailInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        //Listeners
        passwordInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == KeyEvent.KEYCODE_ENTER) signup(view);
                return false;
            }
        });
    }

    public void signup(View view) {
        ParseUser user = new ParseUser();

        if(userEmailInput.getText().toString().equals("") || usernameInput.getText().toString().equals("") || passwordInput.getText().toString().equals("")) {
            Toast.makeText(this, "Wszystkie pola są wymagane", Toast.LENGTH_SHORT).show();
        } else {
            user.setEmail(userEmailInput.getText().toString());
            user.setUsername(usernameInput.getText().toString());
            user.setPassword(passwordInput.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if(e == null) {
                        ParseUser.logOut();
                        Toast.makeText(SignupActivity.this, "Zarejestrowano pomyślnie. Aktywuj swój adres email.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    else if(e != null) {
                        Toast.makeText(SignupActivity.this, "Nie udało się zarejestrować. Spróbuj ponownie.", Toast.LENGTH_SHORT).show();
                        ParseUser.logOut();
                    }
                }
            });
        }

    }
}

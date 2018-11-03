package com.catdev.dowork.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.catdev.dowork.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.List;

public class PasswordResetActivity extends AppCompatActivity {

    EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        emailInput = findViewById(R.id.emailInput);
    }

    public void resetPassword(View view) {
        Log.i("Password reset", "reset");

        final String email = emailInput.getText().toString();

        if(email.equals("")) {
            Toast.makeText(this, "Musisz wpisać adres email.", Toast.LENGTH_SHORT).show();
        }
        else {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("email", email);
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e == null) {
                        if(objects.size() == 0) {
                            Toast.makeText(PasswordResetActivity.this, "Żaden użytkownik nie ma takiego adresu email", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null) {
                                        Toast.makeText(PasswordResetActivity.this, "Sprawdź skrzynkę pocztową", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(PasswordResetActivity.this, "Błąd :( Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    else {
                        Toast.makeText(PasswordResetActivity.this, "Błąd :( Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

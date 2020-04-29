package com.pma.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pma.R;
import com.pma.dao.Database;
import com.pma.model.User;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void registrationClick(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void loginClick(View view){
        email = ((EditText)findViewById(R.id.login_email)).getText().toString();
        password = ((EditText)findViewById(R.id.login_password)).getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter credentials", Toast.LENGTH_LONG).show();
        }else{
           LoginTask task = new LoginTask();
           task.execute(email);
        }

    }

    private class LoginTask extends AsyncTask<String, Void, Void> {

        private boolean validationSuccessful = false;

        @Override
        protected Void doInBackground(String... strings) {
           User user = Database.getInstance(LoginActivity.this).userDao().findUserByEmail(strings[0]);

           if(user != null){
               if(BCrypt.checkpw(password, user.getPassword())){
                   validationSuccessful = true;
               }
           }
           return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(validationSuccessful){
                SharedPreferences preferences = getSharedPreferences("com.pma.LOGIN_PREFERENCES", Context.MODE_PRIVATE);
                preferences.edit().putString("userId", email).commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }else{
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
            }
        }
    }
}

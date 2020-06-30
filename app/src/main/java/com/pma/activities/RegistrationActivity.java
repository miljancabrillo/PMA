package com.pma.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pma.R;
import com.pma.dao.Database;
import com.pma.dao.UserDao;
import com.pma.model.User;

import org.mindrot.jbcrypt.BCrypt;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
    }

    public void registerClick(View view) {

        String email = ((EditText)findViewById(R.id.registration_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.registration_password)).getText().toString();
        String height = ((EditText)findViewById(R.id.registration_height)).getText().toString();
        String weight = ((EditText)findViewById(R.id.registration_weight)).getText().toString();
        String age = ((EditText)findViewById(R.id.registration_age)).getText().toString();

        boolean male = true;
        int selectedGender = ((RadioGroup)findViewById(R.id.radio_buttons)).getCheckedRadioButtonId();
        if(selectedGender == R.id.female_radio_button) male = false;

        if(email.equals("") || !Patterns.EMAIL_ADDRESS.matcher((CharSequence)email).matches()){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show();
            return;
        }

        if(password.equals("") || password.length()<5){
            Toast.makeText(this, "Password too short", Toast.LENGTH_LONG).show();
            return;
        }

        if(weight.equals("")){
            Toast.makeText(this, "Invalid weight", Toast.LENGTH_LONG).show();
            return;
        }

        if(height.equals("")){
            Toast.makeText(this, "Invalid height", Toast.LENGTH_LONG).show();
            return;
        }

        if(age.equals("")){
            Toast.makeText(this, "Invalid age", Toast.LENGTH_LONG).show();
            return;
        }

        User user =  new User(email, password, Float.parseFloat(weight), Float.parseFloat(height), Integer.parseInt(age),false, male);
        AddUserTask task = new AddUserTask();
        task.execute(user);

    }

    private class AddUserTask extends AsyncTask<User, Void, Boolean>{

        @Override
        protected Boolean doInBackground(User... users) {
            User u = Database.getInstance(RegistrationActivity.this).userDao().findUserByEmail(users[0].getEmail());
            if(u != null) return false;

            users[0].setPassword(BCrypt.hashpw(users[0].getPassword(),BCrypt.gensalt()));
            Database.getInstance(RegistrationActivity.this).userDao().insert(users[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result) {
                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                ((EditText) findViewById(R.id.registration_email)).setText("");
                ((EditText) findViewById(R.id.registration_password)).setText("");
                ((EditText) findViewById(R.id.registration_height)).setText("");
                ((EditText) findViewById(R.id.registration_weight)).setText("");
                ((EditText) findViewById(R.id.registration_age)).setText("");
            }else{
                Toast.makeText(RegistrationActivity.this, "Mail already exist", Toast.LENGTH_LONG).show();
            }
        }
    }
}

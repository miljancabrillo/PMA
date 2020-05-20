package com.pma.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.R;
import com.pma.dao.Database;
import com.pma.model.User;

import org.mindrot.jbcrypt.BCrypt;


public class ProfileFragment extends Fragment {

    private MutableLiveData<User> user = new MutableLiveData<>();
    private String loggedUser;

    private EditText height;
    private EditText weight;
    private EditText age;
    private EditText oldPassword;
    private EditText newPassword;

    private boolean validationSuccessful = false;
    private boolean passChanged = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("com.pma.LOGIN_PREFERENCES", Context.MODE_PRIVATE);
        loggedUser = preferences.getString("userId", "");

        height = root.findViewById(R.id.height);
        weight = root.findViewById(R.id.weight);
        age = root.findViewById(R.id.Age);
        oldPassword = root.findViewById(R.id.Password);
        newPassword = root.findViewById(R.id.newPassword);

        final ProfileTask task = new ProfileTask();
        task.execute(loggedUser);

        user.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                height.setText(String.valueOf(user.getHeight()), TextView.BufferType.EDITABLE);
                weight.setText(String.valueOf(user.getWeight()), TextView.BufferType.EDITABLE);
                age.setText(String.valueOf(user.getAge()), TextView.BufferType.EDITABLE);
            }
        });

        root.findViewById(R.id.add_grocery_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileHeight = height.getText().toString();
                String profileWeight = weight.getText().toString();
                String profileAge = age.getText().toString();
                String profileNewPass = newPassword.getText().toString();
                String profileOldPass = oldPassword.getText().toString();

                if(BCrypt.checkpw(profileOldPass, user.getValue().getPassword())){
                    validationSuccessful = true;
                }

                if(profileWeight.equals("")){
                    Toast.makeText(getActivity(), "Invalid weight", Toast.LENGTH_LONG).show();
                    return;
                }

                if(profileHeight.equals("")){
                    Toast.makeText(getActivity(), "Invalid height", Toast.LENGTH_LONG).show();
                    return;
                }

                if(profileAge.equals("")){
                    Toast.makeText(getActivity(), "Invalid age", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!profileOldPass.equals(""))
                    passChanged = true;

                if(!(profileNewPass.equals("")) && profileNewPass.length()>=5){
                    if(validationSuccessful) {
                        user.getValue().setPassword(BCrypt.hashpw(profileNewPass,BCrypt.gensalt()));
                        user.getValue().setWeight(Float.parseFloat(profileWeight));
                        user.getValue().setHeight(Float.parseFloat(profileHeight));
                        user.getValue().setAge(Integer.parseInt(profileAge));
                        validationSuccessful = false;
                    }
                    else {
                        Toast.makeText(getActivity(), "Invalid old password", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                else if(profileNewPass.equals("")) {
                    user.getValue().setWeight(Float.parseFloat(profileWeight));
                    user.getValue().setHeight(Float.parseFloat(profileHeight));
                    user.getValue().setAge(Integer.parseInt(profileAge));
                }
                else {
                    Toast.makeText(getActivity(), "Password too short", Toast.LENGTH_LONG).show();
                    return;
                }

                UpdateProfileTask task1 = new UpdateProfileTask();
                task1.execute(user.getValue());
            }
        });

        return root;
    }

    private class ProfileTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            User userPom = Database.getInstance(getActivity()).userDao().findUserByEmail(strings[0]);
            user.postValue(userPom);
            return null;
        }
    }

    private class UpdateProfileTask extends  AsyncTask<User, Void, Void>{
        @Override
        protected Void doInBackground(User... users) {
            Database.getInstance(getActivity()).userDao().update(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(passChanged){
                Toast.makeText(getActivity(), "Update profile successful", Toast.LENGTH_LONG).show();
                oldPassword.getText().clear();
                newPassword.getText().clear();
            }
            else
                Toast.makeText(getActivity(), "Update profile successful", Toast.LENGTH_LONG).show();
        }
    }
}

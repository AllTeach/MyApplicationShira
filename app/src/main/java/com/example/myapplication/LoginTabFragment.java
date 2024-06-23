package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class LoginTabFragment extends Fragment {

private  View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login_tab, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        EditText etMailLog = view.findViewById(R.id.login_email);
        EditText etPasswordLog = view.findViewById(R.id.login_password);
        Button btnLog = view.findViewById(R.id.login_buttom);



        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etMailLog.getText()) ||
                        TextUtils.isEmpty(etPasswordLog.getText())) {
                    // one of them is empty
                    Toast.makeText(getContext(), "You haven't fill all input fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // user has clicked the login button
                String email = etMailLog.getText().toString();
                String password = etPasswordLog.getText().toString();


                // Check if the email and password are in the SQLITE database
                // update shared preferences with email of this user
                // move to the profile activity
                UserDetailsDBHelper dbHelper = new UserDetailsDBHelper(getContext());

             UserDetails user = dbHelper.getUser(email);
                if (user != null) {
                    // this means email and password are the same
                    if(user.getPassword().equals(password))
                    {
                        // User exists
                        // Save the user's email in shared preferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("details", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("KEY_USER_EMAIL", email);
                        editor.apply();

                        // Move to the profile activity
                        ((StartActivity) getActivity()).moveToProfileActivity();
                    }
                    else
                        Toast.makeText(getContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();

                }
                    else
                    {
                        Toast.makeText(getContext(), "no such email", Toast.LENGTH_SHORT).show();
                    }
                    // User exists
                    // Save the user's email in shared preferences


            }
        });
        }
    }

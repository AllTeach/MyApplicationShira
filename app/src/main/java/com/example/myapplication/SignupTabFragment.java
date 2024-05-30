package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SignupTabFragment extends Fragment {


    private ImageView peopleIcon;
    private Thread animationThread;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_signup_tab, container, false);
    return view;
    }
    private UserDetailsDBHelper dbHelper;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // EditText etConfirmLog = view.findViewById(R.id.login_confirm);

        EditText etMailSign = view.findViewById(R.id.signup_email);
        EditText etPasswordSign = view.findViewById(R.id.signup_password);
        EditText etConfirmSign = view.findViewById(R.id.signup_confirm);
        Button btnSign = view.findViewById(R.id.signup_buttom);

        peopleIcon = view.findViewById(R.id.peopleicons);

        startCircularAnimation();



        dbHelper = new UserDetailsDBHelper(getContext());

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etMailSign.getText() )||
                        TextUtils.isEmpty(etPasswordSign.getText())||
                        TextUtils.isEmpty(etConfirmSign.getText()) )
                {
                    // one of them is empty
                    Toast.makeText(getContext(), "You haven't fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // user has clicked the signin button
                String email = etMailSign.getText().toString();
                String password = etPasswordSign.getText().toString();

                // check if the password and confirm password are the same
                if(!password.equals(etConfirmSign.getText().toString()))
                {
                    Toast.makeText(getContext(), "Password and confirm password are not the same", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveUserEmail(email);



                String[] powers = {"arrows", "rocket", "boomb", "fireball"};
                int coins = 0;
                int level = 1;

                // enter to DB the new user
                UserDetails newUser = new UserDetails(email, password, powers, coins, level);
                dbHelper.addUser(newUser);

                // move to profile fragment
                ((StartActivity)getActivity()).moveToProfileActivity();


            }
        });
        //  String email = etMail.getText().toString();

     //   Toast.makeText(getActivity(),"singup email is " + email,Toast.LENGTH_LONG).show();

    }

    private void startCircularAnimation() {
        final int radius = 100;
        final int duration = 10000;
        final long frameDuration = 1000 / 25; // 25 FPS

        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {


                    for (int t = 0; t < duration; t += frameDuration) {
                        final double angle = 2 * Math.PI * t / duration;
                        final int x = (int) (radius * Math.sin(angle));
                        final int y = (int) (radius * Math.cos(angle));

                        peopleIcon.post(new Runnable() {
                            @Override
                            public void run() {
                                peopleIcon.setTranslationX(x);
                                peopleIcon.setTranslationY(y);
                            }
                        });

                        try {
                            Thread.sleep(frameDuration);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });
        animationThread.start();
    }

    public void saveUserEmail(String email) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_USER_EMAIL", email);
        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (animationThread != null) {
            animationThread.interrupt();
        }
    }
}
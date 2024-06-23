package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class FinancialMeansFragment extends Fragment {

    private View view;

    private int selectedCost = 0;


    private UserDetails userDetails=null;

    ImageView iv0;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;

    TextView tvCoins, tvLevel;

    ImageView selectednewPowerView;
    int selectedReloading = 0, selectedDamage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_financial_means, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                // Update UI on the UI thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Your UI update code here
                        openPowers();
                    }
                });
            }
        }).start();


 */


    }


    @Override
    public void onResume() {
        super.onResume();
        // Read powers from DB
        // set the powers to the image views
        UserDetailsDBHelper dbHelper = new UserDetailsDBHelper(getContext());

        String email = getUserEmail();

        userDetails = dbHelper.getUser(email);



        // relate to current powers
        iv0 = view.findViewById(R.id.ivPower0);
        iv1 = view.findViewById(R.id.ivPower1);
        iv2 = view.findViewById(R.id.ivPower2);
        iv3 = view.findViewById(R.id.ivPower3);
        tvCoins = view.findViewById(R.id.financialmeans_coins);
        tvLevel = view.findViewById(R.id.financialmeans_level);


        tvCoins.setText("Coins: " + userDetails.getCoins());
        tvLevel.setText("Level: " + userDetails.getLevel());
        setPowers(userDetails.getPowers());


        setPowerListeners();
        openPowers();


    }


    private void refreshUserInfo(UserDetails userDetails)
    {
        tvCoins.setText("Coins: " + userDetails.getCoins());
        tvLevel.setText("Level: " + userDetails.getLevel());

    }

    private void setPowers(String[] powers) {

// set the powers to the image views
        // set the powers to the image views

        // update accordingly!!!
        // put each damage in AppConsts

        for (int i = 0; i < powers.length; i++) {

            if(powers[i].equals("arrows"))
                AppConstant.currPowers[i] = new Powers(10, 2, BitmapFactory.decodeResource(getResources(), R.drawable.arrows));
            else if(powers[i].equals("rocket"))
                AppConstant.currPowers[i] = new Powers(20, 3, BitmapFactory.decodeResource(getResources(), R.drawable.rocket));
            else if(powers[i].equals("boomb"))
                AppConstant.currPowers[i] = new Powers(30, 4, BitmapFactory.decodeResource(getResources(), R.drawable.boomb));
            else if(powers[i].equals("fireball"))
                AppConstant.currPowers[i] = new Powers(40, 5, BitmapFactory.decodeResource(getResources(), R.drawable.fireball));
            else if(powers[i].equals("rock"))
                AppConstant.currPowers[i] = new Powers(50, 0, BitmapFactory.decodeResource(getResources(), R.drawable.rock));
            else if(powers[i].equals("randanker"))
                AppConstant.currPowers[i] = new Powers(60, 0, BitmapFactory.decodeResource(getResources(), R.drawable.randanker));
        }


        iv0.setImageBitmap(AppConstant.currPowers[0].getBitmap());
        iv1.setImageBitmap(AppConstant.currPowers[1].getBitmap());
        iv2.setImageBitmap(AppConstant.currPowers[2].getBitmap());
        iv3.setImageBitmap(AppConstant.currPowers[3].getBitmap());




    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("details", Context.MODE_PRIVATE);
        return sharedPreferences.getString("KEY_USER_EMAIL", null);
    }

    private void setPowerListeners() {
        iv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv0.getDrawable();
                iv0.setImageDrawable(selectednewPowerView.getDrawable());

                selectednewPowerView.setImageDrawable(d);

                // check powers and damage

                AppConstant.currPowers[0] = new Powers(selectedDamage, selectedReloading, ((BitmapDrawable) iv0.getDrawable()).getBitmap());
                // update the the new power - inside  AppConstant.currPowers[0] in user details and in Database
               String powerName = getNameOfPower(AppConstant.currPowers[0]);

               // update the new power in user details - in index 0 and update the DB
                UserDetailsDBHelper dbHelper = new UserDetailsDBHelper(getContext());
                String email = getUserEmail();
                UserDetails userDetails = dbHelper.getUser(email);
                userDetails.getPowers()[0] = powerName;
                userDetails.setCoins(userDetails.getCoins() - selectedCost);
                dbHelper.updateUser(userDetails);
                refreshUserInfo(userDetails);

            }
        });

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv1.getDrawable();
                iv1.setImageDrawable(selectednewPowerView.getDrawable());

            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv2.getDrawable();
                iv2.setImageDrawable(selectednewPowerView.getDrawable());

            }
        });


        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv3.getDrawable();
                iv3.setImageDrawable(selectednewPowerView.getDrawable());

            }
        });


    }

    private String getNameOfPower(Powers currPower) {


        Bitmap current = currPower.getBitmap();

        Drawable compareDrawable = ContextCompat.getDrawable(getContext(), R.drawable.randanker);
        Bitmap compareBitmap = ((BitmapDrawable) compareDrawable).getBitmap();

        if (current.sameAs(compareBitmap)) {
            return "randanker";
        }
       return "";
    }


    public void openPowers() // when ur razing a level
    {
        if(userDetails == null)
            return;
        int level = userDetails.getLevel();//2;// get level
        int coins = userDetails.getCoins();//250; // get coins
        int numOfCoinsForThePower;
        if (level >= 1) {
            numOfCoinsForThePower = selectedCost; //????FIX!!
            if(coins<numOfCoinsForThePower)
                return;
            TextView tvLock2 = view.findViewById(R.id.tvLock2);
            tvLock2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

            ImageView ivSelectLevel2 = view.findViewById(R.id.ivLevel2);
            ivSelectLevel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Use the Builder class for convenient dialog construction.
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Do you want to buy this power?\n loading is: 164\n  damage is:8")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
/*
                                    if (coins < numOfCoinsForThePower) {
                                        Toast.makeText(getContext(), "You don't have enough coins for this power", Toast.LENGTH_SHORT).show();
                                    } else {

 */
                                    //         coins = coins - numOfCoinsForThePower;
                                    tvLock2.setVisibility(View.INVISIBLE);
                                    selectednewPowerView = ivSelectLevel2;
                                    selectedDamage = 164;
                                    selectedReloading = 8;

                                    selectedCost = 700;
                                                }
                                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // User cancels the dialog.
                                                }
                                            });


                                        // Create the AlertDialog object and return it.

                                        AlertDialog adialog = builder.create();
                                        adialog.show();
                                    }

                             //   }
                            });
                }

            }


        }


     //   Drawable lock = getResources().getDrawable(R.drawable.ic_baseline_lock_24);
       // lock.setImageDrawable(null);




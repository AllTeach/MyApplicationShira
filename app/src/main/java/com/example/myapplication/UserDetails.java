package com.example.myapplication;

public class UserDetails {

    private String email;
    private String password;
    private String[] powers = new String[4];
    private int coins;
    private int level;

    // Default constructor
    public UserDetails() {
    }

    // Full constructor
    public UserDetails(String email, String password, String[] powers, int coins, int level) {
        this.email = email;
        this.password = password;
        this.powers = powers;
        this.coins = coins;
        this.level = level;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getPowers() {
        return powers;
    }

    public void setPowers(String[] powers) {
        this.powers = powers;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}


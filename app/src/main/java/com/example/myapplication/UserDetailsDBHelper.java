package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDetailsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userDetails.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "userDetails";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_POWER1 = "power1";
    private static final String COLUMN_POWER2 = "power2";
    private static final String COLUMN_POWER3 = "power3";
    private static final String COLUMN_POWER4 = "power4";
    private static final String COLUMN_COINS = "coins";
    private static final String COLUMN_LEVEL = "level";

    public UserDetailsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_POWER1 + " TEXT,"
                + COLUMN_POWER2 + " TEXT,"
                + COLUMN_POWER3 + " TEXT,"
                + COLUMN_POWER4 + " TEXT,"
                + COLUMN_COINS + " INTEGER,"
                + COLUMN_LEVEL + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(UserDetails user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_POWER1, user.getPowers()[0]);
        values.put(COLUMN_POWER2, user.getPowers()[1]);
        values.put(COLUMN_POWER3, user.getPowers()[2]);
        values.put(COLUMN_POWER4, user.getPowers()[3]);
        values.put(COLUMN_COINS, user.getCoins());
        values.put(COLUMN_LEVEL, user.getLevel());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }



    public UserDetails getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_EMAIL,
                        COLUMN_PASSWORD, COLUMN_POWER1, COLUMN_POWER2, COLUMN_POWER3, COLUMN_POWER4, COLUMN_COINS, COLUMN_LEVEL}, COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);


        if (cursor != null)
            cursor.moveToFirst();
        if(cursor.getCount() == 0)
            return null;
        UserDetails user = new UserDetails();
        user.setEmail(cursor.getString(0));
        user.setPassword(cursor.getString(1));
        user.setPowers(new String[]{cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)});
        user.setCoins(cursor.getInt(6));
        user.setLevel(cursor.getInt(7));

        return user;
    }

    public int updateUser(UserDetails user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_POWER1, user.getPowers()[0]);
        values.put(COLUMN_POWER2, user.getPowers()[1]);
        values.put(COLUMN_POWER3, user.getPowers()[2]);
        values.put(COLUMN_POWER4, user.getPowers()[3]);
        values.put(COLUMN_COINS, user.getCoins());
        values.put(COLUMN_LEVEL, user.getLevel());

        return db.update(TABLE_NAME, values, COLUMN_EMAIL + " = ?",
                new String[]{user.getEmail()});
    }
}
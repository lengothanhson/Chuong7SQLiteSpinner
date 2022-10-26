package com.example.chuong7sqlitespinner;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    // database
    public static final String DATABASE_NAME = "db-user";
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_AVATAR = "avatar";
    public static final String COLUMN_USER_DEPARTID = "departid";
    // Department

    public static final String TABLE_DEPARTMENT = "department";
    public static final String COLUMN_DEPA_ID = "id";
    public static final String COLUMN_DEPA_NAME = "name";

    // function
    public static Bitmap convertToBitmapFromAssets(Context context, String nameImage)
    {
        AssetManager assetManager= context.getAssets();
        try {
            InputStream inputStream=assetManager.open("images/"+nameImage);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
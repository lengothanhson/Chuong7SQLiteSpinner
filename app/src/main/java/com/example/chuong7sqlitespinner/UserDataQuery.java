package com.example.chuong7sqlitespinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UserDataQuery {
    // thÃªm má»›i 1 user
    public static long insert(Context context, User us)
    {
        UserDBHelper userDBHelper= new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase= userDBHelper.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Utils.COLUMN_USER_DEPARTID,us.departid);
        values.put(Utils.COLUMN_USER_NAME,us.name);
        values.put(Utils.COLUMN_USER_AVATAR,us.avatar);
        long rs= sqLiteDatabase.insert(Utils.TABLE_USER,null,values);
        return (rs);
    }
    // lay danh sach
    public static ArrayList<User> getAll(Context context)
    {
        ArrayList<User> lstUser= new ArrayList<>();
        UserDBHelper userDBHelper= new UserDBHelper(context);
        SQLiteDatabase db=userDBHelper.getReadableDatabase();
        String strReadUser= "select us.*, de.name as departname from user us left join department de on us.departid= de.id" ;
        // Cursor cs= db.rawQuery("Select * from "+Utils.TABLE_USER,null);
        Cursor cs= db.rawQuery(strReadUser,null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id= cs.getInt(0);
            String name= cs.getString(1);
            String avatar= cs.getString(2);
            User item= new User(id,name,avatar);
            item.departid= cs.getInt(3);
            item.departname= cs.getString(4);
            lstUser.add(item);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstUser;

    }
    // xoa item
    public static boolean delete (Context context, int id)
    {
        UserDBHelper userDBHelper= new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase= userDBHelper.getWritableDatabase();
        int rs= sqLiteDatabase.delete(Utils.TABLE_USER,Utils.COLUMN_USER_ID+"=?", new String[]{String.valueOf(id)});
        return (rs>0);
    }
    // cáº­p nháº­t
    public static int update(Context context, User us)
    {
        UserDBHelper userDBHelper= new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase= userDBHelper.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Utils.COLUMN_USER_NAME,us.getName());
        values.put(Utils.COLUMN_USER_AVATAR,us.getAvatar());
        values.put(Utils.COLUMN_USER_DEPARTID,us.getDepartid());
        int rs= sqLiteDatabase.update(Utils.TABLE_USER,values,Utils.COLUMN_USER_ID+"=?",new String[]{String.valueOf(us.id)});
        return  (rs);
    }
}
package com.example.dictionary;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    public static final String DB_NAME_EV="anh_viet.db";
    public static final String DB_NAME_VE="viet_anh.db";
    private static final int DB_VERSION=1;
    public DatabaseOpenHelper(Context context, String DB_NAME){
        super(context, DB_NAME, null, DB_VERSION);
    }
}

package com.example.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    public ArrayList<MyWord> dict = new ArrayList<>();
    public ArrayList<String> listwords = new ArrayList<>();
    public final static String favouriteDB = "Favourite";
    public final static String personalDB = "Personal";

    private static String DB_TABLE;
    public static DatabaseAccess instance;

    private DatabaseAccess(Context context, String DB_NAME) {
        this.openHelper = new DatabaseOpenHelper(context, DB_NAME);
        this.DB_TABLE = DB_NAME.replace(".db","");

    }

    public static DatabaseAccess getInstance(Context context, String DB_NAME) {
        if (instance == null || !DatabaseAccess.DB_TABLE.equals(DB_NAME.replace(".db",""))) {
            instance = new DatabaseAccess(context, DB_NAME);
        }
        return instance;
    }

    public static ArrayList<MyWord> getRandomList(Context context) {
        DatabaseAccess tempInstance = new DatabaseAccess(context,"anh_viet.db");
        tempInstance.open();
        Random random = new Random();
        ArrayList<Integer> nums = new ArrayList<Integer>();
        ArrayList<MyWord> result = new ArrayList<MyWord>();
        for(int i = 0;i<10;i++) {
            nums.add(random.nextInt(35001));
        }
        for (int i: nums) {
            Cursor cursor = tempInstance.database.rawQuery("Select * from "+ DB_TABLE +"  where id = " + i, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result.add(new MyWord(String.valueOf(cursor.getInt(0)),
                        cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return result;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
        String scriptFavor = "";
        switch (this.DB_TABLE) {
            case "anh_viet":
            {
                scriptFavor = "CREATE TABLE IF NOT EXISTS \"Favourite\" (\n" +
                        "\t\"id\"\tINTEGER NOT NULL,\n" +
                        "\tPRIMARY KEY(\"id\"),\n" +
                        "\tCONSTRAINT \"fk_id\" FOREIGN KEY(\"id\") REFERENCES \"anh_viet\"(\"id\")\n" +
                        ")";
            }break;
            case "viet_anh":
            {
                scriptFavor = "CREATE TABLE IF NOT EXISTS \"Favourite\" (\n" +
                        "\t\"id\"\tINTEGER NOT NULL,\n" +
                        "\tPRIMARY KEY(\"id\"),\n" +
                        "\tCONSTRAINT \"fk_id\" FOREIGN KEY(\"id\") REFERENCES \"viet_and\"(\"id\")\n" +
                        ")";
            }
        }
        String scriptOwn = "CREATE TABLE IF NOT EXISTS \"Personal\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL,\n" +
                "\t\"word\"\tTEXT NOT NULL,\n" +
                "\t\"content\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ")";
        database.execSQL(scriptFavor);
        database.execSQL(scriptOwn);
    }

    public void close() {
        if (database != null)
            this.database.close();
    }

    public ArrayList<MyWord> getWordsOffset(int id, int offset) {
        Cursor cursor = database.rawQuery("select * from "+ DB_TABLE +
                " WHERE id >= " + id + " limit " + offset, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dict.add(new MyWord(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2)));
            listwords.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return dict;
    }

    public String getWordsById(int id) {
        String str = "";
        Cursor cursor = database.rawQuery("Select * from "+ DB_TABLE +"  where id = " + id, null);
        cursor.moveToFirst();
        str = cursor.getString(1);
        cursor.close();
        return str;
    }

    public ArrayList<String> getWordsStartWith(String str) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from "+ DB_TABLE +
                " WHERE word like " + "'" + str + "%'" + " limit 10", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> getListWord(ArrayList<MyWord> dict) {
        return listwords;
    }
    public boolean isFavourite(int wordId) {
        open();
        boolean result = false;
        Cursor cursor = database.rawQuery("select * from " + favouriteDB
                + " where id = " + wordId, null);
        if (cursor.getCount() == 1) {
            result = true;
        }
        cursor.close();
        close();
        return result;
    }
    public boolean addToFavoriteDict(int wordId) {
        boolean signal = false;
        try {
            open();
            ContentValues value = new ContentValues();
            value.put("id",wordId);
            database.insert(favouriteDB,null,value);
            close();
            signal = !signal;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            return  signal;
        }
    }

    public ArrayList<MyWord> getFavouriteWord(int position,int offset) {
        open();
        ArrayList<MyWord> result = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + DB_TABLE + " a inner join "
                + favouriteDB + " b on a.id=b.id where b.id > "+ position + " limit " + offset, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(new MyWord(String.valueOf(cursor.getInt(0)),
                    cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return result;
    }
}

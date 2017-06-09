package com.baroghel.juliecentre.datastore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by User on 08/05/2017.
 */

public class DataBaseSQLite extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.baroghel.juliecentre/databases/";
    private static String DB_NAME = "newsdatabase.db";

    private SQLiteDatabase newsDataBase;
    private Context context;

    public DataBaseSQLite(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase(){
        boolean isExist = isDataBaseExist();
        if(!isExist){
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean isDataBaseExist(){
        SQLiteDatabase database = null;

        try{
            String path = DB_PATH + DB_NAME;
            database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){
            return false;
        }

        if(database != null)
            database.close();

        return true;
    }

    private void copyDataBase() throws IOException{
        InputStream inputStream = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0)
            outputStream.write(buffer, 0, length);

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public SQLiteDatabase openDataBaseToRead(){
        String path = DB_PATH + DB_NAME;
        SQLiteDatabase newsDataBase = SQLiteDatabase.openDatabase(path, null , SQLiteDatabase.OPEN_READONLY);
        return newsDataBase;
    }

    public SQLiteDatabase openDataBaseToWrite(){
        String path = DB_PATH + DB_NAME;
        SQLiteDatabase newsDataBase = SQLiteDatabase.openDatabase(path, null , SQLiteDatabase.OPEN_READWRITE);
        return newsDataBase;
    }

    @Override
    public synchronized void close(){
        if(newsDataBase != null)
            newsDataBase.close();
        super.close();
    }
}

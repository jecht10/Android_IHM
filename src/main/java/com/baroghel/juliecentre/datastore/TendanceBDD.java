package com.baroghel.juliecentre.datastore;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baroghel.juliecentre.data.TendanceNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/05/2017.
 */

public class TendanceBDD {
    private DataBaseSQLite dataBaseSQLite;
    private SQLiteDatabase bdd;

    private static final String TABLE_TYPE = "table_type";

    private static final String COL_ID = "_id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 1;

    public TendanceBDD(DataBaseSQLite dataBaseSQLite){
        this.dataBaseSQLite = dataBaseSQLite;
        this.bdd = dataBaseSQLite.openDataBaseToRead();
    }

    public void close(){
        bdd.close();
    }

    public List<TendanceNews> getAllTendances(){
        Cursor c = bdd.query(TABLE_TYPE, new String[]{COL_ID, COL_NAME}, null, null, null, null,
                COL_NAME);
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        List<TendanceNews> tendanceNewsList = new ArrayList<>();
        while (c.moveToNext()){
            tendanceNewsList.add(new TendanceNews(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NAME)));
        }
        c.close();
        return tendanceNewsList;
    }
}

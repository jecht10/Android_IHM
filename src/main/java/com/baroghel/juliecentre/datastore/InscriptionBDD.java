package com.baroghel.juliecentre.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baroghel.juliecentre.reservations.ReservationListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25/05/2017.
 */

public class InscriptionBDD {
    private DataBaseSQLite dataBaseSQLite;
    private SQLiteDatabase bdd;

    private static final String TABLE_NEWS_INSCRIT = "table_news_inscrit";

    private static final String COL_ID = "_id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NEWS = "NEWS";
    private static final int NUM_COL_NEWS = 1;
    private static final String COL_DATE = "DATE";
    private static final int NUM_COL_DATE = 2;
    private static final String COL_HOUR = "HOUR";
    private static final int NUM_COL_HOUR = 3;

    public InscriptionBDD(DataBaseSQLite dataBaseSQLite){
        this.dataBaseSQLite = dataBaseSQLite;
        this.bdd = dataBaseSQLite.openDataBaseToWrite();
    }

    public void close(){
        this.bdd.close();
    }

    public boolean isNewsAlreadySubscribed(int id){
        Cursor c = bdd.query(TABLE_NEWS_INSCRIT, new String[]{COL_ID, COL_NEWS}, COL_NEWS + " = " + id, null, null, null,
                null);
        int count = c.getCount();
        c.close();
        return count != 0;
    }

    public long insertNews(int id, String date, String heure){
        ContentValues content = new ContentValues();
        content.put(COL_NEWS, id);
        content.put(COL_DATE, date);
        content.put(COL_HOUR, heure);
        return bdd.insert(TABLE_NEWS_INSCRIT, null, content);
    }

    public int removeNews(int id){
        return bdd.delete(TABLE_NEWS_INSCRIT, COL_NEWS + " = " + id, null);
    }

    //TODO Tri par ordre chronologique
    public List<ReservationListItem> getAllReservations(){
        Cursor c = bdd.query(TABLE_NEWS_INSCRIT, new String[]{COL_ID, COL_NEWS, COL_DATE, COL_HOUR}, null,
                null, null, null, COL_DATE);
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        List<ReservationListItem> reservationListItems = new ArrayList<>();
        while (c.moveToNext()){
            reservationListItems.add(
                    new ReservationListItem(c.getInt(NUM_COL_NEWS), c.getString(NUM_COL_DATE), c.getString(NUM_COL_HOUR)));
        }
        c.close();
        return reservationListItems;
    }
}

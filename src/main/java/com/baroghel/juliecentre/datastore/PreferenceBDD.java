package com.baroghel.juliecentre.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baroghel.juliecentre.data.ShopNews;
import com.baroghel.juliecentre.data.TendanceNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/05/2017.
 */

public class PreferenceBDD {
    private static final String TABLE_PREF_TYPE = "table_pref_type";
    private static final String TABLE_PREF_SHOP = "table_pref_shop";
    private static final String TABLE_TYPE = "table_type";
    private static final String TABLE_SHOP = "table_shop";
    private static final String COL_ID = "_id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TYPE = "TYPE";
    private static final int NUM_COL_TYPE = 1;
    private static final String COL_SHOP = "SHOP";
    private static final int NUM_COL_SHOP = 1;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 1;
    private DataBaseSQLite dataBaseSQLite;
    private SQLiteDatabase bdd;
    private SQLiteDatabase myDataBase;

    public PreferenceBDD(DataBaseSQLite dataBaseSQLite) {
        this.dataBaseSQLite = dataBaseSQLite;
        this.bdd = dataBaseSQLite.openDataBaseToWrite();
    }

    public void close() {
        this.bdd.close();
    }

    public long insertTypePreference(int id) {
        ContentValues content = new ContentValues();
        content.put(COL_TYPE, id);
        return bdd.insert(TABLE_PREF_TYPE, null, content);
    }

    public long insertShopPreference(int id) {
        ContentValues content = new ContentValues();
        content.put(COL_SHOP, id);
        return bdd.insert(TABLE_PREF_SHOP, null, content);
    }

    public int removeTypePreference(int id) {
        return bdd.delete(TABLE_PREF_TYPE, COL_TYPE + " = " + id, null);
    }

    public int removeShopPreference(int id) {
        return bdd.delete(TABLE_PREF_SHOP, COL_SHOP + " = " + id, null);
    }

    public List<TendanceNews> getAllTendancePreferences() {
        Cursor c = bdd.query(TABLE_PREF_TYPE, new String[]{COL_ID, COL_TYPE}, null, null, null, null,
                COL_TYPE);
        if (c.getCount() == 0) {
            c.close();
            return new ArrayList<>();
        }

        TendanceBDD tendanceBDD = new TendanceBDD(dataBaseSQLite);
        List<TendanceNews> allTendance = tendanceBDD.getAllTendances();
        tendanceBDD.close();
        List<TendanceNews> tendancePrefs = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(NUM_COL_TYPE);
            for (TendanceNews tendance : allTendance) {
                if (tendance.getId() == id) {
                    tendancePrefs.add(tendance);
                    break;
                }
            }
        }
        c.close();
        return tendancePrefs;
    }

    public List<ShopNews> getAllShopPreferences() {
        Cursor c = bdd.query(TABLE_PREF_SHOP, new String[]{COL_ID, COL_SHOP}, null, null, null, null,
                COL_SHOP);
        if (c.getCount() == 0) {
            c.close();
            return new ArrayList<>();
        }

        ShopBDD shopBDD = new ShopBDD(dataBaseSQLite);
        List<ShopNews> allShop = shopBDD.getAllShops();
        shopBDD.close();
        List<ShopNews> shopPrefs = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(NUM_COL_SHOP);
            for (ShopNews shop : allShop) {
                if (shop.getId() == id) {
                    shopPrefs.add(shop);
                    break;
                }
            }
        }
        c.close();
        return shopPrefs;
    }
}

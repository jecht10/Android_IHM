package com.baroghel.juliecentre.datastore;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baroghel.juliecentre.data.ShopNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/05/2017.
 */

public class ShopBDD {
    private DataBaseSQLite dataBaseSQLite;
    private SQLiteDatabase bdd;

    private static final String TABLE_SHOP = "table_shop";

    private static final String COL_ID = "_id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_IMG = "IMG";
    private static final int NUM_COL_IMG = 2;
    private static final String COL_DESC = "DESC";
    private static final int NUM_COL_DESC = 3;
    private static final String COL_HORAIRE = "HORAIRE";
    private static final int NUM_COL_HORAIRE = 4;
    private static final String COL_NUM = "NUM";
    private static final int NUM_COL_NUM = 5;

    public ShopBDD(DataBaseSQLite dataBaseSQLite){
        this.dataBaseSQLite = dataBaseSQLite;
        this.bdd = dataBaseSQLite.openDataBaseToRead();
    }

    public void close(){
        bdd.close();
    }

    public ShopNews getShopWithId(int id){
        Cursor c = bdd.query(TABLE_SHOP, new String[]{COL_ID, COL_NAME, COL_IMG, COL_DESC, COL_HORAIRE,
        COL_NUM}, COL_ID + " = " + id, null, null, null, null);
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        c.moveToNext();
        ShopNews shopNews = new ShopNews(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NAME), c.getString(NUM_COL_IMG),
                c.getString(NUM_COL_DESC), c.getString(NUM_COL_HORAIRE), c.getString(NUM_COL_NUM));
        c.close();
        return shopNews;
    }

    public List<ShopNews> getAllShops(){
        Cursor c = bdd.query(TABLE_SHOP, new String[]{COL_ID, COL_NAME}, null, null, null, null,
                COL_NAME);
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        List<ShopNews> shopNewsList = new ArrayList<>();
        while (c.moveToNext()){
            shopNewsList.add(new ShopNews(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NAME)));
        }
        c.close();
        return shopNewsList;
    }

    public List<ShopNews> getAllShopsWithAllInformations(){
        Cursor c = bdd.query(TABLE_SHOP, new String[]{COL_ID, COL_NAME, COL_IMG, COL_DESC, COL_HORAIRE, COL_NUM},
                null, null, null, null, COL_NAME);
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        List<ShopNews> allShop = new ArrayList<>();
        while(c.moveToNext()){
            allShop.add(new ShopNews(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NAME), c.getString(NUM_COL_IMG),
                    c.getString(NUM_COL_DESC), c.getString(NUM_COL_HORAIRE), c.getString(NUM_COL_NUM)));
        }
        c.close();
        return allShop;
    }

}

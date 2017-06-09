package com.baroghel.juliecentre.datastore;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baroghel.juliecentre.data.News;
import com.baroghel.juliecentre.data.ShopNews;
import com.baroghel.juliecentre.data.TendanceNews;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 12/05/2017.
 */

public class NewsBDD {
    private DataBaseSQLite dataBaseSQLite;
    private SQLiteDatabase bdd;

    private static final String TABLE_NEWS = "table_news";
    private static final String TABLE_NEWS_TYPE = "table_news_type";
    private static final String TABLE_NEWS_SHOP = "table_news_shop";

    private static final String COL_ID = "_id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TITLE = "TITLE";
    private static final int NUM_COL_TITLE = 1;
    private static final String COL_IMG = "IMG";
    private static final int NUM_COL_IMG = 2;
    private static final String COL_DESC = "DESC";
    private static final int NUM_COL_DESC = 3;

    private static final String COL_NEWS = "NEWS";
    private static final int NUM_COL_NEWS = 1;
    private static final String COL_TYPE = "TYPE";
    private static final int NUM_COL_TYPE = 2;
    private static final String COL_SHOP = "SHOP";
    private static final int NUM_COL_SHOP = 2;

    public NewsBDD(DataBaseSQLite dataBaseSQLite) {
        this.dataBaseSQLite = dataBaseSQLite;
        this.bdd = dataBaseSQLite.openDataBaseToWrite();
    }

    public void close(){
        this.bdd.close();
    }

    public News getNewsWithId(int id){
        Cursor c = bdd.query(TABLE_NEWS, new String[]{COL_ID, COL_TITLE, COL_IMG, COL_DESC},
                COL_ID + " = " + id, null, null, null, COL_ID);
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        c.moveToNext();
        News news = new News(c.getInt(NUM_COL_ID), c.getString(NUM_COL_TITLE),c.getString(NUM_COL_IMG),
                c.getString(NUM_COL_DESC));
        c.close();
        return news;
    }

    public List<News> getAllNews(){
        TendanceBDD tendanceBDD = new TendanceBDD(dataBaseSQLite);
        ShopBDD shopBDD = new ShopBDD(dataBaseSQLite);
        List<TendanceNews> tendanceNewsList = tendanceBDD.getAllTendances();
        List<ShopNews> shopNewsList = shopBDD.getAllShops();
        tendanceBDD.close();
        shopBDD.close();

        Cursor c = bdd.query(TABLE_NEWS, new String[]{COL_ID, COL_TITLE, COL_IMG, COL_DESC}, null,
                null, null, null, COL_ID);
        if(c.getCount() == 0){
            c.close();
            return null;
        }

        List<News> newsList = new ArrayList<>();
        while(c.moveToNext()){
            News news = new News(c.getInt(NUM_COL_ID), c.getString(NUM_COL_TITLE),
                    c.getString(NUM_COL_IMG), c.getString(NUM_COL_DESC));
            Set<ShopNews> shopOfNews = new HashSet<>();
            List<Integer> shopIdOfNews = getShopOfNews(news.getId());
            for(Integer shopId : shopIdOfNews){
                for(ShopNews shop : shopNewsList){
                    if(shop.getId() == shopId){
                        shopOfNews.add(shop);
                    }
                }
            }
            Set<TendanceNews> tendanceOfNews = new HashSet<>();
            List<Integer> tendanceIdOfNews = getTendanceOfNews(news.getId());
            for(Integer tendanceId : tendanceIdOfNews){
                for(TendanceNews tendance : tendanceNewsList){
                    if(tendance.getId() == tendanceId){
                        tendanceOfNews.add(tendance);
                    }
                }
            }
            news.getShops().addAll(shopOfNews);
            news.getTypes().addAll(tendanceOfNews);
            newsList.add(news);
        }
        c.close();
        return newsList;
    }

    private List<Integer> getShopOfNews(int newsId){
        Cursor c = bdd.query(TABLE_NEWS_SHOP, new String[]{COL_ID, COL_NEWS, COL_SHOP},
                COL_NEWS + " = " + newsId, null, null, null, null);
        List<Integer> shopOfNews = new ArrayList<>();
        while (c.moveToNext()){
            shopOfNews.add(c.getInt(NUM_COL_SHOP));
        }
        c.close();
        return shopOfNews;
    }

    private List<Integer> getTendanceOfNews(int newsId){
        Cursor c = bdd.query(TABLE_NEWS_TYPE, new String[]{COL_ID, COL_NEWS, COL_TYPE},
                COL_NEWS + " = " + newsId, null, null, null, null);
        List<Integer> tendanceOfNews = new ArrayList<>();
        while (c.moveToNext()){
            tendanceOfNews.add(c.getInt(NUM_COL_TYPE));
        }
        c.close();
        return tendanceOfNews;
    }
}

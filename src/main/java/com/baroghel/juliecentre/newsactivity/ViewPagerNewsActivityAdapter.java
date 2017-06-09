package com.baroghel.juliecentre.newsactivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.baroghel.juliecentre.data.News;
import com.baroghel.juliecentre.data.ShopNews;
import com.baroghel.juliecentre.data.TendanceNews;
import com.baroghel.juliecentre.datastore.DataBaseSQLite;
import com.baroghel.juliecentre.datastore.NewsBDD;
import com.baroghel.juliecentre.datastore.PreferenceBDD;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 29/04/2017.
 */

public class ViewPagerNewsActivityAdapter extends FragmentPagerAdapter {
    private List<TendanceNews> tendanceNewsList;
    private List<ShopNews> shopNewses;
    private List<News> newsArrayList;
    private Context context;

    public ViewPagerNewsActivityAdapter(FragmentManager fm, Context context) {
        super(fm);

        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(context);
        PreferenceBDD preferenceBDD = new PreferenceBDD(dataBaseSQLite);

        tendanceNewsList = preferenceBDD.getAllTendancePreferences();
        shopNewses = preferenceBDD.getAllShopPreferences();
        preferenceBDD.close();

        NewsBDD newsBDD = new NewsBDD(dataBaseSQLite);

        this.newsArrayList = newsBDD.getAllNews();
        this.newsArrayList = getSortedListNews(shopNewses, tendanceNewsList);
        newsBDD.close();
    }

    @Override
    public Fragment getItem(int position) {
        ListNewsFragment fragment;
        if (position == 0)
            fragment = (ListNewsFragment) ListNewsFragment.newInstance(newsArrayList);
        else if (position < shopNewses.size() + 1)
            fragment = (ListNewsFragment) ListNewsFragment.newInstance(getNewswithShop(shopNewses.get(position - 1)));
        else
            fragment = (ListNewsFragment) ListNewsFragment.newInstance(getNewswithTendance(tendanceNewsList.get(position - (shopNewses.size() + 1))));

        return fragment;
    }

    @Override
    public int getCount() {
        return 1 + shopNewses.size() + tendanceNewsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Selection";
        else if (position < shopNewses.size() + 1)
            return shopNewses.get(position - 1).getName();
        else
            return tendanceNewsList.get(position - (shopNewses.size() + 1)).getName();
    }

    private List<News> getSortedListNews(List<ShopNews> shopPreferences,
                                         List<TendanceNews> tendancePreferences) {
        Set<News> newsSet = new HashSet<>();
        for (ShopNews shop : shopPreferences) {
            newsSet.addAll(getNewswithShop(shop));
        }
        for (TendanceNews tendance : tendancePreferences) {
            newsSet.addAll(getNewswithTendance(tendance));
        }

        return new ArrayList<>(newsSet);
    }

    private List<News> getNewswithTendance(TendanceNews tendanceNews) {
        Set<News> newses = new HashSet<>();
        for (News news : newsArrayList) {
            if (news.getTypes().contains(tendanceNews))
                newses.add(news);
        }
        return new ArrayList<>(newses);
    }

    private List<News> getNewswithShop(ShopNews shopNews) {
        Set<News> newses = new HashSet<>();
        for (News news : newsArrayList) {
            if (news.getShops().contains(shopNews))
                newses.add(news);
        }
        return new ArrayList<>(newses);
    }
}

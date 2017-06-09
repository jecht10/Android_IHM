package com.baroghel.juliecentre.preferencefragment;

import com.baroghel.juliecentre.data.ShopNews;
import com.baroghel.juliecentre.data.TendanceNews;
import com.baroghel.juliecentre.data.TypeNews;

/**
 * Created by User on 13/05/2017.
 */

public class PreferenceListItem {
    private TypeNews typeNews;
    private String title;
    private boolean isSection = false;
    private boolean isTendance = false;

    public PreferenceListItem(String title){
        this.title =title;
        isSection = true;
    }

    public PreferenceListItem(TendanceNews typeNews){
        this.typeNews = typeNews;
        isTendance = true;
    }

    public PreferenceListItem(ShopNews shopNews){
        this.typeNews = shopNews;
    }

    public TypeNews getTypeNews(){
        return typeNews;
    }

    public String getTitle(){
        if(isSection())
            return title;
        return typeNews.getName();
    }

    public boolean isSection(){
        return isSection;
    }

    public boolean isTendance(){
        return isTendance;
    }
}

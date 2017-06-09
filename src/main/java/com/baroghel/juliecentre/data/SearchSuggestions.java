package com.baroghel.juliecentre.data;

/**
 * Created by User on 01/05/2017.
 */

public class SearchSuggestions {
    private TypeNews typeNews;
    private String type;

    public SearchSuggestions(TendanceNews tendanceNews){
        this.typeNews = tendanceNews;
        type = "TENDANCE";
    }

    public SearchSuggestions(ShopNews shopNews){
        typeNews = shopNews;
        type = "MAGASIN";
    }

    public TypeNews getTypeNews(){
        return typeNews;
    }

    public String getDesc(){
        return type;
    }
}

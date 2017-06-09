package com.baroghel.juliecentre.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 30/04/2017.
 */

public class News {
    private int id;
    private String titre;
    private String image;
    private String desc;
    private List<TendanceNews> types;
    private List<ShopNews> shops;

    public News(int id, String titre, String image, String desc){
        this.id = id;
        this.titre = titre;
        this.image = image;
        this.desc = desc;

        this.types = new ArrayList<>();
        this.shops = new ArrayList<>();
    }

    public News(int id, String titre, String image, String desc, List<TendanceNews> types, List<ShopNews> shops){
        this.id = id;
        this.titre = titre;
        this.image = image;
        this.desc = desc;

        this.types = new ArrayList<>(types);
        this.shops = new ArrayList<>(shops);
    }

    public int getId(){
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getImage() {
        return image;
    }

    public String getDesc(){
        return desc;
    }

    public List<TendanceNews> getTypes(){
        return this.types;
    }

    public List<ShopNews> getShops(){
        return this.shops;
    }
}

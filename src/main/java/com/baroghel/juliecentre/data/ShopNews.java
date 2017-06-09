package com.baroghel.juliecentre.data;

/**
 * Created by User on 12/05/2017.
 */

public class ShopNews extends TypeNews {
    private String image;
    private String desc;
    private String horaire;
    private String num;

    public ShopNews(int id, String name){
        this.id = id;
        this.name = name;
    }

    public ShopNews(int id, String name, String image, String desc, String horaire, String num) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.desc = desc;
        this.horaire = horaire;
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }

    public String getHoraire() {
        return horaire;
    }

    public String getNum() {
        return num;
    }
}

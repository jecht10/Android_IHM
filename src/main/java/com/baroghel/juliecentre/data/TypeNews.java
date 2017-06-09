package com.baroghel.juliecentre.data;

/**
 * Created by User on 12/05/2017.
 */

public abstract class TypeNews {
    protected int id;
    protected String name;

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object a){
        return a instanceof TypeNews && ((TypeNews)a).id == id;
    }
}

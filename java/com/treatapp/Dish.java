package com.treatapp;

public class Dish {
    private String name;
    private String desc;

    public Dish() {}

    Dish(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }


    public void setDesc(String newDesc)
    {
        desc = newDesc;
    }

    public String getDesc()
    {
        return desc;
    }

}

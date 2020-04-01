package com.example.indoorpositioning.placeInfo;

public class Place {
    private int id;
    private int x;
    private int y;
    private String name;
    private int [] dis;

    public Place(int id, int x, int y, String name, int [] dis){
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.dis = dis;
    }
}

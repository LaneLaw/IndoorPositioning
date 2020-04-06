package com.example.indoorpositioning.placeInfo;

public class PlaceArray {
    private Place [] array;
    private static final int inf=Integer.MAX_VALUE;

    public PlaceArray(){
        array = new Place[]{
                new Place(0, 400, 800, "Victoria", new int[]{inf, 70, 210, inf}),
                new Place(1, 470, 800, "Cross Road1", new int[]{70, inf, 200, inf}),
                new Place(2, 470, 1000, "Cross Road2", new int[]{210, 200, inf, 85}),
                new Place(3, 400, 1050, "sammy", new int[]{inf, inf, 85,inf})};
    }

    public Place[] getPlaceArray(){
        return array;
    }
}

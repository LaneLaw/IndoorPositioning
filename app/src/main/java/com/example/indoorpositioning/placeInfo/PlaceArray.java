package com.example.indoorpositioning.placeInfo;

public class PlaceArray {
    private Place [] array;
    private static final int inf=Integer.MAX_VALUE;

    public PlaceArray(){
        array = new Place[]{
                new Place(0, 400, 800, "Victoria", new int[]{inf, 70, 210, inf,inf,inf,inf,inf,inf},1),
                new Place(1, 470, 800, "Cross Road1", new int[]{70, inf, 200, inf,inf,290,inf,inf,inf},1),
                new Place(2, 470, 1000, "Cross Road2", new int[]{210, 200, inf, 85,330,inf,inf,inf,inf},1),
                new Place(3, 400, 1050, "sammy", new int[]{inf, inf, 85,inf,inf,inf,inf,inf,inf},1),
                new Place(4,800,1000,"DIESEL",new int[]{inf,inf,330,inf,inf,inf,inf,inf,inf},1),
                new Place(5,750,720,"Cross Road3",new int[]{inf,290,inf,inf,inf,inf,80,inf,inf},1),
                new Place(6,750,640,"Elevator L1",new int[]{inf,inf,inf,inf,inf,80,inf,0,inf},1),
                new Place(7,880,640,"Elevator L2",new int[]{inf,inf,inf,inf,inf,inf,0,inf,90},2),
                new Place(8,880,750,"Cross Road4",new int[]{inf,inf,inf,inf,inf,inf,inf,90,inf},2)};

    }

    public Place[] getPlaceArray(){
        return array;
    }
}

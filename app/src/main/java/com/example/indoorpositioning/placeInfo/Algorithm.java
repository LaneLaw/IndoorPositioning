package com.example.indoorpositioning.placeInfo;
import java.util.ArrayList;
import java.util.Arrays;
public class Algorithm {
    private static final int inf=Integer.MAX_VALUE;//no edge between two points
    static ArrayList <Integer> pathRecord = new ArrayList<>();
    public static int distance;
    public Algorithm(){

    }
    public ArrayList<Integer> getPath(){
        return pathRecord;
    }
    public static ArrayList<Integer> dijkstra(int[][] graph,int n,int u){
        int[] path=new int[n];
        int dist[]=new int[n];
        boolean s[]=new boolean[n];
        Arrays.fill(s, false);
        Arrays.fill(dist, inf);
        int min,v;
        for(int i=0;i<n;i++){
            dist[i]=graph[u][i];
            if(i!=u&&dist[i]<inf)path[i]=u;
            else path[i]=-1;
        }
        s[u]=true;
        while(true){
            min=inf;v=-1;
            //find the shortest path
            for(int i=0;i<n;i++){
                if(!s[i]){
                    if(dist[i]<min){min=dist[i];v=i;}
                }
            }
            if(v==-1)break;//No shorter path
            //update the new shortest path.
            s[v]=true;
            for(int i=0;i<n;i++){
                if(!s[i]&&
                        graph[v][i]!=inf&&
                        dist[v]+graph[v][i]<dist[i]){
                    dist[i]=dist[v]+graph[v][i];
                    path[i]=v;
                }
            }
        }
        //output the path
        int[] shortest=new int[n];
        for(int i=1;i<n;i++){
            Arrays.fill(shortest, 0);
            System.out.print(dist[i]+":");
            distance = dist[i];
            int k=0;
            shortest[k]=i;
            while(path[shortest[k]]!=0){
                k++;shortest[k]=path[shortest[k-1]];
            }
            k++;shortest[k]=0;

            pathRecord.clear();
            for(int j=k;j>0;j--){
                System.out.printf("%d->",shortest[j]);
                pathRecord.add(shortest[j]);
            }
            pathRecord.add(shortest[0]);
            System.out.println(shortest[0]);
        }
        return pathRecord;
    }

    public static void main(String[] args) {
        PlaceArray placeInfo = new PlaceArray();
        Place [] place = placeInfo.getPlaceArray();
        int [][] W = new int[place.length][place.length];
        for(int i =0; i< place.length; i++) {
            System.out.println();
            for (int j=0; j<place.length;j++){
                W[i][j] = place[i].getDis(j);
                System.out.print(W[i][j] + ", ");
            }
        }
        for(int n=0; n<7; n++){
            int temp2 = W[6][n];
            W[6][n] = W[6][n];
            W[6][n] = temp2;
        }
        for(int i =0; i< place.length; i++) {
            System.out.println();
            for (int j=0; j<place.length;j++){
                System.out.print(W[i][j] + ", ");
            }
        }


        dijkstra(W, 5, 0);
        for (int i=0;i<pathRecord.size(); i ++){
            System.out.println(pathRecord.get(i));
        }
    }
}
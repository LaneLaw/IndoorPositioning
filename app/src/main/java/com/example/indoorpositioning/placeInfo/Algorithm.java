package com.example.indoorpositioning.placeInfo;

import java.util.Arrays;
public class Algorithm {
    private static final int inf=Integer.MAX_VALUE;//表示两个点之间无法直接连通
    public static int[] dijkstra(int[][] graph,int n,int u){
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
            //找到最小的dist
            for(int i=0;i<n;i++){
                if(!s[i]){
                    if(dist[i]<min){min=dist[i];v=i;}
                }
            }
            if(v==-1)break;//找不到更短的路径了
            //更新最短路径
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
        //输出路径
        int[] shortest=new int[n];
        for(int i=1;i<n;i++){
            Arrays.fill(shortest, 0);
            System.out.print(dist[i]+":");
            int k=0;
            shortest[k]=i;
            while(path[shortest[k]]!=0){
                k++;shortest[k]=path[shortest[k-1]];
            }
            k++;shortest[k]=0;
            for(int j=k;j>0;j--){
                System.out.printf("%d->",shortest[j]);
            }
            System.out.println(shortest[0]);
        }
        return dist;
    }
    public static void main(String[] args) {
        int[][] W = {
                {  0,   1,   4,  inf,  inf,  inf },
                {  1,   0,   2,   7,    5,  inf },
                {  4,   2,   0,  inf,    1,  inf },
                { inf,  7,  inf,   0,    3,    2 },
                { inf,  5,    1,   3,   0,    6 },
                { inf, inf,  inf,   2,   6,    0 } };

        dijkstra(W, 5, 0);

    }
}
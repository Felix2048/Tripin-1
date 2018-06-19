package com.android.tripin.util;

/**
 * Created by zjdzn on 2018/6/19.
 */

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.android.tripin.entity.Pin;
import com.android.tripin.enums.PinStatus;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class HamiltonianGraph {
    /**
     * Gets length of the shortest Hamiltonian cycle and back pointers for building the shortest path.
     * O(2^|V| |V|^2)<br>
     * AOJ No. 0120, 0146(partial modification, Semi-Hamilton graph)
     *
     * @param G adjacency matrix
     * @param s source node (※= destination node)
     * @return TSPResult
     */
    static List<Integer> TSP(double[][] G, int s) {

        int length = G.length;
        List<Integer> result = new ArrayList<>();
        result.add(s);

        //只用寻找 length-2 次
        for (int i = 0; i < length - 1; i++) {
            result.add(getNearest(G, result, result.get(result.size() - 1)));
        }
        return result;
    }

    static int getNearest(double[][] G, List<Integer> excludeList, int s) {
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < G.length; i++) {
            if (i == s || excludeList.contains(i)) {
                continue;
            }
            if (G[s][i] < min) {
                min = G[s][i];
                index = i;
            }
        }
        return index;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Pin> getOrderedPins(List<Pin> pinList) {

        //计数变量
        int i, j = 0;
        int pinListSize = pinList.size();

//        DistanceUtil.getDistance()
        List<LatLng> latLngList = pinList.stream().map(Pin::getLatLng).collect(Collectors.toList());

        //定义并计算distanceMap
        double distanceMap[][] = new double[pinListSize][pinListSize];
        for (i = 0; i < pinListSize - 1; i++) {
            for (j = i + 1; j < pinListSize; j++) {
//                System.out.println(i+"  " +j);
                //todo
                distanceMap[i][j] = distanceMap[j][i] = DistanceUtil.getDistance(latLngList.get(i), latLngList.get(j));
//                double distance = Math.sqrt(Math.pow((latLngList.get(i).latitude - latLngList.get(j).latitude), 2) + Math.pow(latLngList.get(i).longitude - latLngList.get(j).longitude, 2));
//                distanceMap[i][j] = distanceMap[j][i] =  distance;
            }
        }
        for (i = 0; i < pinListSize; i++) {
            distanceMap[i][i] = 0;
        }
        for (i = 0; i < pinListSize ; i++) {
            for (j = 0; j < pinListSize; j++) {
                System.out.print(distanceMap[i][j] + " ");
            }
            System.out.println();
        }

//        getShortestHamiltonianCycle(distanceMap);

        List<Integer> tspResult = TSP(distanceMap, 0);
        List<Pin> orderedPins = new ArrayList<>();
        for (Integer index : tspResult) {
            orderedPins.add(pinList.get(index));
        }

        return orderedPins;
    }

//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static void main(String[] args) {
//
//        Pin pin1 = new Pin(1, 0, 0, "a", new Date(), new Date(), PinStatus.WANTED, "note");
//        Pin pin2 = new Pin(1, 1, 1, "a", new Date(), new Date(), PinStatus.WANTED, "note");
//        Pin pin3 = new Pin(1, 3, 4, "a", new Date(), new Date(), PinStatus.WANTED, "note");
//        Pin pin4 = new Pin(1, 100, 100, "a", new Date(), new Date(), PinStatus.WANTED, "note");
//        Pin pin5 = new Pin(1, 98, 102, "a", new Date(), new Date(), PinStatus.WANTED, "note");
//        Pin pin6 = new Pin(1, 200, 200, "a", new Date(), new Date(), PinStatus.WANTED, "note");
//
//        List<Pin> pinList = new ArrayList<Pin>() {
//            {
//                add(pin1);
//                add(pin2);
//                add(pin3);
//                add(pin4);
//                add(pin5);
//                add(pin6);
//            }
//        };

//        getOrderedPins(pinList);
////        double[][] map = new double[][]{
////                {0, 1, 5, 141, 141, 282},
////                {1, 0, 3, 140, 140, 281},
////                {5, 3, 0, 136, 136, 277},
////                {141, 140, 136, 0, 2, 141},
////                {141, 140, 136, 2, 0, 141},
////                {282, 281, 277, 141, 141, 0}
////        };
////        List<Integer> tspResult = TSP(map, 0);
////        System.out.println(Arrays.toString(tspResult.toArray()));
//    }

}
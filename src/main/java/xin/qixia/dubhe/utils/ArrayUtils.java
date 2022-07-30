package xin.qixia.dubhe.utils;

import java.util.ArrayList;

public class ArrayUtils {
    //二维数组翻转
    public static ArrayList<ArrayList<Integer>> test(ArrayList<ArrayList<Integer>> arrayList){
        ArrayList<ArrayList<Integer>> resultList=new ArrayList<>();
        for(int i=0;i<arrayList.get(0).size();i++){
            ArrayList<Integer> res=new ArrayList<>();
           for(int j=0;j<arrayList.size();j++){
               res.add(arrayList.get(j).get(i));
           }
           resultList.add(res);
        }
        return resultList;
    }
}

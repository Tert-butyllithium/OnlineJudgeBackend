package edu.sustech.oj_server.util;

import java.util.List;
import java.util.Map;

public class HUSTToQDU {
    public static final Map<Integer,Integer> trans=Map.of(0,6,4,0,6,-1,7,1,8,3,10,4,11,-2,5,8);
    public static Integer translateStatus(Integer i){
        if(i==null) return null;
        if(trans.containsKey(i)){
            return trans.get(i);
        }
        else{
            if(i<=2||i>9){
                return 6;
            }
            else{
                return -1;
            }
        }
    }

    public static Integer translateStatusInverse(Integer id){
        if(id==null) return null;
        for(int i=-2;i<=9;i++){
            if(id.equals(translateStatus(i))){
                return i;
            }
        }
        return null;
    }

    private static final List<String> lans=List.of("C","C++","Pascal","Java","Python2","Python3","Kotlin");
    public static String translateLanguage(Integer i){
        if(i==null) return  null;
        return lans.get(i);
    }

    public static Integer translateLanguage(String lan){
        if(lan==null) return null;
        for(int i=0;i<lans.size();i++){
            if(lans.get(i).equals(lan)){
                return i;
            }
        }
        return -1;
    }

}

package com.pojo;
public class Sell {
    public static String sellNum(String type,int num){
        Long numA = 0L;
        switch (type){
            //zdj
            case "5":
                for (int i=0;i<num;i++){
                    numA += 2000000000;
                }
                break;
        }
        return numA+"";
    }

    public static UserData sellNum(String type,int num,UserData userData){
        switch (type){
            //zdj
            case "5":
                userData.setZdjNum(Integer.parseInt(userData.getZdjNum())-num+"");
                break;
        }
        return userData;
    }
}

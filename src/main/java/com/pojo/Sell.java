package com.pojo;

import cn.hutool.core.util.RandomUtil;

public class Sell {
    public static String sellNum(String type,int num){
        Long numA = 0L;
        switch (type){
            //zdj
            case "12":
                for (int i=0;i<num;i++){
                    numA += 2000000000;
                }
                break;
            case "11":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(200000000,600000000);
                }
            case "10":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(200000000,600000000);
                }
            case "9":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(200000000,600000000);
                }
            case "8":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(60000000,120000000);
                }
                break;
            case "7":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(6000000,12000000);
                }
                break;
            case "6":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(60000000,120000000);
                }
                break;
            case "5":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(12000000,30000000);
                }
                break;
            case "4":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(1200000,4000000);
                }
                break;
            case "3":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(8000000,12000000);
                }
                break;
            case "2":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(2000000,6000000);
                }
                break;
            case "1":
                for (int i=0;i<num;i++){
                    numA += RandomUtil.randomLong(100000,600000);
                }
                break;
        }
        return numA+"";
    }

    public static UserData sellNum(String type,int num,UserData userData){
        switch (type){
            //zdj
            case "12":
                userData.setZdjNum(Integer.parseInt(userData.getZdjNum())-num+"");
                break;
            case "11":
                userData.setZdNum(Integer.parseInt(userData.getZdNum())-num+"");
                break;
            case "10":
                userData.setZjNum(Integer.parseInt(userData.getZjNum())-num+"");
                break;
            case "9":
                userData.setFp1NUm(Integer.parseInt(userData.getFp1NUm())-num+"");
                break;
            case "8":
                userData.setFp2Num(Integer.parseInt(userData.getFp2Num())-num+"");
                break;
            case "7":
                userData.setFp3Num(Integer.parseInt(userData.getFp3Num())-num+"");
                break;
            case "6":
                userData.setJfzNum(Integer.parseInt(userData.getJfzNum())-num+"");
                break;
            case "5":
                userData.setBfzNum(Integer.parseInt(userData.getBfzNum())-num+"");
                break;
            case "4":
                userData.setCfzNum(Integer.parseInt(userData.getCfzNum())-num+"");
                break;
            case "3":
                userData.setHzzNum(Integer.parseInt(userData.getHzzNum())-num+"");
                break;
            case "2":
                userData.setYzzNum(Integer.parseInt(userData.getYzzNum())-num+"");
                break;
            case "1":
                userData.setBzzNum(Integer.parseInt(userData.getBzzNum())-num+"");
                break;
        }
        return userData;
    }
}

package com.pojo;

import java.util.ArrayList;
import java.util.List;

public class Rep {
    public static final int SUCCES_CODE = 200;
    public static final String SUCCES_MSG = "成功";
    public static final int ERRO_CODE = 507;

    //进的数量
    public static List<Integer> jinList = new ArrayList<>();
    static {
        jinList.add(2);
        jinList.add(2);
        jinList.add(2);
        jinList.add(2);
        jinList.add(3);
    }

    //da hu lu
    public static List<Boolean> dhlList = new ArrayList<>();
    static {
        dhlList.add(true);
        dhlList.add(true);
        dhlList.add(true);
        dhlList.add(true);
        dhlList.add(false);
        dhlList.add(false);
        dhlList.add(false);
        dhlList.add(false);
    }
    //xing
    public static List<Integer> list = new ArrayList<>();
    static {
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);

        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);

        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);

        list.add(5);
        list.add(5);
    }
}

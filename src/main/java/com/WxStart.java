package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WxStart {
    public static void main(String[] args){
        SpringApplication.run(WxStart.class,args);
        System.out.println("启动完成。。。");
    }
}

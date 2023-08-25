package com.controller;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.Rep;
import com.pojo.RepData;
import com.pojo.Sell;
import com.pojo.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class WxController {

    @PostMapping("/wuxing/count")
    public Integer post(@RequestBody JSONObject jsonObject){
        String userName = jsonObject.get("userName").toString();
        String page = jsonObject.get("page").toString();
        String str = redisTemplate.opsForValue().get(WX_COUNT + userName);
        UserData userData = JSONObject.parseObject(str, UserData.class);
        switch (page){
            case "1":
                userData.setNum(Long.valueOf(userData.getNum())-2000000L +"");
                break;
            case "2":
                userData.setNum(Long.valueOf(userData.getNum())-20000000L +"");
                break;
            case "3":
                userData.setNum(Long.valueOf(userData.getNum())-100000000L +"");
                break;
        }
        redisTemplate.opsForValue().set(WX_COUNT+userName, JSON.toJSONString(userData));
        int c = RandomUtil.randomEle(Rep.list);
        log.info("随机数:{}",c);
        return c;
    }

    private final String LOGIN_KEY = "LOGIN_KEY";
    @Autowired
    private StringRedisTemplate redisTemplate;
    @PostMapping("/wuxing/login")
    public JSONObject login(@RequestBody JSONObject jsonObject){
        jsonObject.put("code",501 );
        String strList = redisTemplate.opsForValue().get(LOGIN_KEY);
        String userName = jsonObject.get("userName").toString();
        String passWord = jsonObject.get("passWord").toString();
        if(!StringUtils.isEmpty(strList)){
            List<UserData> userDatas = JSONArray.parseArray(strList, UserData.class);
            for (UserData po : userDatas){
                if(po.getUserName().equals(userName) && po.getPassWord().equals(passWord)){
                    String token = userName+"-"+ UUID.randomUUID().toString().replace("-","" );
                    redisTemplate.opsForValue().set(Rep.TOKEN+ userName, token,180 , TimeUnit.MINUTES);
                    jsonObject.put("token",token);
                    jsonObject.put("code",200 );
                    return jsonObject;
                }
            }
        }
        return jsonObject;
    }

    @PostMapping("/wuxing/save")
    public JSONObject save(@RequestBody JSONObject jsonObject){
        String userName = jsonObject.get("userName").toString();
        String passWord = jsonObject.get("passWord").toString();
        String strList = redisTemplate.opsForValue().get(LOGIN_KEY);
        List<UserData> userDataList = new ArrayList<>();
        if(!StringUtils.isEmpty(strList)){
            userDataList = JSONArray.parseArray(strList, UserData.class);
            if(10 <= userDataList.size()){
                jsonObject.put("msg","注册失败用户空间已满" );
                jsonObject.put("code",501 );
                return jsonObject;
            }
        }
        UserData userData1 = new UserData();
        userData1.setPassWord(passWord);
        userData1.setUserName(userName);
        userDataList.add(userData1);
        redisTemplate.opsForValue().set(LOGIN_KEY, JSON.toJSONString(userDataList));
        jsonObject.put("token",userName );
        jsonObject.put("code",200 );
        return jsonObject;
    }

    //根据用户查询数据
    private final String WX_COUNT="WX_COUNT:";
    @PostMapping("/wuxing/data")
    public UserData queryCount(@RequestBody JSONObject jsonObject){
        UserData userData = new UserData();
        String user = jsonObject.get("userName").toString();
        String str = redisTemplate.opsForValue().get(WX_COUNT + user);
        if(!StringUtils.isEmpty(str)){
            try {
                return JSONObject.parseObject(str, UserData.class);
            } catch (NumberFormatException e) {
                return userData;
            }
        }
        return userData;
    }

    //自定义
    @PostMapping("/wuxing/update")
    public String updateCount(@RequestBody JSONObject jsonObject){
        Object userName = jsonObject.get("userName666");
        Object num = jsonObject.get("num888");
        Object zdj = jsonObject.get("zdj888");
        if(null != userName && null != num){
            UserData userData = new UserData();
            userData.setNum(num.toString());
            if(null != zdj){
                userData.setZdjNum(zdj.toString());
            }
            redisTemplate.opsForValue().set(WX_COUNT+userName.toString(), JSON.toJSONString(userData));
            return "ok";
        }else {
            return "error";
        }
    }

    //自定义
    @PostMapping("/wuxing/obtain")
    public void obtain(@RequestBody JSONObject jsonObject){
        UserData userData = new UserData();
        userData.setNum("1800000000");
        redisTemplate.opsForValue().set(WX_COUNT+jsonObject.get("userName").toString(),JSON.toJSONString(userData) );
    }

    @PostMapping("/wuxing/sell")
    public RepData sell(@RequestBody JSONObject jsonObject){
        String user = jsonObject.get("userName").toString();
        //类型
        String type = jsonObject.get("type").toString();
        //数量次数
        int sellNum = Integer.parseInt(jsonObject.get("sellNum").toString());
        String str = Sell.sellNum(type, sellNum);
        //更新缓存
        String strData = redisTemplate.opsForValue().get(WX_COUNT + user);
        UserData userData = JSONObject.parseObject(strData, UserData.class);
        userData.setNum(Long.valueOf(userData.getNum())+Long.valueOf(str)+"");
        userData = Sell.sellNum(type,sellNum,userData);
        redisTemplate.opsForValue().set(WX_COUNT+user,JSON.toJSONString(userData));
        return RepData.success(str);
    }

    @PostMapping("/wuxing/hulu")
    public RepData lulu(@RequestBody JSONObject jsonObject){
        String user = jsonObject.get("userName").toString();
        //类型1
        String atype = jsonObject.get("aType").toString();
        //类型2
        String btype = jsonObject.get("bType").toString();

        if(atype.equals("1")){
            //da hu lu
            boolean c = RandomUtil.randomEle(Rep.dhlList);
            if(!c){
                return RepData.erro("空");
            }else {
                Integer b  = RandomUtil.randomEle(Rep.jinList);
                 //更新缓存
                updateRedis(user,btype,b);
                return RepData.success("dabaihulu3ge"+b+"个");
            }
        }
        updateRedis(user,btype,1);
        return RepData.success("buda1ge");
    }

    public void updateRedis(String user,String type,Integer num){
        String str = redisTemplate.opsForValue().get(WX_COUNT + user);
        UserData userData = JSONObject.parseObject(str, UserData.class);
        switch (type){
            case "12":
                userData.setZdjNum(Integer.parseInt(userData.getZdjNum())+num+"");
                break;
            case "11":
                userData.setZdNum(Integer.parseInt(userData.getZdNum())+num+"");
                break;
            case "10":
                userData.setZjNum(Integer.parseInt(userData.getZjNum())+num+"");
                break;
            case "9":
                userData.setJfzNum(Integer.parseInt(userData.getJfzNum())+num+"");
                break;
            case "8":
                userData.setBfzNum(Integer.parseInt(userData.getBfzNum())+num+"");
                break;
            case "7":
                userData.setCfzNum(Integer.parseInt(userData.getCfzNum())+num+"");
                break;
            case "6":
                userData.setFp1NUm(Integer.parseInt(userData.getFp1NUm())+num+"");
                break;
            case "5":
                userData.setFp2Num(Integer.parseInt(userData.getFp2Num())+num+"");
                break;
            case "4":
                userData.setFp3Num(Integer.parseInt(userData.getFp3Num())+num+"");
                break;
            case "3":
                userData.setHzzNum(Integer.parseInt(userData.getHzzNum())+num+"");
                break;
            case "2":
                userData.setYzzNum(Integer.parseInt(userData.getYzzNum())+num+"");
                break;
            case "1":
                userData.setBzzNum(Integer.parseInt(userData.getBzzNum())+num+"");
                break;
        }
        redisTemplate.opsForValue().set(WX_COUNT+user,JSON.toJSONString(userData));
    }

}


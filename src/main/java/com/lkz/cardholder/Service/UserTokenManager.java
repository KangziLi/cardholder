package com.lkz.cardholder.Service;

import com.lkz.cardholder.Domain.UserToken;
import com.lkz.cardholder.Util.CharUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserTokenManager {
    private static Map<String, UserToken> tokenMap = new HashMap<>();
    private static Map<Integer, UserToken> idMap = new HashMap<>();

    /**
     * 根据token查找用户id
     *
     * @param token 用户token
     * @return 用户id
     */
    public static Integer getUserId(String token) {

        UserToken userToken = tokenMap.get(token);
        if(userToken == null){
            return null;
        }

        if(userToken.getExpireTime().isBefore(LocalDateTime.now())){
            tokenMap.remove(token);
            idMap.remove(userToken.getUserId());
            return null;
        }

        return userToken.getUserId();
    }

    /**
     * 生成对应id的token
     *
     * @param  id 用户id
     * @return UserToken
     */
    public static UserToken generateToken(Integer id){
        UserToken userToken = null;

//        userToken = idMap.get(id);
//        if(userToken != null) {
//            tokenMap.remove(userToken.getToken());
//            idMap.remove(id);
//        }

        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        //设置生成时间及失效时间
        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusDays(1);

        userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);
        userToken.setUserId(id);
        tokenMap.put(token, userToken);
        idMap.put(id, userToken);

        return userToken;
    }
}

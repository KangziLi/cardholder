package com.lkz.cardholder.Service;


import com.lkz.cardholder.Domain.User;
import com.lkz.cardholder.Domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private UserService userService;

    /**
     * 根据id获取用户信息
     *
     * @param  userId 用户id
     * @return UserInfo
     */
    public UserInfo getInfo(Integer userId) {
        User user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }


}

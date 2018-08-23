package com.lkz.cardholder.Service;


import com.github.pagehelper.PageHelper;
import com.lkz.cardholder.Domain.User;
import com.lkz.cardholder.Domain.UserExample;
import com.lkz.cardholder.dao.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {


    @Resource
    private UserMapper userMapper;

    /**
     * 根据id查找用户
     *
     * @param userId 用户id
     * @return User
     */
    public User findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据openid查找用户
     *
     * @param openId 用户openid
     * @return User
     */
    public User queryByOid(String openId) {
        UserExample example = new UserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     */
    public void add(User user) {
        userMapper.insertSelective(user);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 查找符合条件的用户列表
     *
     * @param username 用户名
     * @param mobile 电话
     * @param page 页数
     * @param size 条目数
     * @param sort 排序
     * @param order 顺序
     * @return List<User> 用户信息列表
     */
    public List<User> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    /**
     * 查找符合条件的用户数
     *
     * @param username 用户名
     * @param mobile 电话
     * @param page 页数
     * @param size 条目数
     * @param sort 排序
     * @param order 顺序
     * @return int 用户数
     */
    public int countSeletive(String username, String mobile, Integer page, Integer size, String sort, String order) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)){
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    /**
     * 获取注册用户数
     *
     * @return int 用户数
     */
    public int count() {
        UserExample example = new UserExample();
        example.or().andDeletedEqualTo(false);

        return (int)userMapper.countByExample(example);
    }

    /**
     * 查找用户名对应用户
     *
     * @param username 用户名
     * @return List<User> 用户列表
     */
    public List<User> queryByUsername(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    /**
     * 查找手机号对应用户
     *
     * @param mobile 手机号
     * @return List<User> 用户列表
     */
    public List<User> queryByMobile(String mobile) {
        UserExample example = new UserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    public void deleteById(Integer id) {
        userMapper.logicalDeleteByPrimaryKey(id);
    }
}

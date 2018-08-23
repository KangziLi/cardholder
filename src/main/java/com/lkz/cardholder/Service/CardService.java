package com.lkz.cardholder.Service;

import com.github.pagehelper.PageHelper;
import com.lkz.cardholder.Domain.Card;
import com.lkz.cardholder.Domain.CardExample;
import com.lkz.cardholder.dao.CardMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CardService {
    @Resource
    private CardMapper cardMapper;

    /**
     * 查找用户名片信息
     *
     * @param uid 用户id
     * @return 名片信息列表
     */
    public List<Card> queryByUid(Integer uid) {
        CardExample example = new CardExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return cardMapper.selectByExample(example);
    }

    /**
     * 查找名片信息
     *
     * @param id 名片id
     * @return 名片信息
     */
    public Card findById(Integer id) {
        return cardMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增名片信息
     *
     * @param card 名片信息
     */
    public int add(Card card) {
        return cardMapper.insertSelective(card);
    }

    /**
     * 更新名片信息
     *
     * @param card 名片信息
     */
    public int update(Card card) {
        return cardMapper.updateByPrimaryKeySelective(card);
    }

    /**
     * 删除名片信息
     *
     * @param id 名片id
     */
    public void delete(Integer id) {
        cardMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 查找名片
     *
     * @param userId 用户id
     * @param name 名片姓名
     * @param page 页数
     * @param limit 条目数
     * @param sort 排序
     * @param order 顺序
     * @return 名片信息列表
     */
    public List<Card> querySelectiveName(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        CardExample cardExample = new CardExample();
        CardExample.Criteria criteria = cardExample.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            cardExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cardMapper.selectByExample(cardExample);
    }

    /**
     * 查找名片
     *
     * @param userId 用户id
     * @param comp 名片公司
     * @param page 页数
     * @param limit 条目数
     * @param sort 排序
     * @param order 顺序
     * @return 名片信息列表
     */
    public List<Card> querySelectiveComp(Integer userId, String comp, Integer page, Integer limit, String sort, String order) {
        CardExample cardExample = new CardExample();
        CardExample.Criteria criteria = cardExample.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(comp)){
            criteria.andCompLike("%" + comp + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            cardExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cardMapper.selectByExample(cardExample);
    }

    /**
     * 查找名片
     *
     * @param userId 用户id
     * @param title 名片职位
     * @param page 页数
     * @param limit 条目数
     * @param sort 排序
     * @param order 顺序
     * @return 名片信息列表
     */
    public List<Card> querySelectiveTitle(Integer userId, String title, Integer page, Integer limit, String sort, String order) {
        CardExample cardExample = new CardExample();
        CardExample.Criteria criteria = cardExample.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            cardExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cardMapper.selectByExample(cardExample);
    }

    /**
     * 查找名片
     *
     * @param userId 用户id
     * @param address 名片地址
     * @param page 页数
     * @param limit 条目数
     * @param sort 排序
     * @param order 顺序
     * @return 名片信息列表
     */
    public List<Card> querySelectiveAddress(Integer userId, String address, Integer page, Integer limit, String sort, String order) {
        CardExample cardExample = new CardExample();
        CardExample.Criteria criteria = cardExample.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(address)){
            criteria.andAddressLike("%" + address + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            cardExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cardMapper.selectByExample(cardExample);
    }

    /**
     * 查找名片
     *
     * @param userId 用户id
     * @param phone 名片联系方式
     * @param page 页数
     * @param limit 条目数
     * @param sort 排序
     * @param order 顺序
     * @return 名片信息列表
     */
    public List<Card> querySelectivePhone(Integer userId, String phone, Integer page, Integer limit, String sort, String order) {
        CardExample cardExample = new CardExample();
        CardExample.Criteria criteria = cardExample.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(phone)){
            criteria.andPhoneLike("%" + phone + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            cardExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cardMapper.selectByExample(cardExample);
    }

    /**
     * 查找名片
     *
     * @param userId 用户id
     * @param other 名片备注
     * @param page 页数
     * @param limit 条目数
     * @param sort 排序
     * @param order 顺序
     * @return 名片信息列表
     */
    public List<Card> querySelectiveOther(Integer userId, String other, Integer page, Integer limit, String sort, String order) {
        CardExample cardExample = new CardExample();
        CardExample.Criteria criteria = cardExample.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(other)){
            criteria.andPhoneLike("%" + other + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            cardExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cardMapper.selectByExample(cardExample);
    }

    /**
     * 查找符合条件名片数
     *
     * @param userId 用户id
     * @param name 名片姓名
     * @param page 页数
     * @param limit 条目数
     * @param sort 排序
     * @param order 顺序
     * @return 名片信息数目
     */
    public int countSelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        CardExample example = new CardExample();
        CardExample.Criteria criteria = example.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");

        }
        criteria.andDeletedEqualTo(false);

        return (int)cardMapper.countByExample(example);
    }

    /**
     * 更新名片
     *
     * @param card 名片
     */
    public void updateById(Card card) {
        cardMapper.updateByPrimaryKeySelective(card);
    }
}
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

    public List<Card> queryByUid(Integer uid) {
        CardExample example = new CardExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return cardMapper.selectByExample(example);
    }

    public Card findById(Integer id) {
        return cardMapper.selectByPrimaryKey(id);
    }

    public int add(Card card) {
        return cardMapper.insertSelective(card);
    }

    public int update(Card card) {
        return cardMapper.updateByPrimaryKeySelective(card);
    }

    public void delete(Integer id) {
        cardMapper.logicalDeleteByPrimaryKey(id);
    }


    public List<Card> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
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

    public void updateById(Card card) {
        cardMapper.updateByPrimaryKeySelective(card);
    }
}
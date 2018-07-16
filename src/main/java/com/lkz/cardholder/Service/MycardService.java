package com.lkz.cardholder.Service;

import com.github.pagehelper.PageHelper;
import com.lkz.cardholder.Domain.Mycard;
import com.lkz.cardholder.Domain.MycardExample;
import com.lkz.cardholder.dao.MycardMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MycardService {
    @Resource
    private MycardMapper mycardMapper;

    public List<Mycard> queryByUid(Integer uid) {
        MycardExample mycardExample = new MycardExample();
        mycardExample.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return mycardMapper.selectByExample(mycardExample);
    }

    public Mycard findById(Integer id) {
        return mycardMapper.selectByPrimaryKey(id);
    }

    public int add(Mycard Mycard) {
        return mycardMapper.insertSelective(Mycard);
    }

    public int update(Mycard Mycard) {
        return mycardMapper.updateByPrimaryKeySelective(Mycard);
    }

    public void delete(Integer id) {
        mycardMapper.logicalDeleteByPrimaryKey(id);
    }

    public List<Mycard> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        MycardExample mycardExample = new MycardExample();
        MycardExample.Criteria criteria = mycardExample.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!org.springframework.util.StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!org.springframework.util.StringUtils.isEmpty(sort) && !org.springframework.util.StringUtils.isEmpty(order)) {
            mycardExample.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return mycardMapper.selectByExample(mycardExample);
    }

    public int countSelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        MycardExample example = new MycardExample();
        MycardExample.Criteria criteria = example.createCriteria();

        if(userId !=  null){
            criteria.andUserIdEqualTo(userId);
        }
        if(!org.springframework.util.StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)mycardMapper.countByExample(example);
    }

    public void updateById(Mycard Mycard) {
        mycardMapper.updateByPrimaryKeySelective(Mycard);
    }
}

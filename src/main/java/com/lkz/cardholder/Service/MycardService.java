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

    /**
     * 查找用户名片信息
     *
     * @param uid 用户id
     * @return 名片信息列表
     */
    public List<Mycard> queryByUid(Integer uid) {
        MycardExample mycardExample = new MycardExample();
        mycardExample.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return mycardMapper.selectByExample(mycardExample);
    }

    /**
     * 查找名片信息
     *
     * @param id 名片id
     * @return 名片信息
     */
    public Mycard findById(Integer id) {
        return mycardMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增名片信息
     *
     * @param mycard 名片信息
     */
    public int add(Mycard mycard) {
        return mycardMapper.insertSelective(mycard);
    }

    /**
     * 更新名片信息
     *
     * @param mycard 名片信息
     */
    public int update(Mycard mycard) {
        return mycardMapper.updateByPrimaryKeySelective(mycard);
    }

    /**
     * 删除名片信息
     *
     * @param id 名片id
     */
    public void delete(Integer id) {
        mycardMapper.logicalDeleteByPrimaryKey(id);
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

    /**
     * 更新名片
     *
     * @param mycard 名片
     */
    public void updateById(Mycard mycard) {
        mycardMapper.updateByPrimaryKeySelective(mycard);
    }
}

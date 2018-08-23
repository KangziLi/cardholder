package com.lkz.cardholder.Controller;


import com.lkz.cardholder.Domain.Mycard;
import com.lkz.cardholder.Service.MycardService;
import com.lkz.cardholder.Util.ResponseUtil;
import com.lkz.cardholder.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mycard")
public class MycardController {
    @Autowired
    private MycardService mycardService;

    //连通性测试
    @GetMapping("test")
    public Object list() {
        return ResponseUtil.ok("This is the mycard page！");
    }

    /**
     * 模糊搜索
     *
     * @param userId    用户ID
     * @param keyword 搜索关键词
     * @return 名片列表
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("search")
    public Object search(@LoginUser Integer userId, String keyword) {
        System.out.println(userId);
        System.out.println(keyword);
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        List<Mycard> mycardList = mycardService.querySelective(userId, keyword,0,1000,"","");
        List<Map<String, Object>> mycardVoList = new ArrayList<>(mycardList.size());
        for(Mycard mycard : mycardList){
            Map<String, Object> mycardVo = new HashMap<>();
            mycardVo.put("id", mycard.getId());
            mycardVo.put("name",mycard.getName());
            mycardVo.put("comp",mycard.getComp());
            mycardVo.put("title",mycard.getTitle());
            mycardVo.put("address",mycard.getAddress());
            mycardVo.put("phone",mycard.getPhone());
            mycardVo.put("other",mycard.getOther());
            mycardVoList.add(mycardVo);
        }
        return ResponseUtil.ok(mycardVoList);
    }

    /**
     * 根据姓名字段查找名片信息
     *
     * @param userId    用户ID
     * @param name 用户姓名
     * @param page 页
     * @param limit 条目数
     * @param sort  排序方式
     * @param order 顺序
     * @return 名片列表
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("select")
    public Object select(@LoginUser Integer userId, String name, Integer page, Integer limit, String sort, String order){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        List<Mycard> mycardList = mycardService.querySelective(userId, name,page, limit, sort, order);
        List<Map<String, Object>> mycardVoList = new ArrayList<>(mycardList.size());
        for(Mycard mycard : mycardList){
            Map<String, Object> mycardVo = new HashMap<>();
            mycardVo.put("id", mycard.getId());
            mycardVo.put("name",mycard.getName());
            mycardVo.put("comp",mycard.getComp());
            mycardVo.put("title",mycard.getTitle());
            mycardVo.put("address",mycard.getAddress());
            mycardVo.put("phone",mycard.getPhone());
            mycardVo.put("other",mycard.getOther());
            mycardVoList.add(mycardVo);
        }
        return ResponseUtil.ok(mycardVoList);
    }

    /**
     * 获取名片列表
     *
     * @param userId    用户ID
     * @return 名片列表
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        List<Mycard> mycardList = mycardService.queryByUid(userId);
        List<Map<String, Object>> mycardVoList = new ArrayList<>(mycardList.size());
        for(Mycard mycard : mycardList){
            Map<String, Object> mycardVo = new HashMap<>();
            mycardVo.put("id", mycard.getId());
            mycardVo.put("name",mycard.getName());
            mycardVo.put("comp",mycard.getComp());
            mycardVo.put("title",mycard.getTitle());
            mycardVo.put("address",mycard.getAddress());
            mycardVo.put("phone",mycard.getPhone());
            mycardVo.put("other",mycard.getOther());
            mycardVoList.add(mycardVo);
        }
        return ResponseUtil.ok(mycardVoList);
    }

    /**
     * 获取名片详情
     *
     * @param userId    用户ID
     * @param id    名片序号
     * @return 名片信息
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, Integer id) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(id == null){
            return ResponseUtil.badArgument();
        }

        Mycard mycard = mycardService.findById(id);
        if(mycard == null){
            return ResponseUtil.badArgumentValue();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("id", mycard.getId());
        data.put("name",mycard.getName());
        data.put("comp",mycard.getComp());
        data.put("title",mycard.getTitle());
        data.put("address",mycard.getAddress());
        data.put("phone",mycard.getPhone());
        data.put("other",mycard.getOther());
        return ResponseUtil.ok(data);
    }

    /**
     * 添加或更新名片信息
     *
     * @param userId    用户ID
     * @param mycard    名片信息
     * @return 名片id
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("save")
    public Object save(@LoginUser Integer userId, @RequestBody Mycard mycard) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(mycard == null){
            return ResponseUtil.badArgument();
        }

        if (mycard.getId() == null || mycard.getId().equals(0)) {
            mycard.setId(null);
            mycard.setUserId(userId);
            mycardService.add(mycard);
        } else {
            mycard.setUserId(userId);
            mycardService.update(mycard);
        }
        return ResponseUtil.ok(mycard.getId());
    }

    /**
     * 删除
     *
     * @param userId 用户ID
     * @param mycard
     * @return 删除结果
     *   成功则 { errno: 0, errmsg: '成功' }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody Mycard mycard) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(mycard == null){
            return ResponseUtil.badArgument();
        }
        mycardService.delete(mycard.getId());
        return ResponseUtil.ok();
    }
}

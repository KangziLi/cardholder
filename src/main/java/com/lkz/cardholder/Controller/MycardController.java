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

    @GetMapping("test")
    public Object list() {
        return ResponseUtil.ok("mycard page");
    }

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
     * 添加或更新收货地址
     *
     * @param userId 用户ID
     * @param mycard 用户名片
     * @return 添加或更新操作结果
     *   成功则 { errno: 0, errmsg: '成功' }
     *   失败则 { errno: XXX, errmsg: XXX }
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

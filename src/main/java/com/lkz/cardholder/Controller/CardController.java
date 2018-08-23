package com.lkz.cardholder.Controller;

import com.lkz.cardholder.Domain.Card;
import com.lkz.cardholder.Service.CardService;
import com.lkz.cardholder.Util.ResponseUtil;
import com.lkz.cardholder.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardService cardService;

    //连通性测试
    @GetMapping("test")
    public Object list() {
        return ResponseUtil.ok("This is the card page！");
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

        if(userId == null){
            return ResponseUtil.unlogin();
        }

        //查询各个字段对应记录
        List<Card> cardList = cardService.querySelectiveName(userId, keyword,0,1000,"","");
        List<Card> cardList1 = cardService.querySelectiveComp(userId, keyword,0,1000,"","");
        List<Card> cardList2 = cardService.querySelectiveTitle(userId, keyword,0,1000,"","");
        List<Card> cardList3= cardService.querySelectiveAddress(userId, keyword,0,1000,"","");
        List<Card> cardList4 = cardService.querySelectivePhone(userId, keyword,0,1000,"","");
        List<Card> cardList5= cardService.querySelectiveOther(userId, keyword,0,1000,"","");
        cardList.addAll(cardList1);
        cardList.addAll(cardList2);
        cardList.addAll(cardList3);
        cardList.addAll(cardList4);
        cardList.addAll(cardList5);

        //封装返回
        List<Map<String, Object>> cardVoList = new ArrayList<>(cardList.size());
        for(Card card : cardList){
            Map<String, Object> cardVo = new HashMap<>();
            cardVo.put("id", card.getId());
            cardVo.put("name",card.getName());
            cardVo.put("comp",card.getComp());
            cardVo.put("title",card.getTitle());
            cardVo.put("address",card.getAddress());
            cardVo.put("phone",card.getPhone());
            cardVo.put("other",card.getOther());
            cardVoList.add(cardVo);
        }
        return ResponseUtil.ok(cardVoList);
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

        List<Card> cardList = cardService.querySelectiveName(userId,name,page, limit, sort, order);

        List<Map<String, Object>> cardVoList = new ArrayList<>(cardList.size());
        for(Card card : cardList){
            Map<String, Object> cardVo = new HashMap<>();
            cardVo.put("id", card.getId());
            cardVo.put("name",card.getName());
            cardVo.put("comp",card.getComp());
            cardVo.put("title",card.getTitle());
            cardVo.put("address",card.getAddress());
            cardVo.put("phone",card.getPhone());
            cardVo.put("other",card.getOther());
            cardVoList.add(cardVo);
        }
        return ResponseUtil.ok(cardVoList);
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

        //查询该用户id名片记录
        List<Card> cardList = cardService.queryByUid(userId);

        //封装返回
        List<Map<String, Object>> cardVoList = new ArrayList<>(cardList.size());
        for(Card card : cardList){
            Map<String, Object> cardVo = new HashMap<>();
            cardVo.put("id", card.getId());
            cardVo.put("name",card.getName());
            cardVo.put("comp",card.getComp());
            cardVo.put("title",card.getTitle());
            cardVo.put("address",card.getAddress());
            cardVo.put("phone",card.getPhone());
            cardVo.put("other",card.getOther());
            cardVoList.add(cardVo);
        }
        return ResponseUtil.ok(cardVoList);
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

        //查询对应id记录
        Card card = cardService.findById(id);
        if(card == null){
            return ResponseUtil.badArgumentValue();
        }

        //封装返回
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("id", card.getId());
        data.put("name",card.getName());
        data.put("comp",card.getComp());
        data.put("title",card.getTitle());
        data.put("address",card.getAddress());
        data.put("phone",card.getPhone());
        data.put("other",card.getOther());
        return ResponseUtil.ok(data);
    }


    /**
     * 添加或更新名片信息
     *
     * @param userId    用户ID
     * @param card    名片信息
     * @return 名片id
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("save")
    public Object save(@LoginUser Integer userId, @RequestBody Card card) {

        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(card == null){
            return ResponseUtil.badArgument();
        }

        //若卡片不存在则新增，否则更新旧卡片信息
        if (card.getId() == null || card.getId().equals(0)) {
            card.setId(null);
            card.setUserId(userId);
            cardService.add(card);
        } else {
            card.setUserId(userId);
            cardService.update(card);
        }
        return ResponseUtil.ok(card.getId());
    }

    /**
     * 删除
     *
     * @param userId 用户ID
     * @param card
     * @return 删除结果
     *   成功则 { errno: 0, errmsg: '成功' }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody Card card) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(card == null){
            return ResponseUtil.badArgument();
        }
        cardService.delete(card.getId());
        return ResponseUtil.ok();
    }
}

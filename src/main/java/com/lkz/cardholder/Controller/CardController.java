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

    @GetMapping("test")
    public Object list() {
        return ResponseUtil.ok("card page");
    }

    @GetMapping("list")
    public Object list(@LoginUser Integer userId) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        List<Card> cardList = cardService.queryByUid(userId);
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


    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, Integer id) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(id == null){
            return ResponseUtil.badArgument();
        }

        Card card = cardService.findById(id);
        if(card == null){
            return ResponseUtil.badArgumentValue();
        }

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


    @PostMapping("save")
    public Object save(@LoginUser Integer userId, @RequestBody Card card) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(card == null){
            return ResponseUtil.badArgument();
        }

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

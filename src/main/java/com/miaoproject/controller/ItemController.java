package com.miaoproject.controller;

import com.miaoproject.controller.viewObject.ItemVo;
import com.miaoproject.response.CommonReturnType;
import com.miaoproject.service.ItemService;
import com.miaoproject.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    //创建商品的controller
    @RequestMapping(value = "/createItem",method = {RequestMethod.POST} )
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name="title")String title,
                                       @RequestParam(name="price") BigDecimal price,
                                       @RequestParam(name="stock")Integer stock,
                                       @RequestParam(name="description")String description,
                                       @RequestParam(name="imgUrl")String imgUrl) throws Exception{
        ItemModel itemModel=new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelForReturn= itemService.createItem(itemModel);
        ItemVo itemVo=convertVoFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVo);
    }

    private ItemVo convertVoFromModel(ItemModel itemModel){
        if(itemModel==null)
            return null;
        ItemVo itemVo=new ItemVo();
        BeanUtils.copyProperties(itemModel,itemVo);
        return itemVo;
    }



}

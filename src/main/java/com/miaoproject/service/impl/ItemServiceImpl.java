package com.miaoproject.service.impl;

import com.miaoproject.dao.ItemMapper;
import com.miaoproject.dao.ItemStockMapper;
import com.miaoproject.dataobject.Item;
import com.miaoproject.dataobject.ItemStock;
import com.miaoproject.error.BusinessException;
import com.miaoproject.error.EmBusinessError;
import com.miaoproject.service.ItemService;
import com.miaoproject.service.model.ItemModel;
import com.miaoproject.validator.ValidationResult;
import com.miaoproject.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemMapper itemMapper;
    @Resource
    private ItemStockMapper itemStockMapper;



    private ItemStock converItemStockFromItemModel(ItemModel itemModel){
        if (itemModel ==null){
            return null;
        }
        ItemStock itemStock=new ItemStock();

        itemStock.setItemId(itemModel.getId());
        itemStock.setStock(itemModel.getStock());
        return itemStock;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result=validator.validate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        ItemStock itemStock=this.converItemStockFromItemModel(itemModel);
        itemStockMapper.insertSelective(itemStock);
        //返回  创建完的对象
        return itemModel;
    }

    @Override
    public List<ItemModel> listItem() {
        List<Item> list=itemMapper.selectList();
        List<ItemModel> listModel=list.stream().map(item -> {
            ItemStock itemStock=itemStockMapper.selectByItemId(item.getId());
            return converModelFromDataObject(item,itemStock);
        }).collect(Collectors.toList());
        return listModel;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        Item item=itemMapper.selectByPrimaryKey(id);
        if(item==null){
            return null;
        }
        //操作获得库存数量
        ItemStock itemStock=itemStockMapper.selectByItemId(item.getId());
        //将dataobject->model
        return converModelFromDataObject(item,itemStock);
    }

    private ItemModel converModelFromDataObject(Item item,ItemStock itemStock){
        ItemModel itemModel=new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setSales(itemStock.getStock());
        return itemModel;
    }

}

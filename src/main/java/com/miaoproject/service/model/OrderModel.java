package com.miaoproject.service.model;

import java.math.BigDecimal;

//用户下单的交易模型
public class OrderModel {
    //20190410000220202
    private String id;
    //下单用户
    private Integer userId;
    //商品id
    private Integer itemId;
    //商品价格
    private BigDecimal itemPrice;
    //购买数量
    private Integer amout;
    //购买总金额
    private BigDecimal orderAmout;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmout() {
        return amout;
    }

    public void setAmout(Integer amout) {
        this.amout = amout;
    }

    public BigDecimal getOrderAmout() {
        return orderAmout;
    }

    public void setOrderAmout(BigDecimal orderAmout) {
        this.orderAmout = orderAmout;
    }
}

package com.miaoproject.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

public class PormoModel {

    private Integer id;
    //秒杀胡哦哦东状态  1  还未开始  2 进行  3一结束
    private Integer status;
    //秒杀活动名称
    private String promoName;
    //秒杀开始时间
    private DateTime startDate;
    //秒杀结束时间
    private DateTime endDate;
    //秒杀活动的使用商品
    private Integer itemId;

    //秒杀活动的商品价格
    private BigDecimal promoPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }
}

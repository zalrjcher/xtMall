package com.xtuniversity.mall.model.util;

import com.xtuniversity.mall.model.address.entity.Address;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zlj
 * @introduce
 */
public class OrderResult {
    private long id ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String image;
    private String name ;
    private long price;
    private long allPrice;
    /**
     * number是购买的数量
     */
    private int number ;

    public int getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(int commodityNum) {
        this.commodityNum = commodityNum;
    }

    /**
     * 货物的总量
     */
    private int commodityNum;
    private String createTime;
   	private Address address ;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(long allPrice) {
        this.allPrice = allPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static OrderResult ValueOf(long id , String image, String name , long price, long allPrice , int number, String createTime, Address address,int commodityNum){
        OrderResult orderResult  = new OrderResult();
        orderResult.setImage(image);
        orderResult.setId(id);
        orderResult.setName(name);
        orderResult.setPrice(price);
        orderResult.setAllPrice(allPrice);
        orderResult.setCreateTime(createTime);
        orderResult.setNumber(number);
        orderResult.setAddress(address);
        orderResult.setCommodityNum(commodityNum);
        return orderResult;
    }
}

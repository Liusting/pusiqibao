package com.pusiqibao.entity;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * (CarShop)实体类
 *
 * @author makejava
 * @since 2021-07-03 15:08:15
 */
public class CarShop extends BaseEntity {

    /**
     * 洗车店id
     */
    private String id;
    /**
     * 洗车店名称
     */
    private String name;
    /**
     * 洗车店经度
     */
    private Double lng;
    /**
     * 洗车店维度
     */
    private Double lat;
    /**
     * 洗车店门店照片
     */
    private String imageurl;
    /**
     * 0--筹建中  1---营业中  2---休息中
     */
    private Integer type;
    /**
     * 洗车店地址
     */
    private String address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
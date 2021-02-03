package com.example.wetchat.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SpAddress)实体类
 *
 * @author makejava
 * @since 2021-02-01 10:46:52
 */
@Data
public class SpAddress implements Serializable {
    private static final long serialVersionUID = 280380528401654049L;
    /**
     * 收货地址id
     */
    private String id;
    /**
     * 收货人名字
     */
    private String receivername;
    /**
     * 电话
     */
    private String phonenumber;
    /**
     * 详细地址
     */
    private String addressdetail;
    /**
     * 关联的用户名
     */
    private String userid;
    /**
     * 省份
     */
    private String provincename;
    /**
     * 地级市
     */
    private String cityname;
    /**
     * 县级市
     */
    private String countyname;
    /**
     * 1.默认 0.不默认
     */
    private Integer defaultaddress;

}
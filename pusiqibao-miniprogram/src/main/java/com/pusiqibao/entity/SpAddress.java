package com.washcar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (SpAddress)实体类
 *
 * @author makejava
 * @since 2021-06-18 17:03:03
 */
public class SpAddress implements Serializable {
    private static final long serialVersionUID = -30707548412912285L;
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
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddressdetail() {
        return addressdetail;
    }

    public void setAddressdetail(String addressdetail) {
        this.addressdetail = addressdetail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public Integer getDefaultaddress() {
        return defaultaddress;
    }

    public void setDefaultaddress(Integer defaultaddress) {
        this.defaultaddress = defaultaddress;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
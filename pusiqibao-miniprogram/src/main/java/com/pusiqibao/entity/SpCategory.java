package com.pusiqibao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (SpCategory)实体类
 *
 * @author makejava
 * @since 2021-06-24 09:50:06
 */
public class SpCategory implements Serializable {
    private static final long serialVersionUID = 921734897396147699L;
    /**
     * id
     */
    private Long id;
    /**
     * 上级id
     */
    private Long parentId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 显示状态：0->不显示；1->显示
     */
    private Integer showStatus;
    /**
     * 图片展示
     */
    private String imageUrl;
    /**
     * 分类等级：1一级分类 2二级分类 3三级分类
     */
    private Integer level;

    /**
     * 子列表
     * @return
     */
    List<SpCategory> children;


    public SpCategory(Long id, Long parentId, String name, Date createTime, Integer showStatus, String imageUrl, Integer level) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.createTime = createTime;
        this.showStatus = showStatus;
        this.imageUrl = imageUrl;
        this.level = level;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<SpCategory> getChildren() {
        return children;
    }

    public void setChildren(List<SpCategory> children) {
        this.children = children;
    }
}
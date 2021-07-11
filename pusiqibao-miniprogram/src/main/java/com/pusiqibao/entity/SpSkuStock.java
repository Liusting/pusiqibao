package com.pusiqibao.entity;

import java.io.Serializable;

/**
 * sku的库存(SpSkuStock)实体类
 *
 * @author makejava
 * @since 2021-06-29 10:34:01
 */
public class SpSkuStock implements Serializable {
    private static final long serialVersionUID = 936703436492965947L;

    private Long id;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * sku编码
     */
    private String skuCode;
    /**
     * 价格
     */
    private Double price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 预警库存
     */
    private Integer lowStock;
    /**
     * 展示图片
     */
    private String pic;
    /**
     * 销量
     */
    private Integer sale;
    /**
     * 单品促销价格
     */
    private Double promotionPrice;
    /**
     * 锁定库存
     */
    private Integer lockStock;
    /**
     * 商品销售属性，json格式
     */
    private String spData;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLowStock() {
        return lowStock;
    }

    public void setLowStock(Integer lowStock) {
        this.lowStock = lowStock;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public String getSpData() {
        return spData;
    }

    public void setSpData(String spData) {
        this.spData = spData;
    }

}
package com.pusiqibao.miniprogram.entity;

import com.pusiqibao.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeChatProductDetail {

    @ApiModelProperty("商品信息")
    private SpProduct spProduct;

    @ApiModelProperty("商品的sku库存信息")
    private List<SpSkuStock> skuStockList;

    @ApiModelProperty("商品可用优惠券")
    private List<SpCoupon> couponList;

    @ApiModelProperty("商品品牌")
    private SpBrand brand;

    @ApiModelProperty("商品属性与参数")
    private List<SpProductAttribute> productAttributeList;
}

package com.washcar.controller;

<<<<<<< HEAD:pusiqibao-miniprogram/src/main/java/com/washcar/controller/SpProductController.java
import com.washcar.config.core.domain.AjaxResult;
import com.washcar.entity.SpProduct;
import com.washcar.service.SpProductService;
=======
import com.pusiqibao.config.core.domain.AjaxResult;
import com.pusiqibao.entity.SpProduct;
import com.pusiqibao.entity.SpSkuStock;
import com.pusiqibao.service.SpProductService;
import com.pusiqibao.service.SpSkuStockService;
>>>>>>> f7d04b92776c40dbf6f34df868ede0a3b7fee56e:pusiqibao-miniprogram/src/main/java/com/pusiqibao/controller/SpProductController.java
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SpProduct)表控制层
 *
 * @author makejava
 * @since 2021-06-29 10:33:16
 */
@RestController
@RequestMapping("spProduct")
@Api("商品模块")
public class SpProductController {
    /**
     * 服务对象
     */
    @Resource
    private SpProductService spProductService;

    @Resource
    private SpSkuStockService spSkuStockService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ApiOperation("商品详情")
    public AjaxResult selectOne(Long id) {
        //获取商品详情
        SpProduct spProduct = this.spProductService.queryById(id);
        String[] strArray = null;
        strArray = spProduct.getAlbumPics().split(","); //拆分字符为"," ,然后把结果交给数组strArray
        spProduct.setAlbumPics("");
        spProduct.setAblums(strArray);

        //商品分类属性

        //商品sku
        List<SpSkuStock> skuList = spSkuStockService.queryAll(id);

        //商品所属优惠券


        return AjaxResult.success("操作成功",
                spProduct
        );
    }

}
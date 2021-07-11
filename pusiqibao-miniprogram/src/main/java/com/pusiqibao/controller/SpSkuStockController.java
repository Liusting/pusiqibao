package com.pusiqibao.controller;

import com.pusiqibao.config.core.domain.AjaxResult;
import com.pusiqibao.service.SpSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * sku的库存(SpSkuStock)表控制层
 *
 * @author makejava
 * @since 2021-06-29 10:34:02
 */
@RestController
@RequestMapping("spSkuStock")
@Api("商品SKU")
public class SpSkuStockController {
    /**
     * 服务对象
     */
    @Resource
    private SpSkuStockService spSkuStockService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ApiOperation("商品库存详情")
    public AjaxResult selectOne(Long id) {
        return AjaxResult.success("操作成功",this.spSkuStockService.queryById(id));
    }

}
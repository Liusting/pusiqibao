package com.washcar.controller;

import com.washcar.config.core.domain.AjaxResult;
import com.washcar.entity.SpCategory;
import com.washcar.service.SpCategoryService;
import com.washcar.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SpCategory)表控制层
 *
 * @author makejava
 * @since 2021-06-24 09:50:08
 */
@RestController
@RequestMapping("spCategory")
@Api("汽配分类模块")
public class SpCategoryController {
    /**
     * 服务对象
     */
    @Resource
    private SpCategoryService spCategoryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ApiOperation("查询分类明细")
    public AjaxResult selectOne(Long id) {
        return AjaxResult.success("操作成功",this.spCategoryService.queryById(id));
    }

    @GetMapping("getCategoryTree")
    @ApiOperation("查询分类列表")
    public AjaxResult selectOne() {
        List<SpCategory> list = this.spCategoryService.queryAllBy();
        return AjaxResult.success("操作成功", TreeUtil.listToTree(list));
    }



}
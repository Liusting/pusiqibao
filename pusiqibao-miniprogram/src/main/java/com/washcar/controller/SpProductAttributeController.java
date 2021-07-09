package com.washcar.controller;

import com.washcar.config.core.domain.AjaxResult;
import com.washcar.entity.SpProductAttribute;
import com.washcar.service.SpProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性参数表(SpProductAttribute)表控制层
 *
 * @author makejava
 * @since 2021-06-29 10:34:46
 */
@RestController
@RequestMapping("spProductAttribute")
@Api("商品属性")
public class SpProductAttributeController {
    /**
     * 服务对象
     */
    @Resource
    private SpProductAttributeService spProductAttributeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ApiOperation("商品属性详情")
    public AjaxResult selectOne(Long id) {
        return AjaxResult.success("操作成功", this.spProductAttributeService.queryById(id));
    }

    @GetMapping("selectOneDetail")
    @ApiOperation("商品属性详情Byid")
    public AjaxResult selectOneDetail(Long id) {
        List<SpProductAttribute> spProductAttribute = this.spProductAttributeService.selectOneDetail(id);
        List reList = null;
        for (int i = 0; i < spProductAttribute.size(); i++) {
            String[] strArray = null;
            System.out.println(spProductAttribute.get(i).getInputList());
            strArray = spProductAttribute.get(i).getInputList().split(","); //拆分字符为"," ,然后把结果交给数组strArray
            spProductAttribute.get(i).setInputList("");
            spProductAttribute.get(i).setTypeList(strArray);
        }
        return AjaxResult.success("操作成功", spProductAttribute);
    }




}
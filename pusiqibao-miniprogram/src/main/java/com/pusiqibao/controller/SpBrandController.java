package com.pusiqibao.controller;

import com.pusiqibao.entity.SpBrand;
import com.pusiqibao.service.SpBrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 品牌表(SpBrand)表控制层
 *
 * @author makejava
 * @since 2021-07-09 17:14:58
 */
@RestController
@RequestMapping("spBrand")
public class SpBrandController {
    /**
     * 服务对象
     */
    @Resource
    private SpBrandService spBrandService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SpBrand selectOne(Long id) {
        return this.spBrandService.queryById(id);
    }

}
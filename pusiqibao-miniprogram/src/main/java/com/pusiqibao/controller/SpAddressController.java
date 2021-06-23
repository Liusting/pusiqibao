package com.pusiqibao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pusiqibao.config.core.domain.AjaxResult;
import com.pusiqibao.dao.SpAddressDao;
import com.pusiqibao.entity.SpAddress;
import com.pusiqibao.service.SpAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * (SpAddress)表控制层
 *
 * @author makejava
 * @since 2021-06-18 17:03:07
 */
@Api("用户地址信息管理")
@RestController
@RequestMapping("spAddress")
public class SpAddressController {
    /**
     * 服务对象
     */
    @Resource
    private SpAddressService spAddressService;

    @Autowired
    private SpAddressDao spAddressDao;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ApiOperation("地址详情")
    public AjaxResult selectOne(String id) {
        return AjaxResult.success("操作成功",this.spAddressService.queryById(id));
    }

    @PostMapping("insert")
    @ApiOperation("添加地址")
    public AjaxResult insert(@RequestBody SpAddress spAddress) {
        spAddress.setId(UUID.randomUUID().toString().replace("-",""));
        spAddress.setUpdatetime(new Date());
        spAddress.setCreatetime(new Date());
        return AjaxResult.success(this.spAddressService.insert(spAddress));
    }

    @GetMapping("/spAddressList")
    @ApiOperation("地址列表")
    public AjaxResult spaddressList(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SpAddress> list = this.spAddressService.queryAll();
        PageInfo pageInfo = new PageInfo(list);
        return AjaxResult.success("操作成功",pageInfo);
    }



}
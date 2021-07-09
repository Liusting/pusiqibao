package com.washcar.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.washcar.entity.CarShop;
import com.washcar.service.CarShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CarShop)表控制层
 *
 * @author makejava
 * @since 2021-07-03 15:08:16
 */
@RestController
@RequestMapping("carShop")
@Api("洗车店管理")
public class CarShopController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private CarShopService carShopService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ApiOperation("洗车店详情")
    public AjaxResult selectOne(String id) {
        try {
            return AjaxResult.success("操作成功",this.carShopService.queryById(id));
        }catch (Exception e){
            throw new NullPointerException();
        }

    }

    @PostMapping("/getWashCarList")
    @ApiOperation("洗车店列表")
    public PageInfo getWashCarList(CarShop carShop){
//        PageHelper.startPage(carShop.getPageNum(),carShop.getPageSize());
        CarShop carShop1 = new CarShop();
        List<CarShop> list = carShopService.queryAll(carShop1);
        PageInfo  pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
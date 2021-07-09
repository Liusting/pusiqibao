package com.washcar.controller;

import com.washcar.entity.SpCoupon;
import com.washcar.service.SpCouponService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 优惠券表(SpCoupon)表控制层
 *
 * @author makejava
 * @since 2021-06-30 16:39:32
 */
@RestController
@RequestMapping("spCoupon")
public class SpCouponController {
    /**
     * 服务对象
     */
    @Resource
    private SpCouponService spCouponService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SpCoupon selectOne(Long id) {
        return this.spCouponService.queryById(id);
    }

}
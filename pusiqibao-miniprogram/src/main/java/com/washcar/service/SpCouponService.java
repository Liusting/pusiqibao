package com.washcar.service;

import com.washcar.entity.SpCoupon;

import java.util.List;

/**
 * 优惠券表(SpCoupon)表服务接口
 *
 * @author makejava
 * @since 2021-06-30 16:39:31
 */
public interface SpCouponService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpCoupon queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpCoupon> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param spCoupon 实例对象
     * @return 实例对象
     */
    SpCoupon insert(SpCoupon spCoupon);

    /**
     * 修改数据
     *
     * @param spCoupon 实例对象
     * @return 实例对象
     */
    SpCoupon update(SpCoupon spCoupon);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
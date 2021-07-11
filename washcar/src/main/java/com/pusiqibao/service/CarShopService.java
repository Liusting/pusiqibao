package com.pusiqibao.service;

import com.pusiqibao.entity.CarShop;

import java.util.List;

/**
 * (CarShop)表服务接口
 *
 * @author makejava
 * @since 2021-07-03 15:08:15
 */
public interface CarShopService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CarShop queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CarShop> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param carShop 实例对象
     * @return 实例对象
     */
    CarShop insert(CarShop carShop);

    /**
     * 修改数据
     *
     * @param carShop 实例对象
     * @return 实例对象
     */
    CarShop update(CarShop carShop);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    List<CarShop> queryAll(CarShop carShop);
}
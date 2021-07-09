package com.washcar.service;

import com.washcar.entity.SpSkuStock;

import java.util.List;

/**
 * sku的库存(SpSkuStock)表服务接口
 *
 * @author makejava
 * @since 2021-06-29 10:34:02
 */
public interface SpSkuStockService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpSkuStock queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpSkuStock> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param spSkuStock 实例对象
     * @return 实例对象
     */
    SpSkuStock insert(SpSkuStock spSkuStock);

    /**
     * 修改数据
     *
     * @param spSkuStock 实例对象
     * @return 实例对象
     */
    SpSkuStock update(SpSkuStock spSkuStock);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
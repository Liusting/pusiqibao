package com.washcar.service;

import com.washcar.entity.SpProduct;

import java.util.List;

/**
 * (SpProduct)表服务接口
 *
 * @author makejava
 * @since 2021-06-29 10:33:16
 */
public interface SpProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpProduct queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpProduct> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param spProduct 实例对象
     * @return 实例对象
     */
    SpProduct insert(SpProduct spProduct);

    /**
     * 修改数据
     *
     * @param spProduct 实例对象
     * @return 实例对象
     */
    SpProduct update(SpProduct spProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
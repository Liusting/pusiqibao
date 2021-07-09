package com.washcar.service;

import com.washcar.entity.SpAddress;

import java.util.List;

/**
 * (SpAddress)表服务接口
 *
 * @author makejava
 * @since 2021-06-18 17:03:06
 */


public interface SpAddressService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpAddress queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpAddress> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param spAddress 实例对象
     * @return 实例对象
     */
    SpAddress insert(SpAddress spAddress);

    /**
     * 修改数据
     *
     * @param spAddress 实例对象
     * @return 实例对象
     */
    SpAddress update(SpAddress spAddress);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    List<SpAddress> queryAll();
}
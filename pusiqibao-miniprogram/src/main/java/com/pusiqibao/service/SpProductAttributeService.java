package com.washcar.service;

import com.washcar.entity.SpProductAttribute;

import java.util.List;

/**
 * 商品属性参数表(SpProductAttribute)表服务接口
 *
 * @author makejava
 * @since 2021-06-29 10:34:45
 */
public interface SpProductAttributeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpProductAttribute queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpProductAttribute> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param spProductAttribute 实例对象
     * @return 实例对象
     */
    SpProductAttribute insert(SpProductAttribute spProductAttribute);

    /**
     * 修改数据
     *
     * @param spProductAttribute 实例对象
     * @return 实例对象
     */
    SpProductAttribute update(SpProductAttribute spProductAttribute);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    List<SpProductAttribute> selectOneDetail(Long id);
}
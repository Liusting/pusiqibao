package com.pusiqibao.service;

import com.pusiqibao.entity.SpCategory;

import java.util.List;

/**
 * (SpCategory)表服务接口
 *
 * @author makejava
 * @since 2021-06-24 09:50:07
 */
public interface SpCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpCategory queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpCategory> queryAllByLimit(int offset, int limit);


    /**
     * 新增数据
     *
     * @param spCategory 实例对象
     * @return 实例对象
     */
    SpCategory insert(SpCategory spCategory);

    /**
     * 修改数据
     *
     * @param spCategory 实例对象
     * @return 实例对象
     */
    SpCategory update(SpCategory spCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    List<SpCategory> queryAllBy();
}
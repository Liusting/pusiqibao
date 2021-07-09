package com.pusiqibao.service;

import com.pusiqibao.entity.SpBrand;

import java.util.List;

/**
 * 品牌表(SpBrand)表服务接口
 *
 * @author makejava
 * @since 2021-07-09 17:14:57
 */
public interface SpBrandService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpBrand queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpBrand> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param spBrand 实例对象
     * @return 实例对象
     */
    SpBrand insert(SpBrand spBrand);

    /**
     * 修改数据
     *
     * @param spBrand 实例对象
     * @return 实例对象
     */
    SpBrand update(SpBrand spBrand);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
package com.washcar.dao;

import com.washcar.entity.SpCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SpCategory)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-24 09:50:07
 */
@Mapper
public interface SpCategoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpCategory queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpCategory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param spCategory 实例对象
     * @return 对象列表
     */
    List<SpCategory> queryAll();

    /**
     * 新增数据
     *
     * @param spCategory 实例对象
     * @return 影响行数
     */
    int insert(SpCategory spCategory);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpCategory> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SpCategory> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpCategory> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SpCategory> entities);

    /**
     * 修改数据
     *
     * @param spCategory 实例对象
     * @return 影响行数
     */
    int update(SpCategory spCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
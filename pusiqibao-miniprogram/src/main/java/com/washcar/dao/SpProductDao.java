package com.washcar.dao;

import com.washcar.entity.SpProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SpProduct)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-29 10:33:15
 */
@Mapper
public interface SpProductDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpProduct queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpProduct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param spProduct 实例对象
     * @return 对象列表
     */
    List<SpProduct> queryAll(SpProduct spProduct);

    /**
     * 新增数据
     *
     * @param spProduct 实例对象
     * @return 影响行数
     */
    int insert(SpProduct spProduct);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpProduct> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SpProduct> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpProduct> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SpProduct> entities);

    /**
     * 修改数据
     *
     * @param spProduct 实例对象
     * @return 影响行数
     */
    int update(SpProduct spProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
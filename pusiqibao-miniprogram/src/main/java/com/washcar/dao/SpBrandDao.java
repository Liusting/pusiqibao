package com.pusiqibao.dao;

import com.pusiqibao.entity.SpBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌表(SpBrand)表数据库访问层
 *
 * @author makejava
 * @since 2021-07-09 17:14:57
 */
@Mapper
public interface SpBrandDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpBrand queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpBrand> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param spBrand 实例对象
     * @return 对象列表
     */
    List<SpBrand> queryAll(SpBrand spBrand);

    /**
     * 新增数据
     *
     * @param spBrand 实例对象
     * @return 影响行数
     */
    int insert(SpBrand spBrand);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpBrand> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SpBrand> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpBrand> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SpBrand> entities);

    /**
     * 修改数据
     *
     * @param spBrand 实例对象
     * @return 影响行数
     */
    int update(SpBrand spBrand);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
package com.pusiqibao.dao;

import com.pusiqibao.entity.SpSkuStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku的库存(SpSkuStock)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-29 10:34:01
 */
@Mapper
public interface SpSkuStockDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpSkuStock queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpSkuStock> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param spSkuStock 实例对象
     * @return 对象列表
     */
    List<SpSkuStock> queryAll(SpSkuStock spSkuStock);

    /**
     * 新增数据
     *
     * @param spSkuStock 实例对象
     * @return 影响行数
     */
    int insert(SpSkuStock spSkuStock);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpSkuStock> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SpSkuStock> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpSkuStock> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SpSkuStock> entities);

    /**
     * 修改数据
     *
     * @param spSkuStock 实例对象
     * @return 影响行数
     */
    int update(SpSkuStock spSkuStock);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
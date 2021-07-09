package com.washcar.dao;

import com.washcar.entity.SpCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券表(SpCoupon)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-30 16:39:30
 */
public interface SpCouponDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpCoupon queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpCoupon> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param spCoupon 实例对象
     * @return 对象列表
     */
    List<SpCoupon> queryAll(SpCoupon spCoupon);

    /**
     * 新增数据
     *
     * @param spCoupon 实例对象
     * @return 影响行数
     */
    int insert(SpCoupon spCoupon);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpCoupon> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SpCoupon> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpCoupon> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SpCoupon> entities);

    /**
     * 修改数据
     *
     * @param spCoupon 实例对象
     * @return 影响行数
     */
    int update(SpCoupon spCoupon);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
package com.washcar.dao;

import com.washcar.entity.CarShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CarShop)表数据库访问层
 *
 * @author makejava
 * @since 2021-07-03 15:08:15
 */
@Mapper
public interface CarShopDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CarShop queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CarShop> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param carShop 实例对象
     * @return 对象列表
     */
    List<CarShop> queryAll(CarShop carShop);

    /**
     * 新增数据
     *
     * @param carShop 实例对象
     * @return 影响行数
     */
    int insert(CarShop carShop);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CarShop> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CarShop> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<CarShop> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<CarShop> entities);

    /**
     * 修改数据
     *
     * @param carShop 实例对象
     * @return 影响行数
     */
    int update(CarShop carShop);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}
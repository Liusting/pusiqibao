package com.example.wetchat.dao;

import com.example.wetchat.entity.SpAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (SpAddress)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-01 10:46:53
 */
@Mapper
public interface SpAddressDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SpAddress queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SpAddress> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param spAddress 实例对象
     * @return 对象列表
     */
    List<SpAddress> queryAll(SpAddress spAddress);

    /**
     * 新增数据
     *
     * @param spAddress 实例对象
     * @return 影响行数
     */
    int insert(SpAddress spAddress);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpAddress> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SpAddress> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SpAddress> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SpAddress> entities);

    /**
     * 修改数据
     *
     * @param spAddress 实例对象
     * @return 影响行数
     */
    int update(SpAddress spAddress);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    List<SpAddress> queryAllByUserId(@Param("userId") String userId);
}
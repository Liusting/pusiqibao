package com.pusiqibao.service.impl;

import com.pusiqibao.dao.SpAddressDao;
import com.pusiqibao.entity.SpAddress;
import com.pusiqibao.service.SpAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SpAddress)表服务实现类
 *
 * @author makejava
 * @since 2021-06-18 17:03:07
 */
@Service("spAddressService")
public class SpAddressServiceImpl implements SpAddressService {
    @Resource
    private SpAddressDao spAddressDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SpAddress queryById(String id) {
        return this.spAddressDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SpAddress> queryAllByLimit(int offset, int limit) {
        return this.spAddressDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param spAddress 实例对象
     * @return 实例对象
     */
    @Override
    public SpAddress insert(SpAddress spAddress) {
        this.spAddressDao.insert(spAddress);
        return spAddress;
    }

    /**
     * 修改数据
     *
     * @param spAddress 实例对象
     * @return 实例对象
     */
    @Override
    public SpAddress update(SpAddress spAddress) {
        this.spAddressDao.update(spAddress);
        return this.queryById(spAddress.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.spAddressDao.deleteById(id) > 0;
    }

    @Override
    public List<SpAddress> queryAll() {
        return this.spAddressDao.queryAll();
    }
}
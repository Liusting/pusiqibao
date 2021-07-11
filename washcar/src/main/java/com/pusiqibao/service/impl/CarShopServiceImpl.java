package com.pusiqibao.service.impl;

import com.pusiqibao.dao.CarShopDao;
import com.pusiqibao.entity.CarShop;
import com.pusiqibao.service.CarShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CarShop)表服务实现类
 *
 * @author makejava
 * @since 2021-07-03 15:08:15
 */
@Service("carShopService")
public class CarShopServiceImpl implements CarShopService {
    @Resource
    private CarShopDao carShopDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CarShop queryById(String id) {
        return this.carShopDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<CarShop> queryAllByLimit(int offset, int limit) {
        return this.carShopDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param carShop 实例对象
     * @return 实例对象
     */
    @Override
    public CarShop insert(CarShop carShop) {
        this.carShopDao.insert(carShop);
        return carShop;
    }

    /**
     * 修改数据
     *
     * @param carShop 实例对象
     * @return 实例对象
     */
    @Override
    public CarShop update(CarShop carShop) {
        this.carShopDao.update(carShop);
        return this.queryById(carShop.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.carShopDao.deleteById(id) > 0;
    }

    @Override
    public List<CarShop> queryAll(CarShop carShop) {
        return this.carShopDao.queryAll(carShop);
    }
}
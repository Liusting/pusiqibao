package com.washcar.service.impl;

import com.washcar.dao.SpSkuStockDao;
import com.washcar.entity.SpSkuStock;
import com.washcar.service.SpSkuStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * sku的库存(SpSkuStock)表服务实现类
 *
 * @author makejava
 * @since 2021-06-29 10:34:02
 */
@Service("spSkuStockService")
public class SpSkuStockServiceImpl implements SpSkuStockService {
    @Resource
    private SpSkuStockDao spSkuStockDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SpSkuStock queryById(Long id) {
        return this.spSkuStockDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SpSkuStock> queryAllByLimit(int offset, int limit) {
        return this.spSkuStockDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param spSkuStock 实例对象
     * @return 实例对象
     */
    @Override
    public SpSkuStock insert(SpSkuStock spSkuStock) {
        this.spSkuStockDao.insert(spSkuStock);
        return spSkuStock;
    }

    /**
     * 修改数据
     *
     * @param spSkuStock 实例对象
     * @return 实例对象
     */
    @Override
    public SpSkuStock update(SpSkuStock spSkuStock) {
        this.spSkuStockDao.update(spSkuStock);
        return this.queryById(spSkuStock.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.spSkuStockDao.deleteById(id) > 0;
    }
}
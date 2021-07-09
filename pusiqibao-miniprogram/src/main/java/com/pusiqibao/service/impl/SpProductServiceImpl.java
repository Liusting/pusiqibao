package com.washcar.service.impl;

import com.washcar.dao.SpProductDao;
import com.washcar.entity.SpProduct;
import com.washcar.service.SpProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SpProduct)表服务实现类
 *
 * @author makejava
 * @since 2021-06-29 10:33:16
 */
@Service("spProductService")
public class SpProductServiceImpl implements SpProductService {
    @Resource
    private SpProductDao spProductDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SpProduct queryById(Long id) {
        return this.spProductDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SpProduct> queryAllByLimit(int offset, int limit) {
        return this.spProductDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param spProduct 实例对象
     * @return 实例对象
     */
    @Override
    public SpProduct insert(SpProduct spProduct) {
        this.spProductDao.insert(spProduct);
        return spProduct;
    }

    /**
     * 修改数据
     *
     * @param spProduct 实例对象
     * @return 实例对象
     */
    @Override
    public SpProduct update(SpProduct spProduct) {
        this.spProductDao.update(spProduct);
        return this.queryById(spProduct.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.spProductDao.deleteById(id) > 0;
    }
}
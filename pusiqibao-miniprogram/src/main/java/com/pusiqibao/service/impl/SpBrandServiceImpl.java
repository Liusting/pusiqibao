package com.pusiqibao.service.impl;

import com.pusiqibao.dao.SpBrandDao;
import com.pusiqibao.entity.SpBrand;
import com.pusiqibao.service.SpBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌表(SpBrand)表服务实现类
 *
 * @author makejava
 * @since 2021-07-09 17:14:58
 */
@Service("spBrandService")
public class SpBrandServiceImpl implements SpBrandService {
    @Resource
    private SpBrandDao spBrandDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SpBrand queryById(Long id) {
        return this.spBrandDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SpBrand> queryAllByLimit(int offset, int limit) {
        return this.spBrandDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param spBrand 实例对象
     * @return 实例对象
     */
    @Override
    public SpBrand insert(SpBrand spBrand) {
        this.spBrandDao.insert(spBrand);
        return spBrand;
    }

    /**
     * 修改数据
     *
     * @param spBrand 实例对象
     * @return 实例对象
     */
    @Override
    public SpBrand update(SpBrand spBrand) {
        this.spBrandDao.update(spBrand);
        return this.queryById(spBrand.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.spBrandDao.deleteById(id) > 0;
    }
}
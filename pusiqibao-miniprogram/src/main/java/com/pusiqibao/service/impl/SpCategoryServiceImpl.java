package com.pusiqibao.service.impl;

import com.pusiqibao.dao.SpCategoryDao;
import com.pusiqibao.entity.SpCategory;
import com.pusiqibao.service.SpCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SpCategory)表服务实现类
 *
 * @author makejava
 * @since 2021-06-24 10:57:37
 */
@Service("spCategoryService")
public class SpCategoryServiceImpl implements SpCategoryService {
    @Resource
    private SpCategoryDao spCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SpCategory queryById(Long id) {
        return this.spCategoryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SpCategory> queryAllByLimit(int offset, int limit) {
        return this.spCategoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param spCategory 实例对象
     * @return 实例对象
     */
    @Override
    public SpCategory insert(SpCategory spCategory) {
        this.spCategoryDao.insert(spCategory);
        return spCategory;
    }

    /**
     * 修改数据
     *
     * @param spCategory 实例对象
     * @return 实例对象
     */
    @Override
    public SpCategory update(SpCategory spCategory) {
        this.spCategoryDao.update(spCategory);
        return this.queryById(spCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.spCategoryDao.deleteById(id) > 0;
    }

    @Override
    public List<SpCategory> queryAllBy() {
        return this.spCategoryDao.queryAll();
    }
}
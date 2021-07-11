package com.pusiqibao.service.impl;

import com.pusiqibao.dao.SpCouponDao;
import com.pusiqibao.entity.SpCoupon;
import com.pusiqibao.service.SpCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 优惠券表(SpCoupon)表服务实现类
 *
 * @author makejava
 * @since 2021-06-30 16:39:31
 */
@Service("spCouponService")
public class SpCouponServiceImpl implements SpCouponService {
    @Resource
    private SpCouponDao spCouponDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SpCoupon queryById(Long id) {
        return this.spCouponDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SpCoupon> queryAllByLimit(int offset, int limit) {
        return this.spCouponDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param spCoupon 实例对象
     * @return 实例对象
     */
    @Override
    public SpCoupon insert(SpCoupon spCoupon) {
        this.spCouponDao.insert(spCoupon);
        return spCoupon;
    }

    /**
     * 修改数据
     *
     * @param spCoupon 实例对象
     * @return 实例对象
     */
    @Override
    public SpCoupon update(SpCoupon spCoupon) {
        this.spCouponDao.update(spCoupon);
        return this.queryById(spCoupon.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.spCouponDao.deleteById(id) > 0;
    }
}
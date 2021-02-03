package com.example.wetchat.controller;

import com.example.wetchat.config.HttpStatusEnum;
import com.example.wetchat.config.Result;
import com.example.wetchat.dao.SpAddressDao;
import com.example.wetchat.entity.SpAddress;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * (SpAddress)表控制层
 *
 * @author makejava
 * @since 2021-02-01 10:46:53
 */
@RestController
@RequestMapping("spAddress")
public class SpAddressController {
    /**
     * 服务对象
     */
    @Autowired
    private SpAddressDao spAddressDao;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Result selectOne(String id) {
        return Result.success(HttpStatusEnum.OK,spAddressDao.queryById(id));
    }

    @PostMapping("/getAddressList")
    public PageInfo getAddressList(int page, int pageSize, String userId) {
        PageHelper.startPage(page, pageSize);
        List<SpAddress> list = spAddressDao.queryAllByUserId(userId);
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @PostMapping("/add")
    public void updateAndAddAddress(SpAddress spAddress){
        if(StringUtils.isEmpty(spAddress.getId())){
            spAddressDao.insert(spAddress);
        }else {
            spAddressDao.update(spAddress);
        }
    }

    @PostMapping("delete")
    public void deleteAddress(String id){
        spAddressDao.deleteById(id);
    }

}
package com.ruoyi.web.controller.tool;

import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("用户信息管理")
@RestController
@RequestMapping("/park")
public class parkController {
    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public AjaxResult userList()
    {
        Map map = new HashMap();
        map.put("id",1);
        map.put("name","lguiting");

        List userList = new ArrayList();
        userList.add(map);
        return AjaxResult.success(userList);
    }

}

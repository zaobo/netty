package com.zab.netty.protal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.Users;
import com.zab.netty.protal.enums.SysCodeMsg;
import com.zab.netty.protal.service.IUsersService;
import com.zab.netty.protal.utils.JudgeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @PostMapping("registOrLogin")
    public ReturnData registOrLogin(@RequestBody Users users) {
        // 判断用户名密码不能为空
        if (StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())) {
            return ReturnData.errorMsg("用户名密码不能为空");
        }

        return usersService.registOrLogin(users);
    }

}

package com.zab.netty.protal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.Users;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
public interface IUsersService extends IService<Users> {

    ReturnData registOrLogin(Users users);

    ReturnData update(Users user);

    ReturnData search(String myUserId, String friendUsername);

    ReturnData addFriendRequest(String myUserId, String friendUsername);
}

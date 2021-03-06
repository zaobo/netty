package com.zab.netty.protal.service;

import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.MyFriends;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zab.netty.protal.vo.MyFriendsVO;

import java.util.List;

/**
 * <p>
 * 用户和朋友的关联表 服务类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
public interface IMyFriendsService extends IService<MyFriends> {

    List<MyFriendsVO> queryMyFriends(String userId);
}

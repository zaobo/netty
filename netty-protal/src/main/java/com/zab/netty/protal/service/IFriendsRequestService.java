package com.zab.netty.protal.service;

import com.zab.netty.protal.entity.FriendsRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zab.netty.protal.vo.FriendRequestVO;

import java.util.List;

/**
 * <p>
 * 用户请求表 服务类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
public interface IFriendsRequestService extends IService<FriendsRequest> {
    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    void operFriendRequest(String acceptUserId, String sendUserId, Integer operType);
}

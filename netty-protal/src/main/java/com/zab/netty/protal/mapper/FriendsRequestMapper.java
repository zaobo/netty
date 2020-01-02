package com.zab.netty.protal.mapper;

import com.zab.netty.protal.entity.FriendsRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zab.netty.protal.vo.FriendRequestVO;

import java.util.List;

/**
 * <p>
 * 用户请求表 Mapper 接口
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
public interface FriendsRequestMapper extends BaseMapper<FriendsRequest> {

    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

}

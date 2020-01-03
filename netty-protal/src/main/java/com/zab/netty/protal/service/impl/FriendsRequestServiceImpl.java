package com.zab.netty.protal.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.FriendsRequest;
import com.zab.netty.protal.entity.MyFriends;
import com.zab.netty.protal.enums.OperatorFriendRequestTypeEnum;
import com.zab.netty.protal.idwork.Sid;
import com.zab.netty.protal.mapper.FriendsRequestMapper;
import com.zab.netty.protal.mapper.MyFriendsMapper;
import com.zab.netty.protal.service.IFriendsRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zab.netty.protal.vo.FriendRequestVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户请求表 服务实现类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Service
public class FriendsRequestServiceImpl extends ServiceImpl<FriendsRequestMapper, FriendsRequest> implements IFriendsRequestService {

    @Resource
    private FriendsRequestMapper friendsRequestMapper;
    @Resource
    private MyFriendsMapper myFriendsMapper;
    @Resource
    private Sid sid;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return friendsRequestMapper.queryFriendRequestList(acceptUserId);
    }

    @Transactional
    @Override
    public ReturnData operFriendRequest(String acceptUserId, String sendUserId, Integer operType) {
        // 判断如果是忽略好友请求，则直接删除好友请求的数据库表记录
        if (operType == OperatorFriendRequestTypeEnum.IGNORE.type) {
            deleteFriendRequest(acceptUserId, sendUserId);
        } else if (operType == OperatorFriendRequestTypeEnum.PASS.type) {
            // 1.通过好友请求，则直接添加到我的好友表中
            saveFriend(acceptUserId, sendUserId);
            // 2.逆向添加好友，就是你加了我，我也要加你
            saveFriend(sendUserId, acceptUserId);
            // 3.删除好友请求表
            deleteFriendRequest(acceptUserId, sendUserId);
        }

        // 4.查询好友列表
        return ReturnData.ok(myFriendsMapper.queryMyFriends(acceptUserId));
    }

    private void deleteFriendRequest(String acceptUserId, String sendUserId) {
        QueryWrapper<FriendsRequest> requestWrapper = new QueryWrapper<>();
        requestWrapper.eq("accept_user_id", acceptUserId);
        requestWrapper.eq("send_user_id", sendUserId);
        remove(requestWrapper);
    }

    private void saveFriend(String acceptUserId, String sendUserId) {
        MyFriends acceptFriend = new MyFriends();
        acceptFriend.setId(sid.nextShort());
        acceptFriend.setMyUserId(acceptUserId);
        acceptFriend.setMyFriendUserId(sendUserId);
        myFriendsMapper.insert(acceptFriend);
    }

}

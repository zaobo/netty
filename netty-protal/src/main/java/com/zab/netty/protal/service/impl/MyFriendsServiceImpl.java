package com.zab.netty.protal.service.impl;

import com.zab.netty.protal.entity.MyFriends;
import com.zab.netty.protal.mapper.MyFriendsMapper;
import com.zab.netty.protal.service.IMyFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和朋友的关联表 服务实现类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Service
public class MyFriendsServiceImpl extends ServiceImpl<MyFriendsMapper, MyFriends> implements IMyFriendsService {

}

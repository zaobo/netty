package com.zab.netty.protal.service.impl;

import com.zab.netty.protal.entity.FriendsRequest;
import com.zab.netty.protal.mapper.FriendsRequestMapper;
import com.zab.netty.protal.service.IFriendsRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

package com.zab.netty.protal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.MyFriends;
import com.zab.netty.protal.mapper.MyFriendsMapper;
import com.zab.netty.protal.service.IMyFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zab.netty.protal.vo.MyFriendsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private MyFriendsMapper myFriendsMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<MyFriendsVO> queryMyFriends(String userId) {
        return myFriendsMapper.queryMyFriends(userId);
    }
}

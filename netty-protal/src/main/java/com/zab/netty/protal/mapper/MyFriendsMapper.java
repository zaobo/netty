package com.zab.netty.protal.mapper;

import com.zab.netty.protal.entity.MyFriends;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zab.netty.protal.vo.MyFriendsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户和朋友的关联表 Mapper 接口
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
public interface MyFriendsMapper extends BaseMapper<MyFriends> {

    List<MyFriendsVO> queryMyFriends(@Param(value = "userId") String userId);
}

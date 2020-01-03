package com.zab.netty.protal.mapper;

import com.zab.netty.protal.entity.ChatMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 聊天记录表 Mapper 接口
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
public interface ChatMsgMapper extends BaseMapper<ChatMsg> {

    void batchUpdateMsgSigned(@Param(value = "msgIdList") List<String> msgIdList);

}

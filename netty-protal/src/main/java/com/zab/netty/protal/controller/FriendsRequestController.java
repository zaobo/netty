package com.zab.netty.protal.controller;


import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.enums.OperatorFriendRequestTypeEnum;
import com.zab.netty.protal.service.IFriendsRequestService;
import com.zab.netty.protal.utils.JudgeUtil;
import com.zab.netty.protal.vo.FriendRequestVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户请求表 前端控制器
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/friendsrequest")
public class FriendsRequestController {

    @Autowired
    private IFriendsRequestService friendsRequestService;

    /**
     * 查询用户接收到的用户请求
     *
     * @param acceptUserId
     * @return
     */
    @GetMapping("queryFriendRequestList/{acceptUserId}")
    public ReturnData queryFriendRequestList(@PathVariable String acceptUserId) {
        if (StringUtils.isBlank(acceptUserId)) {
            return ReturnData.errorMsg("用户id不能为空");
        }
        return ReturnData.ok(friendsRequestService.queryFriendRequestList(acceptUserId));
    }

    /**
     * 接收方通过或忽略朋友请求
     *
     * @param acceptUserId
     * @return
     */
    @GetMapping("operFriendRequest/{acceptUserId}/{sendUserId}/{operType}")
    public ReturnData operFriendRequest(@PathVariable String acceptUserId, @PathVariable String sendUserId, @PathVariable Integer operType) {
        if (StringUtils.isBlank(acceptUserId)) {
            return ReturnData.errorMsg("用户id不能为空");
        }
        if (StringUtils.isBlank(acceptUserId)) {
            return ReturnData.errorMsg("朋友id不能为空");
        }
        if (null == operType) {
            return ReturnData.errorMsg("类型不能为空");
        }

        // 如果operTyep没有对应的枚举值，则直接抛出错误信息
        if (null == OperatorFriendRequestTypeEnum.getMsgByType(operType)) {
            return ReturnData.errorMsg("没有对应的操作符");
        }
        return friendsRequestService.operFriendRequest(acceptUserId, sendUserId, operType);
    }

}

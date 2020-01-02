package com.zab.netty.protal.controller;


import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.service.IMyFriendsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户和朋友的关联表 前端控制器
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/myfriends")
public class MyFriendsController {

    @Autowired
    private IMyFriendsService myFriendsService;

    /**
     * 查询我的好友列表
     * @param userId
     * @return
     */
    @GetMapping("myFriends/{userId}")
    public ReturnData myFriends(@PathVariable String userId) {
        if(StringUtils.isBlank(userId)){
            return ReturnData.errorMsg("用户编号不能为空");
        }

        return ReturnData.ok(myFriendsService.queryMyFriends(userId));
    }

}

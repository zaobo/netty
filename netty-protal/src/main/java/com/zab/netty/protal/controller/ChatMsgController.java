package com.zab.netty.protal.controller;


import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.service.IChatMsgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 聊天记录表 前端控制器
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/chatmsg")
public class ChatMsgController {

    @Autowired
    private IChatMsgService chatMsgService;

    /**
     * 用户手机端获取未签收的消息列表
     *
     * @param acceptUserId
     * @return
     */
    @GetMapping("/getUnReadMgsList/{acceptUserId}")
    public ReturnData getUnReadMgsList(@PathVariable String acceptUserId) {
        if (StringUtils.isBlank(acceptUserId)) {
            return ReturnData.errorMsg("用户id不能为空");
        }

        return ReturnData.ok(chatMsgService.getUnReadMgsList(acceptUserId));
    }

}

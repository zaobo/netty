package com.zab.netty.protal.controller;


import com.zab.netty.protal.bo.UsersBO;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.Users;
import com.zab.netty.protal.enums.SearchFriendsStatusEnum;
import com.zab.netty.protal.service.IUsersService;
import com.zab.netty.protal.utils.FastDFSClient;
import com.zab.netty.protal.utils.FileUtils;
import com.zab.netty.protal.utils.JudgeUtil;
import com.zab.netty.protal.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {

    @Autowired
    private IUsersService usersService;
    @Value("${file.temp.path}")
    private String temppath;
    @Autowired
    private FastDFSClient fastDFSClient;

    @PostMapping("registOrLogin")
    public ReturnData registOrLogin(@RequestBody Users users) {
        // 判断用户名密码不能为空
        if (StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())) {
            return ReturnData.errorMsg("用户名密码不能为空");
        }

        return usersService.registOrLogin(users);
    }

    @PostMapping("uploadFaceBase64")
    public ReturnData uploadFaceBase64(@RequestBody UsersBO usersBO) throws Exception {

        // 获取前端传过来的base64字符串，然后转换为文件对象再上传
        String base64Data = usersBO.getFaceData();
        String path = temppath + usersBO.getUserId() + "userface64.png";
        FileUtils.base64ToFile(path, base64Data);

        // 上传文件到fastdfs
        MultipartFile faceFile = FileUtils.fileToMultipart(path);
        String storePath = fastDFSClient.uploadBase64(faceFile);
        log.info(storePath);

        org.apache.commons.io.FileUtils.forceDelete(new File(path));

        // 获取缩略图的url
        String thump = "_80x80.";
        String[] arr = storePath.split("\\.");
        String thumpImaUrl = arr[0] + thump + arr[1];

        // 更新用户头像
        Users user = new Users();
        user.setId(usersBO.getUserId());
        user.setFaceImage(thumpImaUrl);
        user.setFaceImageBig(storePath);
        return usersService.update(user);

    }

    @PostMapping("setNickname")
    public ReturnData setNickname(@RequestBody UsersBO usersBO) {
        String nickname = usersBO.getNickname();
        if (StringUtils.isBlank(nickname)) {
            return ReturnData.errorMsg("昵称不能为空!");
        }

        if (StringUtils.length(nickname) > 8) {
            return ReturnData.errorMsg("昵称的长度不能超过8个字符");
        }

        Users user = new Users();
        user.setId(usersBO.getUserId());
        user.setNickname(nickname);
        return usersService.update(user);
    }

    /**
     * 搜索好友接口，根据账号做匹配查询，而不能是模糊查询
     *
     * @param myUserId
     * @param friendUsername
     * @return
     */
    @GetMapping("search")
    public ReturnData searchUser(@RequestParam String myUserId, @RequestParam String friendUsername) {
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return ReturnData.errorMsg("查询失败");
        }

        return usersService.search(myUserId, friendUsername);

    }

    /**
     * 添加好友
     *
     * @param myUserId
     * @param friendUsername
     * @return
     */
    @PostMapping("addFriendRequest")
    public ReturnData addFriendRequest(@RequestParam String myUserId, @RequestParam String friendUsername) {
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return ReturnData.errorMsg("查询失败");
        }

        usersService.addFriendRequest(myUserId, friendUsername);
        return ReturnData.ok();

    }

}

package com.zab.netty.protal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.FriendsRequest;
import com.zab.netty.protal.entity.MyFriends;
import com.zab.netty.protal.entity.Users;
import com.zab.netty.protal.enums.SearchFriendsStatusEnum;
import com.zab.netty.protal.exceptions.FastDFSException;
import com.zab.netty.protal.exceptions.WrongArgumentException;
import com.zab.netty.protal.idwork.Sid;
import com.zab.netty.protal.mapper.FriendsRequestMapper;
import com.zab.netty.protal.mapper.MyFriendsMapper;
import com.zab.netty.protal.mapper.UsersMapper;
import com.zab.netty.protal.service.IUsersService;
import com.zab.netty.protal.utils.*;
import com.zab.netty.protal.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Resource
    private Sid sid;
    @Resource
    private QRCodeUtils qrCodeUtils;
    @Value("${file.temp.qrcode}")
    private String qrcode;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Resource
    private MyFriendsMapper myFriendsMapper;
    @Resource
    private FriendsRequestMapper friendsRequestMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ReturnData registOrLogin(Users users) {
        // 判断用户名是否存在，如果存在就登陆，不存在就注册
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", users.getUsername());
        Users result;
        String password = MD5Utils.MD5EncodeUtf8(Base64Util.strDecode(users.getPassword()));
        if (!JudgeUtil.isDBNull(count(queryWrapper))) {
            // 登陆
            queryWrapper.eq("password", password);
            result = getOne(queryWrapper);
            if (null == result) {
                return ReturnData.errorMsg("用户名或密码错误");
            }
        } else {
            String userId = sid.nextShort();
            // 注册
            users.setNickname(users.getUsername());
            users.setFaceImage("");
            users.setFaceImageBig("");
            users.setPassword(password);

            // 为每个用户生成二维码
            String qrCodePath = qrcode + userId + "qrcode.png";
            // openxin_qrcode:[username]
            qrCodeUtils.createQRCode(qrCodePath, "openxin_qrcode:" + users.getUsername());
            MultipartFile qrcodeFile = FileUtils.fileToMultipart(qrCodePath);

            String qrCodeUrl;
            try {
                qrCodeUrl = fastDFSClient.uploadQRCode(qrcodeFile);
                org.apache.commons.io.FileUtils.forceDelete(new File(qrCodePath));
            } catch (Exception e) {
                log.error("上传失败", e);
                return ReturnData.errorMsg("上传失败");
            }

            users.setQrcode(qrCodeUrl);

            users.setId(userId);
            save(users);
            result = users;
        }

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(result, usersVO);
        return ReturnData.ok(usersVO);
    }

    @Override
    @Transactional
    public ReturnData update(Users user) {
        updateById(user);
        Users result = getById(user.getId());
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(result, usersVO);
        return ReturnData.ok(usersVO);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ReturnData search(String myUserId, String friendUsername) {
        Users friend = findFriend(friendUsername);
        ReturnData returnData = check(myUserId, friend);
        if (null != returnData) {
            return returnData;
        }
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(friend, usersVO);
        return ReturnData.ok(usersVO);
    }

    @Override
    @Transactional
    public ReturnData addFriendRequest(String myUserId, String friendUsername) {
        Users friend = findFriend(friendUsername);
        ReturnData returnData = check(myUserId, friend);
        if (null != returnData) {
            return returnData;
        }
        // 1.查询发送好友请求记录表
        QueryWrapper<FriendsRequest> friendsRequestQueryWrapper = new QueryWrapper<>();
        friendsRequestQueryWrapper.eq("send_user_id", myUserId);
        friendsRequestQueryWrapper.eq("accept_user_id", friend.getId());
        FriendsRequest friendsRequest = friendsRequestMapper.selectOne(friendsRequestQueryWrapper);

        if (null == friendsRequest) {
            // 如果不是你的好友，并且好友记录没有添加，则新增好友请求记录
            String requestId = sid.nextShort();
            friendsRequest = new FriendsRequest();
            friendsRequest.setId(requestId);
            friendsRequest.setSendUserId(myUserId);
            friendsRequest.setAcceptUserId(friend.getId());
            friendsRequest.setRequestDateTime(new Date());
            friendsRequestMapper.insert(friendsRequest);
        }
        return ReturnData.ok();
    }

    private ReturnData check(String myUserId, Users friend) {
        if (null == friend) {
            // 1.搜索的用户如果不存在，返回无此用户
            return ReturnData.errorMsg(SearchFriendsStatusEnum.USER_NOT_EXIST.msg);
        }
        if (StringUtils.equalsIgnoreCase(myUserId, friend.getId())) {
            // 2.搜索的账号是自己，返回不能添加自己
            return ReturnData.errorMsg(SearchFriendsStatusEnum.NOT_YOURSELF.msg);
        }

        QueryWrapper<MyFriends> myFriendsWrapper = new QueryWrapper<>();
        myFriendsWrapper.eq("my_user_id", myUserId);
        myFriendsWrapper.eq("my_friend_user_id", friend.getId());
        int count = myFriendsMapper.selectCount(myFriendsWrapper);
        // 3.搜索的用户已经是你的好友了，返回已添加此好友
        if (count > 0) {
            return ReturnData.errorMsg(SearchFriendsStatusEnum.ALREADY_FRIENDS.msg);
        }
        return null;
    }

    private Users findFriend(String friendUsername) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", friendUsername);
        return getOne(queryWrapper);
    }

}

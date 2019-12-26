package com.zab.netty.protal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.Users;
import com.zab.netty.protal.exceptions.FastDFSException;
import com.zab.netty.protal.idwork.Sid;
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
import java.util.Base64;

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
                throw new FastDFSException("上传失败", e);
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

}

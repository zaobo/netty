package com.zab.netty.protal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.entity.Users;
import com.zab.netty.protal.idwork.Sid;
import com.zab.netty.protal.mapper.UsersMapper;
import com.zab.netty.protal.service.IUsersService;
import com.zab.netty.protal.utils.Base64Util;
import com.zab.netty.protal.utils.JudgeUtil;
import com.zab.netty.protal.utils.MD5Utils;
import com.zab.netty.protal.vo.UsersVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Resource
    private Sid sid;

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
            // 注册
            users.setNickname(users.getUsername());
            users.setFaceImage("");
            users.setFaceImageBig("");
            users.setPassword(password);

            // 为每个用户生成二维码
            users.setQrcode("");
            users.setId(sid.nextShort());
            save(users);
            result = users;
        }

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(result, usersVO);
        return ReturnData.ok(usersVO);
    }

}

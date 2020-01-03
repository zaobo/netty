package com.zab.netty.protal;

import com.alibaba.fastjson.JSON;
import com.zab.netty.protal.service.IChatMsgService;
import com.zab.netty.protal.service.IFriendsRequestService;
import com.zab.netty.protal.service.IMyFriendsService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProtalApplicationTest {

    @Autowired
    private IMyFriendsService myFriendsService;
    @Autowired
    private IChatMsgService chatMsgService;

    @Test
    public void contextLoads() {
        chatMsgService.updateMsgSigned(Lists.list("1"));
    }

    public static void main(String[] args) {
        System.err.println("M00/00/00/wKiycF4Ep2iADzHEAAABoy_nrCU236.png".length());
    }

}

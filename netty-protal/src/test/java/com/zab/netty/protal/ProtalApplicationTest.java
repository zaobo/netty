package com.zab.netty.protal;

import com.alibaba.fastjson.JSON;
import com.zab.netty.protal.service.IFriendsRequestService;
import com.zab.netty.protal.service.IMyFriendsService;
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

    @Test
    public void contextLoads() {
        System.err.println(JSON.toJSONString(myFriendsService.queryMyFriends("191230AZ7A071BF8")));
    }

    public static void main(String[] args) {
        System.err.println("M00/00/00/wKiycF4Ep2iADzHEAAABoy_nrCU236.png".length());
    }

}

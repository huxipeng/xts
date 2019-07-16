package com.mogo.xts;

import com.mogo.xts.enums.ActionEnum;
import com.mogo.xts.netty.client.DefaultFuture;
import com.mogo.xts.netty.client.NettyClient;
import com.mogo.xts.netty.client.SocketManager;
import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.netty.protobuf.ChannelResponseProto;
import com.mogo.xts.utils.KidUtils;
import com.mogo.xts.utils.SocketUtils;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class
XtsApplicationTests {
	@Test
	public void sendMsg() throws Exception{
		/*ChannelRequestProto.ChannelRequest.Builder builder = ChannelRequestProto.ChannelRequest.newBuilder();
		builder.setAction(ActionEnum.TEST.getAction());
		builder.setRequestId(KidUtils.generateShortUuid());
		builder.setContext(ActionEnum.TEST.getAction());
		ChannelResponseProto.ChannelResponse response = NettyClient.send(builder.build());
		System.out.println(response);*/
	}

}

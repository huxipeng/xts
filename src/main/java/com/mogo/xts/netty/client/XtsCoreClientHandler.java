package com.mogo.xts.netty.client;

import com.mogo.xts.enums.ActionEnum;
import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.netty.protobuf.ChannelResponseProto;
import com.mogo.xts.utils.KidUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xipeng
 **/
@Slf4j
@ChannelHandler.Sharable
public class XtsCoreClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client receive msg " + msg);
        ChannelResponseProto.ChannelResponse response = (ChannelResponseProto.ChannelResponse) msg;
        DefaultFuture future = new DefaultFuture(response.getRequestId());
        DefaultFuture.receive(response);
        System.out.println(future.get());
        ctx.fireChannelRead(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketManager.getInstance().setCtx(ctx);

        ChannelRequestProto.ChannelRequest.Builder builder = ChannelRequestProto.ChannelRequest.newBuilder();
        builder.setAction(ActionEnum.TEST.getAction());
        builder.setRequestId(KidUtils.generateShortUuid());
        builder.setContext(ActionEnum.TEST.getAction());
        SocketManager.getInstance().sendMsg(builder.build());
        ctx.fireChannelActive();
    }
}

package com.mogo.xts.netty.idle;

import com.mogo.xts.enums.ActionEnum;
import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.utils.SocketUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Xipeng
 **/
@Slf4j
@ChannelHandler.Sharable
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChannelRequestProto.ChannelRequest channelRequest = (ChannelRequestProto.ChannelRequest)msg;
        if(channelRequest != null && StringUtils.equalsIgnoreCase(channelRequest.getAction(), ActionEnum.HEART_BEAT.getAction())){
             log.info("xts server heart channelRead ..");
             log.info(ctx.channel().remoteAddress() + " -> Server : " + channelRequest.getContext());
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent)evt).state();
            if(state == IdleState.READER_IDLE) { // 如果出现读空闲，则认为客户端挂了，需要重连
                throw new Exception("reader_idle exception");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}

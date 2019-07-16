package com.mogo.xts.netty.idle;

import com.mogo.xts.enums.ActionEnum;
import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.netty.protobuf.ChannelResponseProto;
import com.mogo.xts.utils.KidUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author Xipeng
 **/
@Slf4j
@ChannelHandler.Sharable
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
            CharsetUtil.UTF_8));

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("激活时间是："+new Date());
        log.info("HeartBeatClientHandler channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("停止时间是："+new Date());
        log.info("HeartBeatClientHandler channelInactive");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 为了减少网络消耗，此处不会接收到任何服务端对于心跳的任何响应
        ChannelResponseProto.ChannelResponse channelResponse = (ChannelResponseProto.ChannelResponse) msg;
        if(channelResponse != null && StringUtils.equalsIgnoreCase(channelResponse.getAction(), ActionEnum.HEART_BEAT.getAction())) {
            log.info("xts client heart channelRead ..");
        } else {
            ctx.fireChannelRead(msg);
        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(createChannelRequest());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private ChannelRequestProto.ChannelRequest createChannelRequest() {
        ChannelRequestProto.ChannelRequest.Builder builder = ChannelRequestProto.ChannelRequest.newBuilder();
        builder.setAction(ActionEnum.HEART_BEAT.getAction());
        builder.setRequestId(KidUtils.generateShortUuid());
        builder.setContext(ActionEnum.HEART_BEAT.getAction());
        return builder.build();
    }
}

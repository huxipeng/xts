package com.mogo.xts.utils;

import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.netty.protobuf.ChannelResponseProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author Xipeng
 **/
public class SocketUtils {

    public static void sendMsg(final ChannelHandlerContext ctx, final ChannelResponseProto.ChannelResponse msg) {
        ctx.writeAndFlush(msg);
    }

    public static void sendMsg(final ChannelHandlerContext ctx, final ChannelRequestProto.ChannelRequest msg) {
        ctx.writeAndFlush(msg);
    }


}

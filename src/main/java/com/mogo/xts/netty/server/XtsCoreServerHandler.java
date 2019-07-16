package com.mogo.xts.netty.server;

import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.netty.protobuf.ChannelResponseProto;
import com.mogo.xts.utils.SocketUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Xipeng
 **/
@Slf4j
@ChannelHandler.Sharable
public class XtsCoreServerHandler extends ChannelInboundHandlerAdapter {

    private static final int max_size = 100;

    private static ExecutorService executorService = Executors.newFixedThreadPool(max_size);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("xts server receive " + msg);
        ChannelRequestProto.ChannelRequest request = (ChannelRequestProto.ChannelRequest) msg;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                SocketUtils.sendMsg(ctx, createResponse(request));
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private ChannelResponseProto.ChannelResponse createResponse(ChannelRequestProto.ChannelRequest request) {
        ChannelResponseProto.ChannelResponse.Builder builder =  ChannelResponseProto.ChannelResponse.newBuilder();
        builder.setRequestId(request.getRequestId());
        builder.setAction(request.getAction());
        builder.setContext(request.getContext());
        builder.setRetMsg("isOk");
        return builder.build();
    }
}

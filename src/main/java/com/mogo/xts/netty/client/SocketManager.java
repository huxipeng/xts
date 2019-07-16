package com.mogo.xts.netty.client;

import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.utils.SocketUtils;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 发送数据
 * @author Xipeng
 **/
public class SocketManager {

    private static final int max_size = 50;

    private ChannelHandlerContext ctx;

    private static ExecutorService executorService = Executors.newFixedThreadPool(max_size);

    private static class HolderClass {
        private final static SocketManager instance = new SocketManager();
    }

    public static SocketManager getInstance() {
        return HolderClass.instance;
    }

    private SocketManager() {

    }

    public void sendMsg(ChannelRequestProto.ChannelRequest channelRequest) throws Exception {

        if(channelRequest == null) {
            return;
        }

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                SocketUtils.sendMsg(ctx, channelRequest);
            }
        });

    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}

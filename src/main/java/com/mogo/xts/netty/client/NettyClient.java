package com.mogo.xts.netty.client;

import com.mogo.xts.netty.idle.HeartBeatClientHandler;
import com.mogo.xts.netty.idle.NettyConnectionWatchDog;
import com.mogo.xts.netty.protobuf.ChannelRequestProto;
import com.mogo.xts.netty.protobuf.ChannelResponseProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author Xipeng
 **/
public class NettyClient {

    private final String host;
    private final int port;

    private Bootstrap b;

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new LoggingHandler(LogLevel.INFO));

            // 定义重连检测狗, 如果netty服务端挂了，则尝试重连
            final NettyConnectionWatchDog watchDog = new NettyConnectionWatchDog(b, timer, port, host, true) {
                @Override
                public ChannelHandler[] handlers() {
                    return new ChannelHandler[]{
                            this,
                            //new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                            new ProtobufVarint32FrameDecoder(),
                            new ProtobufDecoder(ChannelResponseProto.ChannelResponse.getDefaultInstance()),
                            new ProtobufVarint32LengthFieldPrepender(),
                            new ProtobufEncoder(),
                            new HeartBeatClientHandler(),
                            new XtsCoreClientHandler()
                    };
                }
            };

            ChannelFuture future;
            //进行连接
            try {
                synchronized (b) {
                    b.handler(new ChannelInitializer<Channel>() {
                        //初始化channel
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(watchDog.handlers());
                        }
                    });
                    future = b.connect(host,port);
                }

                future.sync();
            } catch (Throwable t) {
                throw new Exception("connects to  fails", t);
            }
        } catch (Exception e){
            group.shutdownGracefully().sync();
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: " + NettyClient.class.getSimpleName() + " <host> <port>");
            return;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new NettyClient(host, port).start();
    }

}

package com.mogo.xts.netty.idle;

import io.netty.channel.ChannelHandler;

/**
 * 客户端的ChannelHandler集合
 * Xipeng
 */
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();

}

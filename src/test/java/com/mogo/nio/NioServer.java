package com.mogo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xipeng
 * @create 2019-03-31 17:25
 **/
public class NioServer {

    private static final Map<String, SocketChannel> clientMap = new ConcurrentHashMap();

    public static void main(String[] args) {

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            serverSocketChannel.bind(new InetSocketAddress(8899));

            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(value -> {
                    try {
                        if(value.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel)value.channel();
                            SocketChannel clientChannel = channel.accept();
                            clientChannel.configureBlocking(false);
                            String clientId = UUID.randomUUID().toString();
                            System.out.println("客户端接入" + clientId);
                            clientMap.put(clientId, clientChannel);
                            clientChannel.register(selector, SelectionKey.OP_READ);
                        } else if(value.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel)value.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int count = clientChannel.read(buffer);
                            if (count > 0) {
                                buffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receiveMsg = String.valueOf(charset.decode(buffer).array());
                                System.out.println("receiveMsg = " +receiveMsg);
                                Iterator<Map.Entry<String, SocketChannel>> it = clientMap.entrySet().iterator();
                                String sendClient = null;
                                while (it.hasNext()) {
                                    Map.Entry<String, SocketChannel> next = it.next();
                                    if(next.getValue() == clientChannel) {
                                        sendClient = next.getKey();
                                        break;
                                    }
                                }
                                it = clientMap.entrySet().iterator();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                while (it.hasNext()) {
                                    SocketChannel socketChannel = it.next().getValue();
                                    writeBuffer.clear();
                                    writeBuffer.put(("sendClient:" + sendClient + "发送了消息").getBytes());
                                    writeBuffer.flip();
                                    socketChannel.write(writeBuffer);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

}

package com.mogo.xts.netty.client;

import com.mogo.xts.netty.protobuf.ChannelResponseProto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Xipeng
 **/
public class DefaultFuture {

    private static final Map<String, DefaultFuture> defaultFutures = new ConcurrentHashMap<String, DefaultFuture>();

    public Lock lock = new ReentrantLock();

    private Condition isEmpty = lock.newCondition();

    private volatile boolean isDone = false;

    private ChannelResponseProto.ChannelResponse response;

    private static final int delay = 10;

    public DefaultFuture(String requestId) {
        defaultFutures.put(requestId, this);
    }

    public ChannelResponseProto.ChannelResponse get() throws Exception {

        lock.lock();

        try {
            while (!isDone) {
                isEmpty.await(delay, TimeUnit.MILLISECONDS);
            }
            return this.response;
        } finally {
            lock.unlock();
        }
    }

    public static void receive(ChannelResponseProto.ChannelResponse response) {
        DefaultFuture future = defaultFutures.get(response.getRequestId());
        if (future != null) {
            Lock lock = future.lock;
            lock.lock();
            try {
                future.setResponse(response);
                future.isDone = true;
                future.isEmpty.signal();
                defaultFutures.remove(future);
            } catch (Exception e) {
                throw e;
            } finally {
                lock.unlock();
            }
        }
    }

    public ChannelResponseProto.ChannelResponse getResponse() {
        return response;
    }

    public void setResponse(ChannelResponseProto.ChannelResponse response) {
        this.response = response;
    }
}

package org.noear.solon.core.message;

import javafx.util.Callback;
import org.noear.solon.core.handle.MethodType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * SocketD 会话（为 Message + Listener 架构服务 ）
 *
 * @author noear
 * @since 1.0
 * */
public interface Session {
    Object real();

    /**
     * 会话ID
     * */
    String sessionId();

    /**
     * 方法
     * */
    MethodType method();
    /**
     * 路径（socket 可能为null）
     * */
    String path();

    /**
     * 发送消息
     * */
    void send(String message);
    /**
     * 发送消息
     * */
    void send(byte[] message);
    /**
     * 发送消息
     * */
    void send(Message message);

    /**
     * 发送消息并等待响应
     * */
    Message sendAndResponse(Message message);

    /**
     * 发送消息并异步回调
     * */
    void sendAndCallback(Message message, BiConsumer<Message,Throwable> callback);

    /**
     * 当前实例监听者（ListenEndpoint 为路径监听者，不限实例）
     * */
    default void listener(Listener listener){}
    /**
     * 当前实例监听者
     * */
    default Listener listener(){return null;}

    /**
     * 关闭会话
     * */
    void close() throws IOException;

    /**
     * 是否是有效的
     * */
    boolean isValid();
    /**
     * 是否是安全的
     * */
    boolean isSecure();

    /**
     * 远程地址
     * */
    InetSocketAddress getRemoteAddress();
    /**
     * 本地地址
     * */
    InetSocketAddress getLocalAddress();

    /**
     * 设置附件
     * */
    void setAttachment(Object obj);
    /**
     * 获取附件
     * */
    <T> T getAttachment();

    /**
     * 获取所有会话
     * */
    Collection<Session> getOpenSessions();
}

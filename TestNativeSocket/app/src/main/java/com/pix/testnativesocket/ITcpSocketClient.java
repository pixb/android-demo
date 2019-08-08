package com.pix.testnativesocket;

/**
 * tcp client客户端接口
 * 为了标准tcp的客户端协议
 * 方便切换tcp底层实现
 * Created by tangpengxiang on 2017/7/20.
 */

public interface ITcpSocketClient {
    /**
     * 设置host和port
     * @param host
     * @param port
     */
    public void setHostAndPort(String host, int port);

    /**
     * 客户端发送报文
     * @param bs
     */
    public void sendData(byte[] bs);

    /**
     * 客户端是否连接
     * @return
     */
    public boolean isConnected();

    /**
     * 连接服务器
     */
    public void connect();

    /**
     * 是否发送完报文再关闭
     * @param flush
     */
    public void close(boolean flush);

    /**
     * 直接关闭
     */
    public void close();

    /**
     * 销毁客户端,释放资源
     */
    public  void destroy();

    /**
     * 设置监听器
     * @param mTcpNioListener
     */
    public void setTcpNioListener(TcpSocketListener mTcpNioListener);
}

package com.pix.testnativesocket;

/**
 * tcp socket监听器
 * Created by tangpengxiang on 2017/7/20.
 */

public interface TcpSocketListener {
    void onConnect(ITcpSocketClient tcpSocketClient);

    void onReceiveData(ITcpSocketClient tcpSocketClient, byte[] data);

    void onConnectError(ITcpSocketClient tcpSocketClient, String message, Throwable throwable);
}

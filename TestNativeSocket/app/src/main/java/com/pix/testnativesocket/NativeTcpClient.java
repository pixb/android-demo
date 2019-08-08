package com.pix.testnativesocket;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/**
 * C/C++实现的Tcp客户端
 * Created by tangpengxiang on 2017/7/21.
 */

public class NativeTcpClient implements ITcpSocketClient{
    private static final String TAG = "NativeTcpClient";
    private static final int TcpEvent_ConnectSuccess = 0;
    private static final int TcpEvent_ConnectError = 1;
    private static final int TcpEvent_SendSuccess = 2;
    private static final int TcpEvent_SendError = 3;
    private static final int TcpEvent_RecvSuccess = 4;
    private static final int TcpEvent_Recv_Error = 5;
    private static final int TcpEvent_InitError = 6;
    private static final int TcpEvent_OnStop = 7;

    private String mHost;
    private int mPort;
    private static volatile ITcpSocketClient singleInstance = null;
    private TcpSocketListener mListener;
    public static ITcpSocketClient getSingleInstance() {
        if (null == singleInstance) {
            singleInstance = new NativeTcpClient();
        }
        return singleInstance;
    }
    static {
        System.loadLibrary("CSocketLib");
    }

    private NativeTcpClient() {
    }

    @Override
    public void setHostAndPort(String host, int port) {
        mHost = host;
        mPort = port;
        TcpSocketClientInit(mHost,mPort);
    }

    @Override
    public void sendData(byte[] bs) {
        SendData(bs,bs.length);
    }

    @Override
    public boolean isConnected() {
        return IsConnect();
    }

    @Override
    public void connect() {
        TcpSocketClientConnect();
    }

    @Override
    public void close(boolean flush) {
        Stop();
    }

    @Override
    public void close() {
        Stop();
    }

    @Override
    public void destroy() {
        Destroy();
    }

    @Override
    public void setTcpNioListener(TcpSocketListener mTcpNioListener) {
        mListener = mTcpNioListener;
    }

    /**
     *  初始化
     * @param host
     * @param port
     */
    public native void TcpSocketClientInit(String host,int port);

    /**
     * 连接服务器TcpSocketClientConnect
     */
    public native void TcpSocketClientConnect();

    /**
     * 停止与服务器连接
     */
    public native void Stop();

    /**
     * 发送数据
     * @param data
     * @param datalen
     */
    public native void SendData(byte [] data,int datalen);

    public native boolean IsConnect();

    public native void Destroy();

    public void TcpEventCallback(int event,byte [] data) {
        Log.d(TAG,"TcpEventCallback(),event:" + event);
        switch (event) {
            case TcpEvent_ConnectSuccess:
                if(mListener != null) {
                    mListener.onConnect(this);
                }
                break;
            case TcpEvent_ConnectError :
                if(mListener != null) {
                    mListener.onConnectError(this,"NativeTcpClient connect error!",new IllegalStateException("NativeTcpClient connect error!"));
                }
                break;
            case TcpEvent_SendSuccess :
                break;
            case TcpEvent_SendError:
                break;
            case TcpEvent_RecvSuccess:
                Log.d(TAG,"TcpEventCallback(),TcpEvent_RecvSuccess,data:" + new String(data));
                if(mListener != null ) {
                    mListener.onReceiveData(this,data);
                }
                break;
            case TcpEvent_Recv_Error:
                break;
            case TcpEvent_InitError:
                break;
            case TcpEvent_OnStop:

                break;
        }
    }
}
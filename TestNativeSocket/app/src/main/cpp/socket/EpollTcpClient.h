//
// Created by 唐鹏翔 on 2017/7/21.
//

#ifndef TESTNATIVESOCKET_EPOLLTCPCLIENT_H
#define TESTNATIVESOCKET_EPOLLTCPCLIENT_H

#define MAXSIZE     1024 * 4

#ifndef _Nonnull
#define _Nonnull
#endif
#include <pthread.h>
enum TcpEvent
{
    TcpEvent_ConnectSuccess = 0,
    TcpEvent_ConnectError,
    TcpEvent_SendSuccess,
    TcpEvent_SendError,
    TcpEvent_RecvSuccess,
    TcpEvent_RecvError,
    TcpEvent_InitError,
    TcpEvent_OnStop
};

/**
 * Tcp回调函数
 * int event:事件
 * unsigned char * 回调收到数据
 * int * buflen 回调收到数据长度
 */
typedef void (*TcpCallback)(TcpEvent event, char * buf,int buflen);

/**
 * 使用epoll实现的tcp客户端
 */
class EpollTcpClient {
public:
    EpollTcpClient();
    ~EpollTcpClient();
public:
    /**
     * 设置服务器地址和端口
     */
    int SetHostAndPort(const char * host,int port);
    // 初始化客户端,创建套接字
    int Init();
    // 连接函数，连接服务器
    int Connect();
    // 断开连接
    int Stop();
    // 发送数据
    int Send(char * buf,int buflen);
    // 设置回调函数
    void SetCallback(TcpCallback callback);
    // 处理Epoll连接
    friend void HandleConnection(EpollTcpClient * pClient);
    // 处理Epoll事件
    friend int HandleEvent(EpollTcpClient * pClient,struct epoll_event *events,int num,int listenfd);
    // 读取数据
    friend int DoRead(EpollTcpClient * pClient,int fd);
    // 写出数据
    friend int DoWrite(EpollTcpClient * pClient,int fd);
    // 发送buff
    char writeBuf[MAXSIZE];
    int writeBufLen;
    // 是否连接
    bool IsConnect();
private:
    // 套接字
    int sockfd;
    // epoll描述字
    int epollfd;
    // ip地址
    char * pHost;
    // 端口号
    int port;
    // 回调函数
    TcpCallback callback;
    // 连接判断
    bool isConnect;
    // epoll处理线程
    pthread_t epoll_thread;
};

//声明方法
void HandleConnection(EpollTcpClient * pClient);
// 增加epoll处理事件
int AddEvent(int epollfd,int fd,int state);
// 修改epoll事件
static void ModifyEvent(int epollfd,int fd,int state);
// 处理epoll事件
int HandleEvent(EpollTcpClient * pClient,struct epoll_event *events,int num,int listenfd);
// 发送报文
int DoWrite(EpollTcpClient * pClient,int fd);
// 读取报文
int DoRead(EpollTcpClient * pClient,int fd);

#endif //TESTNATIVESOCKET_EPOLLTCPCLIENT_H

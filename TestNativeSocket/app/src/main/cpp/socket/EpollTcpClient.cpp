//
// Created by 唐鹏翔 on 2017/7/21.
//

#include "EpollTcpClient.h"
#include "../utils/LogUtils.h"
#include <iostream>
#include <netinet/in.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <sys/epoll.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>
#include <arpa/inet.h>

// epoll处理连接数
#define EPOLLEVENTS 20
// buffer大小
#define FDSIZE        1024 * 4

EpollTcpClient::EpollTcpClient():sockfd(-1),isConnect(false)
{
    if(pHost) {
        delete[]pHost;
        pHost = NULL;
    }
    pHost = new char[100];
}
EpollTcpClient::~EpollTcpClient()
{
    if(pHost) {
        delete[]pHost;
        pHost = NULL;
    }
}
// 设置端口
int EpollTcpClient::SetHostAndPort(const char * host,int port)
{
    LOGD("FUNC EpollTcpClient::SetHostAndPort(),RUN...\n");
    int ret = 0;
    if(NULL == host || 0 >= port)
    {
        ret = -1;
        LOGW("FUNC EpollTcpClient::SetHostAndPort(),error,(NULL == host || 0 >= port)\n");
        if(this->callback)
        {
            callback(TcpEvent::TcpEvent_InitError,NULL,0);
        }
        return ret;
    }
    strcpy(pHost,host);
    this->port = port;
    return  ret;
}

int EpollTcpClient::Init()
{
    LOGD("FUNC EpollTcpClient::Init(),RUN...\n");
    int ret = 0;
    sockfd = socket(AF_INET,SOCK_STREAM,0);
    if(0 >= sockfd)
    {
        ret = -1;
        LOGW("FUNC EpollTcpClient::Init(),error,(0 >= sockfd)\n");
        if(this->callback)
        {
            callback(TcpEvent::TcpEvent_InitError,NULL,0);
        }
        return ret;
    }
    isConnect = false;
    return ret;
}
// 声明线程方法
void * EpollThread(void * pClient);
// 连接服务器
int EpollTcpClient::Connect()
{
    LOGD("FUNC EpollTcpClient::Connect(),host:%s\n",pHost);
    int ret = 0;
    struct sockaddr_in  servaddr;
    bzero(&servaddr,sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(port);
    ret = inet_pton(AF_INET,pHost,&servaddr.sin_addr);
    if(ret < 0)
    {
        LOGW("FUNC EpollTcpClient::Connect(),error,inet_pton(),ret:%d\n",ret);
        if(this->callback)
        {
            callback(TcpEvent::TcpEvent_ConnectError,NULL,0);
            isConnect = false;
        }
        return ret;
    }
    ret = connect(sockfd,(struct sockaddr*)&servaddr,sizeof(servaddr));
    if(ret < 0)
    {
        LOGW("FUNC EpollTcpClient::Connect(),error,connect(),ret:%d\n",ret);
        if(this->callback)
        {
            callback(TcpEvent::TcpEvent_ConnectError,NULL,0);
            isConnect = false;
        }
        return ret;
    }
    //    HandleConnection(this);
    // 创建epoll执行线程
    pthread_create(&epoll_thread,NULL,EpollThread,this);
    if(this->callback)
    {
        isConnect = true;
        callback(TcpEvent::TcpEvent_ConnectSuccess,NULL,0);
    }
    this->isConnect = true;

    return ret;
}
// 断开连接
int EpollTcpClient::Stop()
{
    LOGD("FUNC EpollTcpClient::Stop()\n");
    int ret = 0;
    this->isConnect = false;
    close(sockfd);
    return ret;
}
// Epoll线程
void * EpollThread(void * _pClient)
{
    if(NULL != _pClient) {
        EpollTcpClient * pClient = (EpollTcpClient*)(_pClient);
        LOGD("FUNC EpollThread(),RUN...,%d\n",pClient->IsConnect());
        HandleConnection(pClient);
    }
}

// 设置回调函数
void EpollTcpClient::SetCallback(TcpCallback callback)
{
    LOGD("FUNC EpollTcpClient::SetCallback(),RUN...\n");
    if(!callback)
    {
        return ;
    }
    this->callback = callback;
}
// 客户端发送报文
int EpollTcpClient::Send(char * buf,int buflen)
{
    LOGD("FUNC EpollTcpClient::Send(),RUN...\n");
    int ret = 0;
    if(!buf || 0>= buflen)
    {
        ret = -1;
        LOGW("FUNC EpollTcpClient::DoRead(),Send() error,ret:%d\n",ret);
        if(this->callback)
        {
            callback(TcpEvent::TcpEvent_SendError,NULL,0);
        }
        return ret;
    }
    if(epollfd < 0)
    {
        ret = -2;
        LOGW("FUNC EpollTcpClient::Send(),error,ret:%d\n",ret);
        if(this->callback)
        {
            callback(TcpEvent::TcpEvent_SendError,NULL,0);
        }
        return ret;
    }
    ModifyEvent(epollfd,sockfd,EPOLLOUT);  // 可写监听
    return ret;
}

bool EpollTcpClient::IsConnect()
{
    return isConnect;
}


// epoll处理连接
void HandleConnection(EpollTcpClient * pClient)
{
    LOGD("FUNC EpollTcpClient::HandleConnection(),RUN...\n");
    int ret = 0;
    // epoll注册的事件集合
    struct epoll_event events[EPOLLEVENTS];
    char buf[MAXSIZE];
    pClient->epollfd = epoll_create(FDSIZE);
    if(pClient->epollfd < 0)
    {
        ret = -1;
        LOGW("FUNC EpollTcpClient::Connect(),error,connect(),ret:%d\n",ret);
        if(pClient->callback)
        {
            pClient->callback(TcpEvent::TcpEvent_ConnectError,NULL,0);
            pClient->isConnect = false;
            pClient->Stop();
        }
        return ;
    }
    AddEvent(pClient->epollfd,pClient->sockfd,EPOLLIN);   // 给sockfd可读的事件监听
    while (pClient->isConnect)
    {
        ret = epoll_wait(pClient->epollfd,events,EPOLLEVENTS,-1); // 开始监听事件
        HandleEvent(pClient,events,ret,pClient->sockfd);
    }
    pClient->isConnect = false;
    close(pClient->epollfd);
    close(pClient->sockfd);
    pthread_exit(NULL);
    if(pClient->callback)
    {
        pClient->callback(TcpEvent::TcpEvent_OnStop,NULL,0);
    }
}

// 处理事件
int HandleEvent(EpollTcpClient * pClient,struct epoll_event *events,int num,int listenfd)
{
    LOGD("FUNC EpollTcpClient::HandleEvent(),RUN...\n");
    int ret = 0;
    int fd;
    int i;
    for (i = 0;i < num;i++) //遍历处理epoll fd事件
    {
        fd = events[i].data.fd; // 第一个事件
        if (events[i].events & EPOLLIN) // 输入事件
            DoRead(pClient,fd);
        else if (events[i].events & EPOLLOUT)
            DoWrite(pClient,fd);
    }
    return ret;
}

// 客户端读取报文
int DoRead(EpollTcpClient * pClient,int fd)
{
    LOGD("FUNC EpollTcpClient::DoRead(),RUN...\n");
    int ret;
    char buff[MAXSIZE];
    // 读取报文
    ret = recv(fd,buff,MAXSIZE,0);
    if (ret == -1)
    {
//        perror("read error:");
        LOGW("FUNC EpollTcpClient::DoRead(),read() error,ret:%d\n",ret);
        close(fd);
        if(pClient->callback)
        {
            pClient->callback(TcpEvent::TcpEvent_RecvError,NULL,0);
        }
        return  ret;
    }
    else if (ret == 0)
    {
        ret = -2;
        LOGW("FUNC EpollTcpClient::DoRead(),read() error,ret:%d\n",ret);
        fprintf(stderr,"server close.\n");
        close(fd);
        if(pClient->callback)
        {
            pClient->callback(TcpEvent::TcpEvent_RecvError,NULL,0);
        }
        return  ret;
    }
    // 收到的数据
    if(pClient->callback)
    {
        pClient->callback(TcpEvent::TcpEvent_RecvSuccess,buff,ret);
    }
    return ret;
}

// 客户端向服务器发报文
int DoWrite(EpollTcpClient * pClient,int fd)
{
    LOGD("FUNC EpollTcpClient::DoWrite(),RUN...\n");
    int ret = 0;
    if(0 == pClient->writeBufLen)
    {
        ret = -1;
        LOGW("FUNC EpollTcpClient::DoWrite(),(0 == writeBufLen),ret:%d\n",ret);
        if(pClient->callback)
        {
            pClient->callback(TcpEvent::TcpEvent_SendError,NULL,0);
        }
        return ret;
    }
    // 发送报文
    ret = write(fd,pClient->writeBuf,pClient->writeBufLen);
    if (ret == -1)
    {
//        perror("write error:");
        LOGW("FUNC EpollTcpClient::DoWrite(),write() error,ret:%d\n",ret);
        close(fd);
        if(pClient->callback)
        {
            pClient->callback(TcpEvent::TcpEvent_SendError,NULL,0);
        }
        return ret;
    }
    if(pClient->callback)
    {
        pClient->callback(TcpEvent::TcpEvent_SendSuccess,NULL,0);
    }
    memset(pClient->writeBuf,0,MAXSIZE);
    ModifyEvent(pClient->epollfd,fd,EPOLLIN);
    pClient->writeBufLen = 0;
    return ret;
}

// 增加epoll事件
int AddEvent(int epollfd,int fd,int state)
{
    LOGD("FUNC EpollTcpClient::AddEvent(),RUN...\n");
    int ret = 0;
    struct epoll_event ev;
    ev.events = state;
    ev.data.fd = fd;
    ret = epoll_ctl(epollfd,EPOLL_CTL_ADD,fd,&ev);
    if(ret != 0)
    {
        LOGW("FUNC EpollTcpClient::AddEvent(),error,epoll_ctl(),ret:%d\n",ret);
        return ret;
    }
    return ret;
}

static void ModifyEvent(int epollfd,int fd,int state)
{
    struct epoll_event ev;
    ev.events = state;
    ev.data.fd = fd;
    epoll_ctl(epollfd,EPOLL_CTL_MOD,fd,&ev);
}


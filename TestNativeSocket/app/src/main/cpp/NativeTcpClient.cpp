//
// Created by 唐鹏翔 on 2017/7/21.
//

#include "NativeTcpClient.h"
#include "utils/LogUtils.h"
#include "socket/EpollTcpClient.h"

jmethodID mid;
jclass objclass;
jobject mobj;
JNIEnv * mEnv;
JavaVM * m_vm;

EpollTcpClient * pTcpClient;

JNIEnv* getJNIEnv() {
    JNIEnv* env = NULL;
    if (m_vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        int status = m_vm->AttachCurrentThread(&env, 0);
        if (status < 0) {
            LOGD("failed to attach current thread");
            return NULL;
        }
    }
    LOGD("GetEnv Success");
    return env;
}


void TcpCallbackFunc(TcpEvent event,char * buf,int buflen)
{
    mEnv = getJNIEnv();
    if(mEnv)
    {
        // 转换数据
        jbyteArray bArray = mEnv->NewByteArray(buflen);
        if(NULL == bArray) {
            mEnv->DeleteLocalRef(bArray);
            return ;
        }
        mEnv->SetByteArrayRegion(bArray,0,buflen,(jbyte*)buf);
        mEnv->CallVoidMethod(mobj, mid, event,bArray);
        if(event == TcpEvent_OnStop) {
            if(NULL != pTcpClient)
            {
                delete pTcpClient;
                pTcpClient == NULL;
            }
        }
        (mEnv)->DeleteLocalRef(bArray);
    }
}


// 设置回调
void addCallback(JNIEnv * env, jobject object) {
    LOGD("FUNC addCallback(),RUN...\n");
    //在子线程中不能这样用
    //objclass = env->FindClass("org/cocos2dx/lib/Cocos2dxActivity");
    //这种写法可以用在子线程中
    objclass = env->GetObjectClass(object);
    mid = env->GetMethodID(objclass, "TcpEventCallback", "(I[B)V");
    //JNI 函数参数中 jobject 或者它的子类，其4we参数都是 local reference。Local reference 只在这个 JNI函数中有效，JNI函数返回后，引用的对象就被释放，它的生命周期就结束了。若要留着日后使用，则需根据这个 local reference 创建 global reference。Global reference 不会被系统自动释放，它仅当被程序明确调用 DeleteGlobalReference 时才被回收。（JNI多线程机制）
    mobj = env->NewGlobalRef(object);
    env->GetJavaVM(&m_vm);
    mEnv = env;
    if(!pTcpClient)
    {
        return ;
    }
    pTcpClient->SetCallback(TcpCallbackFunc);
}

extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_TcpSocketClientInit(JNIEnv * env,jobject obj,jstring jhost,jint jport)
{
    char * host = (char*)env->GetStringUTFChars(jhost, 0);
    LOGD("FUNC Java_com_pix_testnativesocket_NativeTcpClient_TcpSocketClientInit(),host:%s,port:%d\n",host,jport);
    if(NULL != pTcpClient)
    {
        delete pTcpClient;
        pTcpClient == NULL;
    }
    // 创建epoll tcp client
    pTcpClient = new EpollTcpClient;
    // 设置回调
    addCallback(env,obj);
    // 设置host
    pTcpClient->SetHostAndPort(host,jport);
    pTcpClient->Init();
    env->ReleaseStringUTFChars(jhost,host);
}

// Socket 连接服务器
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_TcpSocketClientConnect(JNIEnv * env,jobject obj)
{
    LOGD("FUNC Java_com_pix_testnativesocket_NativeTcpClient_TcpSocketClientConnect(),RUN...\n");
    if(pTcpClient) {
        pTcpClient->Connect();
    }
}

// Socket 发送数据
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_SendData(JNIEnv * env,jobject obj,jbyteArray jdata,jint jdataLen)
{
    LOGD("FUNC Java_com_pix_testnativesocket_NativeTcpClient_TcpSocketClientConnect(),RUN...\n");
    if(!pTcpClient || jdataLen <= 0) {
        return ;
    }
    // 初始化buff
    memset(pTcpClient->writeBuf,0,MAXSIZE);
    env->GetByteArrayRegion(jdata, (jint)0, jdataLen, (jbyte *) pTcpClient->writeBuf);
    pTcpClient->writeBufLen = jdataLen;
    pTcpClient->Send(pTcpClient->writeBuf,pTcpClient->writeBufLen);
}

// Socket 停止连接
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_Stop(JNIEnv * env,jobject obj)
{
    LOGD("FUNC Java_com_pix_testnativesocket_NativeTcpClient_Stop(),pTcpClient:%p\n",pTcpClient);
    if(pTcpClient)
    {
        pTcpClient->Stop();
    }
}

// Socket 销毁客户端
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_Destroy(JNIEnv * env,jobject obj)
{
    if(pTcpClient) {
        delete pTcpClient;
        pTcpClient = NULL;
    }
}

// Socket 判断是否连接
extern "C"
JNIEXPORT jboolean JNICALL Java_com_pix_testnativesocket_NativeTcpClient_IsConnect(JNIEnv * env,jobject obj)
{
    if(pTcpClient) {
       return pTcpClient->IsConnect();
    }
    return false;
}
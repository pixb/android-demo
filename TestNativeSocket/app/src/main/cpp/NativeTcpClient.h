//
//  Tcp Client JNI 层定义
// Created by 唐鹏翔 on 2017/7/21.
//

#include <jni.h>
#include <string>

#ifndef TESTNATIVESOCKET_NATIVETCPCLIENT_H
#define TESTNATIVESOCKET_NATIVETCPCLIENT_H

// Socket 初始化
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_TcpSocketClientInit(JNIEnv * env,jobject obj,jstring jhost,jint jport);

// Socket 连接服务器
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_TcpSocketClientConnect(JNIEnv * env,jobject obj);

// Socket 发送数据
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_SendData(JNIEnv * env,jobject obj,jbyteArray jdata,jint jdataLen);

// Socket 停止连接
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_Stop(JNIEnv * env,jobject obj);


// Socket 销毁
extern "C"
JNIEXPORT void JNICALL Java_com_pix_testnativesocket_NativeTcpClient_Destroy(JNIEnv * env,jobject obj);

// Socket 判断是否连接
extern "C"
JNIEXPORT jboolean JNICALL Java_com_pix_testnativesocket_NativeTcpClient_IsConnect(JNIEnv * env,jobject obj);

#endif //TESTNATIVESOCKET_NATIVETCPCLIENT_H

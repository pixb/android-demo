#ifndef LOGUTIL_HPP
#define LOGUTIL_HPP

#define NDK_LOG true
#define C_LOG false

#if NDK_LOG
#include <android/log.h>
#include <jni.h>
#endif


#define TAG "CSocketLib"

#if NDK_LOG
#define log_print_verbose(...)  __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define log_print_debug(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define log_print_info(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define log_print_warn(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define log_print_error(...) __android_log_print(ANROID_LOG_ERROR, TAG, __VA_ARGS__)
#elif C_LOG
#define log_print_verbose(...)  printf(__VA_ARGS__)
#define log_print_debug(...) printf(__VA_ARGS__)
#define log_print_info(...) printf(__VA_ARGS__)
#define log_print_warn(...)  printf(__VA_ARGS__)
#define log_print_error(...) printf(__VA_ARGS__)
#else
#define log_print_verbose(...)
#define log_print_debug(...)
#define log_print_info(...)
#define log_print_warn(...)
#define log_print_error(...)
#endif


#define LOGV(...) log_print_verbose(__VA_ARGS__)
#define LOGD(...) log_print_debug(__VA_ARGS__)
#define LOGI(...) log_print_info(__VA_ARGS__)
#define LOGW(...) log_print_warn(__VA_ARGS__)
#define LOGE(...) log_print_error(__VA_ARGS__)


#endif // LOGUTIL_HPP
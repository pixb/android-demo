> c/c++ TcpClient

# 说明
本测试工程，是测试在Android中使用c/c++来完成一个Tcp Clinet的Demo





# 问题

## 1、pthread 多个参数问题
使用`pthread_create`函数传递多个参数报错
处理参考：https://stackoverflow.com/questions/44463834/android-jni-pthread-create-too-many-arguments-expected-1
在引入`#include <pthread.h>`的地方定义宏
```
#ifndef _Nonnull
#define _Nonnull
#endif
#include <pthread.h>
```

## 2、局部表引用异常
`jni`层局部变量个数有限制

不使用时要手动释放

参考：http://blog.csdn.net/xyang81/article/details/44873769

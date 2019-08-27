# HandlerThreadTest
本Demo来自Hongyang的博客


# 总结HandlerThread的简单使用

 - 1、创建带名称的HandlerThread
```
HandlerThread handlerThread = new HandlerThread(“threadName”);
```
 - 2、启动HandlerThread
```
handlerThread.start();
```
 - 3、创建一个Handler并且将HandlerThread的Looper传入
```
new Handler(handlerThread.getLooper()){}
```
	这样就创建了一个子线程的Thread和一个子线程的Handler


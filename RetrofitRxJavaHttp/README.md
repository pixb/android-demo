# Retrofit2 + OkHttp3封装的Http请求库

这个是Retrofit2和Okhttp3封装的http请求库

参考自：https://github.com/yunTerry/Retrofit-CallBack

做了一些改动:

- 使用之前需要先设置公共Header,没有设置一个空map即可

  ```kotlin
  OKHttpManager.setHeaders(map)
  ```

- 使用`kotlin`封装到一个`HttpLibrary`的单独module中，方便项目引入

- 增加了添加公共头和公共参数的方法，阅读`OkHttpManager`中的`AddHeaderAndParamsInterceptor`类

  参考我的博客：http://tangsanzang.tk/2018/10/22/okhttp3添加公共头与请求参数/
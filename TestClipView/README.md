# Android视图裁剪

视图裁剪能让你轻松地改变视图的形状。你可以为了一致性而使用其他的设计元素裁剪视图或者为了响应用户的输入改变视图的形状。

ViewOutlineProvider(轮廓提供者的使用步骤)

1. 自定义轮廓提供者，并重写getOutline方法来提取轮廓；

2. 通过view.setClipToOutline(true)方法来开启组件的裁剪功能；

3. 通过view.setOutlineProvider(new MyViewOutlineProvider() 方法设置自定义的轮廓提供者来完成裁剪。

参考：

https://blog.csdn.net/yihonglvyu1/article/details/52965111
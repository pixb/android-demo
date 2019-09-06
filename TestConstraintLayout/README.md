# ConstraintLayout笔记

参考

- https://www.jianshu.com/p/17ec9bd6ca8a
- https://www.cnblogs.com/angrycode/p/9739513.html
- https://www.jianshu.com/p/9c8a8cd7aa33

## 1、相对定位

相对定位是部件对于另一个位置的约束，这么说可能有点抽象，举个例子：



![img](https:////upload-images.jianshu.io/upload_images/2787721-ff2d4c4b39b9e98b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/171/format/webp)



如图所示，TextView2在TextView1的右边，TextView3在TextView1的下面，这个时候在布局文件里面应该这样写：

```xml
    <TextView
        android:id="@+id/TextView1"
        ...
        android:text="TextView1" />

    <TextView
        android:id="@+id/TextView2"
        ...
        app:layout_constraintLeft_toRightOf="@+id/TextView1" />

    <TextView
        android:id="@+id/TextView3"
        ...
        app:layout_constraintTop_toBottomOf="@+id/TextView1" />
```


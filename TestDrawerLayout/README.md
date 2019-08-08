# 侧滑抽屉布局DrawLayout
## 1、使用方法
- 布局

布局中需要将`Activity`的跟布局设置为`android.support.v4.widget.DrawerLayout`
子布局第一层为内容布局，第二层为左侧滑动布局，第三层为右侧滑动布局

范例：
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout
        android:id="@+id/dl_drawerlayout"
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

    <FrameLayout
            android:background="#00F"
            android:id="@+id/fl_content"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
    </FrameLayout>
    <LinearLayout
            android:id="@+id/ll_drawerview"
            android:orientation="vertical"
            android:background="#F00"
            android:layout_width="300dp"
            android:layout_height="match_parent"
            android:layout_gravity="start">
    </LinearLayout>

</android.support.v4.widget.DrawerLayout>
```
这里放了两层，分别为内容区`FrameLayout`，左侧侧滑抽屉布局`LinearLayout`
这样就可以实现侧滑菜单功能了。

## Toolbar配合DrawerLayout使用
公共Toolbar
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.Toolbar xmlns:android="http://schemas.android.com/apk/res/android"
                                   xmlns:app="http://schemas.android.com/apk/res-auto"
                                   android:id="@+id/toolbar"
                                   android:layout_width="match_parent"
                                   android:layout_height="wrap_content"
                                   android:background="?attr/colorPrimary"
                                   android:minHeight="?attr/actionBarSize"
                                   android:popupTheme="@style/ThemeOverlay.AppCompat.Light"
                                   app:theme="@style/ThemeOverlay.AppCompat.ActionBar">
</android.support.v7.widget.Toolbar>
```
用`<include>`标签导入

# 参考
https://www.cnblogs.com/Chenshuai7/p/5443358.html
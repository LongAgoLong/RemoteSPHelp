# SharePreference&ContentProvider跨进程数据存储与共享

------

[![](https://jitpack.io/v/LongAgoLong/RemoteSPHelp.svg)](https://jitpack.io/#LongAgoLong/RemoteSPHelp)

## Server服务端实现

### ①在project的build.gradle文件中添加url

```scss
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### ②在module的build.gradle文件中添加依赖

```scss
implementation "com.github.LongAgoLong.RemoteSPHelp:provider:$Tag"
```

### ③AndroidManifest.xml文件中添加以下代码

```xml
<meta-data
    android:name="authority"
    android:value="com.android.sp.provider" />

<provider
    android:name="com.leo.sp.provider.SPContentProvider"
    android:authorities="com.android.sp.provider"
    android:exported="true"
    android:grantUriPermissions="true" />
```

- **authority**中**value**值等同于**provider**标签中的**android:authorities**属性；


## Client客户端实现

### ①在project的build.gradle文件中添加url

```scss
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### ②在module的build.gradle文件中添加依赖

```scss
implementation "com.github.LongAgoLong.RemoteSPHelp:resolver:$Tag"
```

### ③AndroidManifest.xml文件中添加以下代码

```xml
<queries>
    <package android:name="com.leo.remotesphelp" />
</queries>

<meta-data
    android:name="authority"
    android:value="com.android.sp.provider" />
```

- **value**必须与server客户端中定义的**authorities**相同

### ④在Application onCreate()方法中调用以下方法初始化

```java
@Override
public void onCreate() {
    super.onCreate();
    SpContants.initAuthority(this);
    SpResolver.getInstance().init(this);
}
```


# AndroidDevTemplate
The Android quickly develop template.

一个Android 快速开发模版。

## How to Start?
```
compile 'com.zgh0711.template:template:2.0.0'
```

## 2.0 版本更新内容
2.0版本主要是增加了对  MVP 的支持，使用的是 [TmeMVP](https://github.com/kymjs/TheMVP)
的模式，把它的源码拿过来改了下放到这个项目中，并增加了相应的 Demo。


然后就是对三方库做了一些更新和增减。

## Tips:
1. 因为依赖了 AndroidUtilCode 这个库，所以项目的 minSdkVersion
必须为 19 及以上版本，不然 gradle 会报错。

2. 因为依赖了 BaseRecyclerViewAdapterHelper 这个库，
所以需要在项目根目录的 build.gradle 文件中的 allprojects
节点下添加以下内容,不然 gradle 会报错。
    ```
    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }
    ```

3. 需要在 Application 中初始化的东西
    ```
    // Logger初始化配置
    FormatStrategy strategy = PrettyFormatStrategy
             .newBuilder()
             .showThreadInfo(false)  // 是否显示线程信息. Default true
             .methodCount(1)         // 显示多少个方法. Default 2
    //       .methodOffset(5)        // 隐藏内部调用的方法数. Default 5
    //       .logStrategy(customLog) // 修改日志打印的位置. Default LogCat
             .tag("APP")   // 全局 TAG. Default PRETTY_LOGGER
             .build();
    Logger.addLogAdapter(new AndroidLogAdapter(strategy));
    // Utils库初始化以及初始化 SPUtils 和 SP 文件
    Utils.init(mContext);
    // 如果项目中需要用到 SP 文件，也需要在这里初始化
    mySP = new SPUtils(AppUtils.getAppName(mContext));
    ```


## 功能
#### BaseActivity 和 BaseFragment
封装好的基类，继承这二个类，实现其中部分的抽象方法就可以实现自己的
界面，只需要专注于编写逻辑代码就可以了。

#### BaseListActivity 和 BaseListFragment
这是二个扩展了的基类，可以快速实现一个列表界面，使用方法同上。

#### 大量实用的 Utils 和自定义View
可以自己去发掘，就不一一说明了。

## 本项目依赖的一些库
glide，gson，eventBus，

[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
[Fragmentation](https://github.com/YoKeyword/Fragmentation)
[FlycoTabLayout](https://github.com/H07000223/FlycoTabLayout)
[StatusBarUtil](https://github.com/laobie/StatusBarUtil)
[logger](https://github.com/orhanobut/logger)
[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)

然后，有一个简单的 Demo 在里面可以参考，真的是很简单，，，


最后，为了快速开发，引用的库比较多，如果你不想在项目中依赖某个库，
可以去 [我的这个项目](https://github.com/zgh0711/AppDevTemplate)
这个用起来也简单，直接 clone 项目文件到本地，修改项目包名就可以开始
愉快的玩耍了。

最最后，这是本人第一个开源项目，限于水平，有写的不好的地方请多包涵，
并欢迎提 Issues 和 star ！



package com.zgh.appdevtemplate.util;

import com.orhanobut.logger.Logger;

/**
 * Log统一管理类
 * 
 */
public class LogUtil {
	// 是否需要打印bug，可以在application的onCreate函数里面初始化
	public static        boolean isDebug = false;
	private static final String  TAG     = "Log";

	private LogUtil() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Logger.i(msg);
	}

	public static void d(String msg) {
		if (isDebug)
            Logger.d(msg);
	}

    public static void d(Object content) {
        if (isDebug)
            Logger.d(content);
    }

	public static void e(Exception exception,String msg) {
		if (isDebug)
            Logger.e(exception, msg);
	}

	public static void v(String msg) {
		if (isDebug)
            Logger.v(msg);
	}

    public static void json(String json){
        if (isDebug)
            Logger.json(json);
    }

    public static void xml(String xml){
        if (isDebug)
            Logger.xml(xml);
    }

    // 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
            Logger.t(tag).i(msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
            Logger.t(tag).d(msg);
	}

    public static void d(String tag, Object content) {
        if (isDebug)
            Logger.t(tag).d(content);
    }

	public static void e(String tag, String msg) {
		if (isDebug)
            Logger.t(tag).e(msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
            Logger.t(tag).v(msg);
	}
}
package com.zgh.appdevtemplate.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 开发中需要用到的工具类
 */

public class DevelopUtils {
    /**
     * 判断按钮是否点击过快
     */
    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void showKeybord(@NonNull EditText mEditText, Context mContext) {
        InputMethodManager
                imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void hideKeybord(@NonNull EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 在 TextView 左边显示状态图标,需要传入图片和颜色id
     */
    public static void setLeftDrawable(@NonNull TextView textView, Context context,
            @DrawableRes int resId, @ColorRes int colorId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        textView.setTextColor(context.getResources().getColor(colorId));
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 在 TextView 右边显示状态图标,需要传入图片和颜色id
     */
    public static void setRightDrawable(@NonNull TextView textView, Context context,
            @DrawableRes int resId, @ColorRes int colorId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        textView.setTextColor(context.getResources().getColor(colorId));
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 在 TextView 上显示文字
     */
    public static void showText(@NonNull TextView textView, String string) {
        if (textView != null) {
            if (!TextUtils.isEmpty(string)) {
                textView.setText(string);
            } else {
                textView.setText("");
            }
        }
    }

    /**
     * 在 EditText 上显示文字
     */
    public static void showText(@NonNull EditText editText, String string) {
        if (editText != null) {
            if (!TextUtils.isEmpty(string)) {
                editText.setText(string);
            } else {
                editText.setText("");
            }
        }
    }

    /**
     * 检查 EditText 是否为空，如果为空就给出提示信息
     */
    public static String checkEditText(@NonNull EditText editText, @NonNull String errInfo, Context context) {
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            return editText.getText().toString();
        } else {
            if (!TextUtils.isEmpty(errInfo)) {
                Toast.makeText(context, errInfo, Toast.LENGTH_SHORT).show();
                return "";
            } else {
                return "";
            }

        }
    }

    /**
     * 检查 TextView 是否为空，如果为空就给出提示信息
     */
    public static String checkTextView(@NonNull TextView textView, @NonNull String errInfo, Context context) {
        if (!TextUtils.isEmpty(textView.getText().toString())) {
            return textView.getText().toString();
        } else {
            if (!TextUtils.isEmpty(errInfo)) {
                Toast.makeText(context,errInfo,Toast.LENGTH_SHORT).show();
                return "";
            } else {
                return "";
            }
        }
    }

    /**
     * 高亮字符串中的某部分文字
     */
    public static CharSequence highLightString(String originStr, String highLightStr,
            @ColorInt int highLightColor) {

        SpannableString ss = new SpannableString(originStr);
        Pattern p = Pattern.compile(highLightStr);
        Matcher m = p.matcher(ss);
        while (m.find()) {
            ss.setSpan(new ForegroundColorSpan(highLightColor), m.start(), m.end(),
                       Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ss;
    }
}

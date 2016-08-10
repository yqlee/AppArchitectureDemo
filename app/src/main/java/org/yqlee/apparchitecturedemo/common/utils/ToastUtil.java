package org.yqlee.apparchitecturedemo.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 上午 11:40
 * 描述 ：
 */
public class ToastUtil {

    public static void showLangCustomToast(Context context, String text) {
        showCustomToast(context, text, true);
    }

    public static void showLangCustomToast(Context context, int strId) {
        showCustomToast(context, strId, true);
    }

    public static void showCustomToast(Context context, String text) {
        showCustomToast(context, text, false);
    }

    public static void showCustomToast(Context context, int strId) {
        showCustomToast(context, strId, false);
    }


    public static void showCustomToast(Context context, String text, boolean isLang) {
        if (isLang) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showCustomToast(Context context, int resId, boolean isLang) {
        if (isLang) {
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
        }
    }

}

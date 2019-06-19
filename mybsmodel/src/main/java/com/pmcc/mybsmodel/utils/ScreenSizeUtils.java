package com.pmcc.mybsmodel.utils;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.pmcc.mybsmodel.base.BaseApp;

/**
 * Created by ${zhangshuai} on 2018/8/21.
 * 2751157603@qq.com
 * 今日头条屏幕解决方案
 */
public class ScreenSizeUtils {
  /**
   * 设计稿标准
   */
  private static final float width = 1080f;
  private static final float high = 1920f;


  private static float textDensity = 0;
  private static float textScaledDensity = 0;


  /**
   * 今日头条的屏幕适配方案
   * 根据当前设备物理尺寸和分辨率去动态计算density、densityDpi、scaledDensity
   * 同时也解决了用户修改系统字体的情况
   */
  public static void setCustomDensity() {
    setCustomDensity(false);
  }


  /**
   * @param isLandscape 是否是横屏
   */
  public static void setCustomDensity(boolean isLandscape) {

    final DisplayMetrics displayMetrics = BaseApp.getAppContext().getResources().getDisplayMetrics();
    if (textDensity == 0) {
      textDensity = displayMetrics.density;
      textScaledDensity = displayMetrics.scaledDensity;
      BaseApp.getAppContext().registerComponentCallbacks(new ComponentCallbacks() {
        @Override
        public void onConfigurationChanged(Configuration configuration) {
          if (configuration != null && configuration.fontScale > 0) {
            textScaledDensity = BaseApp.getAppContext().getResources().getDisplayMetrics().scaledDensity;
          }
        }

        @Override
        public void onLowMemory() {

        }
      });
    }

    final float targetDensity;
    if (isLandscape) {//横屏
      targetDensity = displayMetrics.widthPixels / (high / 3f); //当前UI标准1080*1920
    } else {
      targetDensity = displayMetrics.widthPixels / (width / 3f); //当前UI标准1080*1920
    }


    final float targetScaledDensity = targetDensity * (textScaledDensity / textDensity);
    final int targetDpi = (int) (160 * targetDensity);

    displayMetrics.density = targetDensity;
    displayMetrics.scaledDensity = targetScaledDensity;
    displayMetrics.densityDpi = targetDpi;

    final DisplayMetrics activityDisplayMetrics = BaseApp.getAppContext().getResources().getDisplayMetrics();
    activityDisplayMetrics.density = targetDensity;
    activityDisplayMetrics.scaledDensity = targetScaledDensity;
    activityDisplayMetrics.densityDpi = targetDpi;
  }


}
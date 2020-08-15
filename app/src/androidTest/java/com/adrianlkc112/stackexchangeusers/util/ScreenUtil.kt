package com.adrianlkc112.stackexchangeusers.util

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration


class ScreenUtil {
    fun rotateScreen(activity: Activity) {
        val orientation: Int = activity.resources.configuration.orientation
        activity.requestedOrientation =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
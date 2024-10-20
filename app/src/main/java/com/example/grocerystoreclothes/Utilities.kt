package com.example.grocerystoreclothes

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import kotlin.math.sqrt

class Utilities {

    companion object {

        fun isTablet(activity: Activity): Boolean {
            val metrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            val yInches = metrics.heightPixels / metrics.ydpi
            val xInches = metrics.widthPixels / metrics.xdpi
            val diagonalInches = sqrt((xInches * xInches + yInches * yInches).toDouble())

            return if (tablet(activity)) {
                diagonalInches >= 7
            } else {
                false
            }
        }


        private fun tablet(context: Context): Boolean {
            return (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }
    }


}
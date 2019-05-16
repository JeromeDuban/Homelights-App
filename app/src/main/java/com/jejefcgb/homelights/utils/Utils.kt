package com.jejefcgb.homelights.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View


object Utils {

    fun getMatColor(context: Context, typeColor: String): Int {
        var returnColor = Color.BLACK

        val arrayId = context.resources.getIdentifier("mdcolor_$typeColor", "array", context.packageName)

        if (arrayId != 0) {
            val colors = context.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.BLACK)
            colors.recycle()
        }
        return returnColor
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * Sets the background for a view while preserving its current padding. If the background drawable
     * has its own padding, that padding will be added to the current padding.
     *
     * @param view View to receive the new background.
     * @param backgroundDrawable Drawable to set as new background.
     */
    fun setBackgroundAndKeepPadding(view: View, backgroundDrawable: Drawable) {
        val drawablePadding = Rect()
        backgroundDrawable.getPadding(drawablePadding)
        val top = view.paddingTop + drawablePadding.top
        val left = view.paddingLeft + drawablePadding.left
        val right = view.paddingRight + drawablePadding.right
        val bottom = view.paddingBottom + drawablePadding.bottom

        view.background = backgroundDrawable
        view.setPadding(left, top, right, bottom)
    }


    /**
     * Sets the background tint for a view while preserving its current padding. If the background drawable
     * has its own padding, that padding will be added to the current padding.
     *
     * @param view View to receive the new background.
     * @param backgroundDrawable Drawable to set as new background.
     */
    fun setBackgroundTintAndKeepPadding(view: View, backgroundDrawable: Drawable, color: Int) {
        val drawablePadding = Rect()
        backgroundDrawable.getPadding(drawablePadding)
        val top = view.paddingTop + drawablePadding.top
        val left = view.paddingLeft + drawablePadding.left
        val right = view.paddingRight + drawablePadding.right
        val bottom = view.paddingBottom + drawablePadding.bottom

        backgroundDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        view.background = backgroundDrawable

        view.setPadding(left, top, right, bottom)


    }

}

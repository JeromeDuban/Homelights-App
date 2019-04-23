package com.jejefcgb.homelights

import android.app.SharedElementCallback
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.jejefcgb.homelights.utils.IntentUtil
import java.util.*



class DetailSharedElementEnterCallback(private val intent: Intent, private val title: TextView, private val icon : ImageView, private val background : View) : SharedElementCallback() {
    private var targetTextSize: Float = 0.toFloat()
    private var targetTextColors: ColorStateList? = null
    private var targetPadding: Rect? = null


    override fun onSharedElementStart(sharedElementNames: List<String>,
                                      sharedElements: List<View>,
                                      sharedElementSnapshots: List<View>) {

        targetTextSize = title.textSize
        targetTextColors = title.textColors
        targetPadding = Rect(title.paddingLeft,
                title.paddingTop,
                title.paddingRight,
                title.paddingBottom)
        if (IntentUtil.hasAll(intent,
                        IntentUtil.TEXT_COLOR, IntentUtil.FONT_SIZE, IntentUtil.PADDING)) {
            title.setTextColor(intent.getIntExtra(IntentUtil.TEXT_COLOR, Color.BLACK))
            val textSize = intent.getFloatExtra(IntentUtil.FONT_SIZE, targetTextSize)
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            val padding = intent.getParcelableExtra<Rect>(IntentUtil.PADDING)
            title.setPadding(padding.left, padding.top, padding.right, padding.bottom)
        }
    }

    override fun onSharedElementEnd(sharedElementNames: List<String>,
                                    sharedElements: List<View>,
                                    sharedElementSnapshots: List<View>) {
        val author = title
        author.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize)
        if (targetTextColors != null) {
            author.setTextColor(targetTextColors)
        }
        if (targetPadding != null) {
            author.setPadding(targetPadding!!.left, targetPadding!!.top,
                    targetPadding!!.right, targetPadding!!.bottom)
        }

    }

    override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
        removeObsoleteElements(names, sharedElements, mapObsoleteElements(names))
        mapSharedElement(names, sharedElements, title)
        mapSharedElement(names, sharedElements, icon)
        mapSharedElement(names, sharedElements, background)

    }
//
//    fun setBinding(@NonNull binding: DetailViewBinding) {
//        currentDetailBinding = binding
//        currentPhotoBinding = null
//    }
//
//    fun setBinding(@NonNull binding: PhotoItemBinding) {
//        currentPhotoBinding = binding
//        currentDetailBinding = null
//    }

    /**
     * Maps all views that don't start with "android" namespace.
     *
     * @param names All shared element names.
     * @return The obsolete shared element names.
     */
    @NonNull
    private fun mapObsoleteElements(names: List<String>): List<String> {
        val elementsToRemove = ArrayList<String>(names.size)
        for (name in names) {
            if (name.startsWith("android")) continue
            elementsToRemove.add(name)
        }
        return elementsToRemove
    }

    /**
     * Removes obsolete elements from names and shared elements.
     *
     * @param names Shared element names.
     * @param sharedElements Shared elements.
     * @param elementsToRemove The elements that should be removed.
     */
    private fun removeObsoleteElements(names: MutableList<String>,
                                       sharedElements: MutableMap<String, View>,
                                       elementsToRemove: List<String>) {
        if (elementsToRemove.size > 0) {
            names.removeAll(elementsToRemove)
            for (elementToRemove in elementsToRemove) {
                sharedElements.remove(elementToRemove)
            }
        }
    }

    /**
     * Puts a shared element to transitions and names.
     *
     * @param names The names for this transition.
     * @param sharedElements The elements for this transition.
     * @param view The view to add.
     */
    private fun mapSharedElement(names: MutableList<String>, sharedElements: MutableMap<String, View>, view: View) {
        val transitionName = view.transitionName
        names.add(transitionName)
        sharedElements[transitionName] = view
    }

    private fun forceSharedElementLayout(view: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(view.width,
                View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(view.height,
                View.MeasureSpec.EXACTLY)
        view.measure(widthSpec, heightSpec)
        view.layout(view.left, view.top, view.right, view.bottom)
    }

}
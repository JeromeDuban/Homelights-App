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


class DetailSharedElementEnterCallback(private val intent: Intent, private val name: TextView, private val icon: ImageView, private val background: View) : SharedElementCallback() {
    private var targetTextSize: Float = 0.toFloat()
    private var targetTextColors: ColorStateList? = null
    private var targetPadding: Rect? = null


    override fun onSharedElementStart(sharedElementNames: List<String>,
                                      sharedElements: List<View>,
                                      sharedElementSnapshots: List<View>) {
        // Appelé pour définir le début de la transition.
        // Appelle captureStartValues() de la transition

        // On sauvegarde les valeurs actuelles sur la vue 2
        targetTextSize = name.textSize
        targetTextColors = name.textColors
        targetPadding = Rect(name.paddingLeft,
                name.paddingTop,
                name.paddingRight,
                name.paddingBottom)

        // On les remplace par les valeurs envoyées par la vue 1
        if (IntentUtil.hasAll(intent,
                        IntentUtil.TEXT_COLOR,
                        IntentUtil.FONT_SIZE,
                        IntentUtil.PADDING)) {

            name.setTextColor(intent.getIntExtra(IntentUtil.TEXT_COLOR, Color.BLACK))

            val textSize = intent.getFloatExtra(IntentUtil.FONT_SIZE, targetTextSize)
            name.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            val padding = intent.getParcelableExtra<Rect>(IntentUtil.PADDING)
            name.setPadding(padding.left, padding.top, padding.right, padding.bottom)
        }
    }

    override fun onSharedElementEnd(sharedElementNames: List<String>,
                                    sharedElements: List<View>,
                                    sharedElementSnapshots: List<View>) {

        // Appelé pour définir la fin de la transition.
        // Appelle captureEndValues() de la transition


        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize)

        if (targetTextColors != null) {
            name.setTextColor(targetTextColors)
        }
        if (targetPadding != null) {
            name.setPadding(targetPadding!!.left, targetPadding!!.top,
                    targetPadding!!.right, targetPadding!!.bottom)
        }

    }

    override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {

        // Retire les vues non-system de names
        removeObsoleteElements(names, sharedElements, mapObsoleteElements(names))

        // rajoute les vues non-system dans name et shared elements
        mapSharedElement(names, sharedElements, name)
        mapSharedElement(names, sharedElements, icon)
        mapSharedElement(names, sharedElements, background)

    }

    /**
     * Maps all views that don't start with "android" namespace.
     *
     * @param names All shared element names.
     * @return The obsolete shared element names.
     */
    @NonNull
    private fun mapObsoleteElements(names: List<String>): List<String> {

        // Va retirer tous les sharedElements autre que les vues systeme
        // Les vues retirées sont gérées ailleurs

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

        if (elementsToRemove.isNotEmpty()) {
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


}
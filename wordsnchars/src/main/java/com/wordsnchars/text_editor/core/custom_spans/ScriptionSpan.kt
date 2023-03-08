package com.wordsnchars.text_editor.core.custom_spans

import android.text.TextPaint
import android.text.style.RelativeSizeSpan

const val noscriptionFlag = 0
const val subscriptionFlag = 1
const val superscriptionFlag = 2

class ScriptionSpan(val scriptionType: Int) : RelativeSizeSpan(0.7F) {
    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.baselineShift += when (scriptionType) {
            subscriptionFlag ->
                -(textPaint.ascent() / 4).toInt()
            superscriptionFlag ->
                (textPaint.ascent() / 3).toInt()
            else -> 0
        }
        if (scriptionType != 0)
            super.updateDrawState(textPaint)
    }

    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.baselineShift += when (scriptionType) {
            subscriptionFlag ->
                -(textPaint.ascent() / 4).toInt()
            superscriptionFlag ->
                (textPaint.ascent() / 3).toInt()
            else -> 0
        }
        if (scriptionType != 0)
            super.updateMeasureState(textPaint)
    }

}
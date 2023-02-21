package com.wordsnchars.text_editor.utils

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EditableSuspendExtensionsKtTest {

    @Test
    fun createGapWithProperBorders() {
        var result = true
        var s = SpannableStringBuilder("aegeergwgrg")
        s.setSpan(StyleSpan(2), Border(0, 10))
        s.createGap(StyleSpan::class.java, Border(2, 5))
        s.getSpans(0, s.length, StyleSpan::class.java).forEach { it ->
            println("$it ${s.getBorder(it)}")
            result = s.getBorder(it) == Border(0, 2) || s.getBorder(it) == Border(5, 10) && result
        }
        assertThat(result).isTrue()
    }

    @Test
    fun createGapWithImproperBorders() {
        var result = false
        try {
            var s = SpannableStringBuilder("aegeergwgrg")
            s.setSpan(StyleSpan(2), Border(0, 11))
            s.createGap(StyleSpan::class.java, Border(5, 2))
        } catch (e: ImproperBordersException) {
            result = true
        }
        assertThat(result).isTrue()
    }

    @Test
    fun createGapWithPartialInsideInclinedBorder() {
        var result = true
        var s = SpannableStringBuilder("aegeergwgrg")
        s.setSpan(StyleSpan(2), Border(5, 7))
        s.createGap(StyleSpan::class.java, Border(0, 11))

        s.getSpans(0, 11, StyleSpan::class.java).forEach {
            println(s.getBorder(it))
            result = false
        }
        assertThat(result).isTrue()
    }

    @Test
    fun createGapWithGapThatTouchesTwoBorders() {
        var result = true
        var s = SpannableStringBuilder("aegeergwgrg")
        s.setSpan(StyleSpan(2), Border(0, 4))
        s.setSpan(StyleSpan(3), Border(6, 11))
        s.createGap(StyleSpan::class.java, Border(2, 7))
        s.getSpans(0, s.length, StyleSpan::class.java).forEach { it ->
            println("${s.getBorder(it)}")
            result = s.getBorder(it) == Border(0, 2) || s.getBorder(it) == Border(7, 11)
        }
        assertThat(result).isTrue()
    }

    @Test
    fun setSpanProperly() {
        var result = SpannableStringBuilder("ergergrgt").setSpan(StyleSpan(3), Border(0, 4))
        assertThat(result).isTrue()
    }

    @Test
    fun setSpanImproperly() {
        var result = false
        try {
            SpannableStringBuilder("ergergrgt").setSpan(StyleSpan(3), Border(0, -7))
        } catch (e: ImproperBordersException) {
            result = true
        }
        assertThat(result).isTrue()
    }
}
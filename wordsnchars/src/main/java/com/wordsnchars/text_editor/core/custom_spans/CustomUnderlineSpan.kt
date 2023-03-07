package com.wordsnchars.text_editor.core.custom_spans

import android.os.Parcel
import android.text.ParcelableSpan
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.CharacterStyle
import android.text.style.UnderlineSpan
import android.text.style.UpdateAppearance

const val UNDERLINE_SPAN = 6

internal class CustomUnderlineSpan(val underlined: Boolean = true) : CharacterStyle(),
    UpdateAppearance, ParcelableSpan {

    override fun getSpanTypeId(): Int {
        return spanTypeIdInternal
    }

    /** @hide
     */
    val spanTypeIdInternal: Int
        get() = UNDERLINE_SPAN

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        writeToParcelInternal(dest, flags)
    }

    /** @hide
     */
    fun writeToParcelInternal(dest: Parcel, flags: Int) {}
    override fun updateDrawState(ds: TextPaint) {
        if (underlined)
            ds.isUnderlineText = true
    }
}
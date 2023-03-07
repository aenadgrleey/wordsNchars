package com.wordsnchars.ui_xml

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.wordsnchars.R
import com.wordsnchars.databinding.TextEditorHomeBinding

class TextEditorToolbar(context: Context, attributes: AttributeSet) :
    RelativeLayout(context, attributes) {
    init {
        LayoutInflater.from(context).inflate(R.layout.text_editor_holder, this, true)
    }
}
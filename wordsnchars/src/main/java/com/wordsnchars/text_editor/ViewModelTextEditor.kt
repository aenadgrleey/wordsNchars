package com.wordsnchars.text_editor

import android.graphics.Typeface
import android.text.style.BackgroundColorSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelTextEditor : ViewModel() {
    var previouslySetSpans: MutableMap<Any, MutableSet<Pair<Any, Border>>> = mutableMapOf(
        BackgroundColorSpan::class.java to mutableSetOf(Pair(BackgroundColorSpan(0), Border(0, 0)))
    )
    var cursorPosition = 0
    var currentSpansStarts = mutableMapOf<Any, Int>(
        BackgroundColorSpan::class.java to 0
    )

    //"underline" modifier routine
    private var _underlined = MutableStateFlow(false)
    val underlined get() = _underlined.asStateFlow()
    fun underlinedToggle(){
        _underlined.value = !_underlined.value
    }

    //"strike through" modifier routine
    private var _strikeThrough = false
    val strikeThrough get() = _strikeThrough
    fun strikeThroughToggle(){
        _strikeThrough = !_strikeThrough
    }

    //font size modifier routine
    private var _fontSizeMultiplier = MutableStateFlow(1F)
    val fontSizeMultiplier get() = _fontSizeMultiplier.asStateFlow()
    fun setModifier(modifier: Float): Boolean {
        return if(modifier in 0.5..2.5){
            _fontSizeMultiplier.value = modifier
            true
        } else false
    }

    //highlight modifier routines
    private var _highlighted = MutableStateFlow(false)
    val highlighted get() = _highlighted.asStateFlow()
    private var _highlightColor = MutableStateFlow(16777215)
    val highlightColor get() = _highlightColor.asStateFlow()
    fun highlightToggle() {
        _highlighted.value = !_highlighted.value
    }
    fun highlightColorChange(color: Int): Boolean {
        _highlightColor.value = color
        return true
    }

    //for the future ^_^

//    //font color routines
//    private var _coloredFont = MutableStateFlow(false)
//    val coloredFont get() = _coloredFont.asStateFlow()
//    private var _fontColor = MutableStateFlow(Color.BLACK)
//    val fontColor get() = _coloredFont.asStateFlow()
//    fun fontColoredToggle(){
//        _coloredFont.value = !_coloredFont.value
//    }
//    fun fontColorChange(color: Int): Boolean {
//        return if (color in -16777215..-1) {
//            _fontColor.value = color
//            true
//        } else false
//    }

    //to be deprecated
    private var _bold = MutableStateFlow(false)
    val bold get() = _bold.asStateFlow()
    fun boldToggle() {
        _bold.value = !_bold.value
    }

    //style modifier routine
    private var _styled = MutableStateFlow(false)
    val styled = _styled.asStateFlow()
    private var _style = MutableStateFlow(Typeface.NORMAL)
    val style get() = _style.asStateFlow()
    fun styleChange(style: Int): Boolean {
        return if (style in 0..3) {
            _style.value = style
            true
        } else false
    }

    private var _scription = MutableStateFlow<Any?>(null)
    val scription get() = _scription.asStateFlow()
    fun scriptionChange(scriptionSpan: Any?): Boolean {
        return if (scriptionSpan == null || scriptionSpan::class.java == SuperscriptSpan::class.java || scriptionSpan::class.java == SubscriptSpan::class.java){
            _scription.value = scriptionSpan
            true
        } else false
    }

}
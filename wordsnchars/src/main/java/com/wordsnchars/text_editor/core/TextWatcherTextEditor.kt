package com.wordsnchars.text_editor.core

import android.text.Editable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.supportedSpans
import com.wordsnchars.text_editor.utils.createGap
import com.wordsnchars.text_editor.utils.setSpan
import com.wordsnchars.text_editor.utils.*
import kotlin.math.max

class TextWatcherTextEditor(
    private val viewModel: ViewModelTextEditor,
) : TextWatcher {

    private val TAG = "TextWatcher"

    val symbolThatSystemTreatsAsEndOfInsert = listOf(' ', ',')
    var triggered = false
    private var backspacePressed = false

    var start = 0
    private var lengthBefore = 0
    private var delta = 0
    var cursorPosition = 0

    //gotta get come up with a proper encapsulation
    var currentSpansStarts = run {
        val starts = mutableMapOf<Any, Int>()
        supportedSpans.forEach { spanType -> starts[spanType] = 0 }
        starts
    }
    var insertLength = run {
        val lengths = mutableMapOf<Any, Int>()
        supportedSpans.forEach { spanType -> lengths[spanType] = 0 }
        lengths
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        triggered = true
        lengthBefore = s!!.length
        this.start =
            if (start == 0 || s[start - 1] in symbolThatSystemTreatsAsEndOfInsert) start
            else this.start
        Log.v(TAG, "triggered textwatcher")
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //detect if it was deletion on previous trigger of tw and
        if (backspacePressed) {
            currentSpansStarts.keys.forEach {
                currentSpansStarts[it] = cursorPosition
            }
            insertLength.keys.forEach {
                insertLength[it] = 0
            }
            this.start = cursorPosition
        }

        delta = s.length - lengthBefore
        backspacePressed = delta < 0
        if (backspacePressed) println("backspace pressed)))")
        insertLength.keys.forEach {
            insertLength[it] = insertLength[it]!! + if (!backspacePressed) delta else 0
        }

    }

    override fun afterTextChanged(s: Editable) {
        //starting to applying spans
        s.handleModifier(BackgroundColorSpan(viewModel.highlightColor.value))
//        s.handleModifier(StyleSpan(viewModel.style.value))
//        s.handleModifier(RelativeSizeSpan(viewModel.fontSizeMultiplier.value))
//        if (viewModel.underlined.value) s.handleModifier(UnderlineSpan())
//        if (viewModel.strikeThrough) s.handleModifier(StrikethroughSpan())
    }

    private fun Editable.handleModifier(
        associatedSpan: Any,
    ) {
        //calculate border for span that is being set
        //this part should be commented for sure)))
        val predictedSpanStart =
            max(currentSpansStarts[associatedSpan::class.java] ?: cursorPosition, start)
        val predictedSpanLength = insertLength[associatedSpan::class.java] ?: 0
        val spanBorder = Border(predictedSpanStart, predictedSpanStart + predictedSpanLength)

        Log.v(TAG, "predicted border: $spanBorder $cursorPosition")
        viewModel.previouslySetSpans[associatedSpan::class.java].let {
            while (it?.isNotEmpty() == true) with(it.last()) {
                //return span from cache if it's out of new border
                if (this.second outside spanBorder
                    && !(backspacePressed && cursorPosition inside this.second)
                    && !this@handleModifier.hasSimilar(this.first, this.second))
                    this@handleModifier.setSpan(this.first, this.second)
                viewModel.previouslySetSpans[associatedSpan::class.java]!!.remove(this)
            }
        }

        this.createGap(associatedSpan::class.java, spanBorder)


        if (!this.hasSimilar(associatedSpan, spanBorder))
            this.setSpan(associatedSpan, spanBorder)

        this.getSpans(0, length, BackgroundColorSpan::class.java).forEach {
            Log.v(TAG, "$it ${it.backgroundColor} ${this.getSpanStart(it)} ${this.getSpanEnd(it)}")
        }
    }
}
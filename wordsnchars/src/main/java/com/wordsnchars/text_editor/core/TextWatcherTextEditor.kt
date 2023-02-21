package com.wordsnchars.text_editor.core

import android.text.Editable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.util.Log
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.supportedSpans
import com.wordsnchars.text_editor.utils.createGap
import com.wordsnchars.text_editor.utils.setSpan
import com.wordsnchars.text_editor.utils.*
import kotlin.math.max
import kotlin.math.min

class TextWatcherTextEditor(
    private val viewModel: ViewModelTextEditor,
) : TextWatcher {

    private val TAG = "TextWatcher"

    private val symbolThatSystemTreatsAsEndOfInsert = listOf(' ', ',')
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
        //what the hell is going on here i don't understand now
        triggered = true
        lengthBefore = s!!.length

        //set start of the word, that likely will be treated system as start of insert
        this.start =
            (if (start == 0 || s[start - 1] in symbolThatSystemTreatsAsEndOfInsert) {
                if (this.start != start) insertLength.keys.forEach { insertLength[it] = 0 }
                start
            } else this.start)
        Log.v(TAG, "triggered textwatcher")
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //what the hell is going on here i don't understand now
        delta = s.length - lengthBefore
        cursorPosition += delta
        backspacePressed = delta < 0
        if (backspacePressed) {
            currentSpansStarts.keys.forEach { currentSpansStarts[it] = cursorPosition }
            insertLength.keys.forEach { insertLength[it] = 0 }
            this.start = cursorPosition
        }
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

        Log.v(
            TAG,
            "predicted border: $spanBorder len: $length cursor pos: $cursorPosition and $start pred start: $predictedSpanStart  pred len: $predictedSpanLength"
        )

        restoreCached(
            this,
            spanBorder,
            associatedSpan::class.java,
            viewModel.previouslySetSpans[associatedSpan::class.java]
        )

        this.createGap(associatedSpan::class.java, spanBorder)

        if (!this.hasSimilar(associatedSpan, spanBorder)) {
            println("setting as routine $spanBorder")
            this.setSpan(
                associatedSpan,
                spanBorder
            )
        }

        viewModel.previouslySetSpans[associatedSpan::class.java] = mutableListOf()

        this.getSpans(0, length, BackgroundColorSpan::class.java).forEach {
            Log.v(TAG, "string has: $it ${it.backgroundColor} ${this.getBorder(it)}")
        }

    }

    private fun restoreCached(
        text: Editable,
        untouchableZone: Border,
        spanType: Class<*>,
        cachedSpans: MutableList<Pair<Any, Border>>?,
    ) {
        while (cachedSpans?.isNotEmpty() == true) with(cachedSpans.last()) {
            //return span from cache if it's out of new border
            Log.v(TAG, "info: $cursorPosition ${this.second}")
            if (this.second outside untouchableZone && (!backspacePressed && cursorPosition outside this.second)) {
                if (!text.hasSimilar(this.first, this.second)) {
                    Log.v(TAG, "setting from the cache casual span ${this.second}")
                    text.setSpan(
                        this.first,
                        this.second
                    )
                }
            }
            viewModel.previouslySetSpans[spanType]!!.remove(this)

        }
    }

}
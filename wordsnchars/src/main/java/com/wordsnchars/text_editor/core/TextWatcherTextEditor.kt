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
    private var deletedZone = Border(0, 0)

    //for "delete ahead" bug
    private var normalHandle = true

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
        if (!(before > 1 && count == 0)) {
            delta = s.length - lengthBefore
            normalHandle = true
            cursorPosition += delta
            backspacePressed = delta < 0
            if (backspacePressed) {
                deletedZone = Border(cursorPosition, cursorPosition - delta)
                currentSpansStarts.keys.forEach { currentSpansStarts[it] = cursorPosition }
                insertLength.keys.forEach { insertLength[it] = 0 }
                this.start = cursorPosition
            }
            insertLength.keys.forEach {
                insertLength[it] = insertLength[it]!! + if (!backspacePressed) delta else 0
            }
        } else {
            Log.v(TAG, "detected strange deletion $cursorPosition $start $before $count")
            normalHandle = false
        }
    }

    override fun afterTextChanged(s: Editable) {
        //starting to applying spans
        if (normalHandle) {
            handleModifier(s, BackgroundColorSpan(viewModel.highlightColor.value))
//        s.handleModifier(StyleSpan(viewModel.style.value))
//        s.handleModifier(RelativeSizeSpan(viewModel.fontSizeMultiplier.value))
//        if (viewModel.underlined.value) s.handleModifier(UnderlineSpan())
//        if (viewModel.strikeThrough) s.handleModifier(StrikethroughSpan())
        }
    }

    fun handleModifier(
        text: Editable,
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
            "predicted border: $spanBorder len: ${text.length} cursor pos: $cursorPosition and $start pred start: $predictedSpanStart  delta: $delta"
        )

        restoreCached(
            text,
            spanBorder,
            associatedSpan::class.java,
            viewModel.previouslySetSpans[associatedSpan::class.java]
        )

        text.createGap(associatedSpan::class.java, spanBorder)

        if (!text.hasSimilar(associatedSpan, spanBorder)) {
            println("setting as routine $spanBorder")
            text.setSpan(
                associatedSpan,
                spanBorder
            )
        }

        viewModel.previouslySetSpans[associatedSpan::class.java] = mutableListOf()

        text.getSpans(0, text.length, BackgroundColorSpan::class.java).forEach {
            Log.v(TAG, "string has: $it ${it.backgroundColor} ${text.getBorder(it)}")
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
            if (!(backspacePressed && deletedZone == this.second)
                && this.second outside untouchableZone
                && !text.hasSimilar(this.first, this.second)
            ) {
                Log.v(TAG, "setting from the cache casual span ${this.second}")
                text.setSpan(
                    this.first,
                    this.second
                )
            }
            viewModel.previouslySetSpans[spanType]!!.remove(this)

        }
    }

}
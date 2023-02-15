package com.wordsnchars.text_editor.core

import android.text.Editable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.util.Log
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.text_editor.utils.createGap
import com.wordsnchars.text_editor.getBorder
import com.wordsnchars.text_editor.utils.setSpan
import com.wordsnchars.text_editor.utils.*

class TextWatcherTextEditor(
    private val viewModel: ViewModelTextEditor,
) : TextWatcher {
    var triggered = false
    private val TAG = "TextWatcher"
    private var backspacePressed = false

    private var _lengthBefore = 0
    val lengthBefore get() = _lengthBefore
    private var _delta = 0
    val delta get() = _delta

    //remake it for
    private lateinit var insideBorder: Border
    var insertLength = 1
    private var outsideBorder = Border(0, 0)


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _lengthBefore = s!!.length
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //mark that backspace was pressed
        backspacePressed = before > count
        if (backspacePressed) {
            Log.v(TAG, "backspace press")
            insertLength = 1
            viewModel.currentSpansStarts.clear()
        }

        _delta = s.length - _lengthBefore

        insertLength += _delta


        Log.v(
            TAG,
            "text is being changed start $start  before $before count $count spanLength $insertLength delta $_delta "
        )
    }

    override fun afterTextChanged(s: Editable) {

        triggered = true
        //highlight
        s.handleModifier(BackgroundColorSpan(viewModel.highlightColor.value))

    }

    private fun Editable.handleModifier(
        associatedSpan: Any,
    ) {

        Log.v(TAG, "starting ${associatedSpan::class.java} routine")

        val predictedSpanStart =
            viewModel.currentSpansStarts[associatedSpan::class.java] ?: viewModel.cursorPosition
        insideBorder = Border(predictedSpanStart, predictedSpanStart + insertLength)

        viewModel.previouslySetSpans[associatedSpan::class.java].let {
            Log.v(TAG, "cached spans: ${it?.size}")

            while (it?.isNotEmpty() == true) {
                with(it.last()) {
                    //return span from cache if it's out of new border
                    if (!(this.second In insideBorder ||
                                //this crutch works only with deleting only one symbol
                                backspacePressed && viewModel.cursorPosition In insideBorder)
                    ) {
                        this@handleModifier.setSpan(this.first, this.second)
                    }
                    viewModel.previouslySetSpans[associatedSpan::class.java]!!.remove(this)
                }
            }
        }

        //create gap in text to set new spans in the given range
        this.createGap(associatedSpan::class.java, this@TextWatcherTextEditor.insideBorder)


        //set new span with given borders
        this.setSpan(associatedSpan, this@TextWatcherTextEditor.insideBorder)
        Log.v(TAG, "ended ${associatedSpan::class.java} routine")

        //checking that there are only proper span
        this.getSpans(0, length, associatedSpan::class.java).forEach {
            Log.v(TAG, "string has $it ${getBorder(it)}")
        }


    }
}
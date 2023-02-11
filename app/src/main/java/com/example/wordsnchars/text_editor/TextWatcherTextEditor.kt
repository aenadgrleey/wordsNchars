package com.example.wordsnchars.text_editor

import android.text.Editable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.util.Log
import com.example.xconspectus.ui.text_editor.ViewModelTextEditor

class TextWatcherTextEditor(
    private val viewModel: ViewModelTextEditor,
) : TextWatcher {
    private val TAG = "TextWatcher"
    private var _backspaceFlag = false

    private var lengthBefore = 0
    private var _delta = 0
    val delta get() = _delta

    private var _insideBorder = Border(0, 0)
    val insideBorder get() = _insideBorder
    var insertLength = 1
    private var outsideBorder = Border(0, 0)


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        lengthBefore = s!!.length
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //mark that backspace was pressed
        _backspaceFlag = before > count
        if (_backspaceFlag) {
            Log.v(TAG, "backspace press")
            insertLength = 1
            viewModel.currentSpansStarts.clear()
        }

        _delta = s.length - lengthBefore

        insertLength += _delta

        outsideBorder = Border(start, start + count)


        Log.v(
            TAG,
            "text is being changed start $start  before $before count $count spanLength $insertLength delta $_delta "
        )
    }

    override fun afterTextChanged(s: Editable) {
        //highlight
        s.handleModifier(BackgroundColorSpan(viewModel.highlightColor.value))
    }

    private fun Editable.handleModifier(
        associatedSpan: Any,
    ) {
        val predictedSpanStart =
            viewModel.currentSpansStarts[associatedSpan::class.java]?: viewModel.cursorPosition
        _insideBorder = Border(predictedSpanStart, predictedSpanStart + insertLength)

        Log.v(TAG, "starting ${associatedSpan::class.java} routine")
        Log.v(TAG, "predicted border: $_insideBorder for $associatedSpan")

        Log.v(TAG, "spans on s: ${this.getSpans(insideBorder.start, insideBorder.end, BackgroundColorSpan::class.java).size}")


        viewModel.previouslySetSpans[associatedSpan::class.java].let { it ->
            Log.v(TAG, "cached spans: ${it?.size}")

            while (it!!.isNotEmpty()) {
                with(it.last()) {
                    //return span from cache if it's out of new border
                    if (!(this.second In _insideBorder))
                        this@handleModifier.setSpan(this.first, this.second)
                    viewModel.previouslySetSpans[associatedSpan::class.java]!!.remove(this)
                }
            }
        }

        //create gap in text to set new spans in the given range
        this.createGap(associatedSpan::class.java, insideBorder)

        //set new span with given borders
        Log.v(TAG, this.setSpan(associatedSpan, _insideBorder).toString())
    }
}
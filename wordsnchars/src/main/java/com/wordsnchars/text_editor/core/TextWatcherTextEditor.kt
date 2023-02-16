package com.wordsnchars.text_editor.core

import android.text.Editable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.text_editor.utils.createGap
import com.wordsnchars.text_editor.utils.getBorder
import com.wordsnchars.text_editor.utils.setSpan
import com.wordsnchars.text_editor.utils.*

class TextWatcherTextEditor(
    private val viewModel: ViewModelTextEditor,
) : TextWatcher {

    private val TAG = "TextWatcher"

    //gotta get come up with a proper encapsulation
    var triggered = false
    private var backspacePressed = false

    //gotta get come up with a proper encapsulation
    var insertLength = mutableMapOf<Any, Int>(
        BackgroundColorSpan::class.java to 0,
        StyleSpan::class.java to 0
    )
    private var lengthBefore = 0
    private var delta = 0
    private lateinit var insideBorder: Border
    var cursorPosition = 0
    var currentSpansStarts = mutableMapOf<Any, Int>(
        BackgroundColorSpan::class.java to 0,
        StyleSpan::class.java to 0
    )


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        triggered = true
        lengthBefore = s!!.length
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //detect if it was deletion on previous trigger of tw and
        if (backspacePressed) {
            insertLength.keys.forEach {
                insertLength[it] = 0
            }
            currentSpansStarts.keys.forEach {
                currentSpansStarts[it] = cursorPosition
            }
        }
        backspacePressed = before > count

        delta = s.length - lengthBefore
        insertLength.keys.forEach {
            insertLength[it] = insertLength[it]!! + delta
        }

    }

    override fun afterTextChanged(s: Editable) {
        //starting to applying spans

        //highlight
        s.handleModifier(BackgroundColorSpan(viewModel.highlightColor.value))

        //style
        s.handleModifier(StyleSpan(viewModel.style.value))

    }

    private fun Editable.handleModifier(
        associatedSpan: Any,
    ) {

        Log.v(TAG, "starting ${associatedSpan::class.java} routine")

        //calculate border for span that is being set
        //this part should be commented for sure)))

        val predictedSpanStart =
            currentSpansStarts[associatedSpan::class.java] ?: cursorPosition
        val predictedSpanLength = insertLength[associatedSpan::class.java] ?: 0
        insideBorder = Border(predictedSpanStart, predictedSpanStart + predictedSpanLength)

        viewModel.previouslySetSpans[associatedSpan::class.java].let {
            while (it?.isNotEmpty() == true) {
                with(it.last()) {
                    //return span from cache if it's out of new border
                    if (!(this.second inside insideBorder ||
                                //this crutch works only with deleting only one symbol
                                backspacePressed && cursorPosition inside insideBorder)
                    ) {
                        Log.v(TAG, "set from cache ${this.first}")
                        this@handleModifier.setSpan(this.first, this.second)
                    }
                    viewModel.previouslySetSpans[associatedSpan::class.java]!!.remove(this)
                }
            }
        }

        this.createGap(associatedSpan::class.java, this@TextWatcherTextEditor.insideBorder)


        this.setSpan(associatedSpan, this@TextWatcherTextEditor.insideBorder)

        //checking that there are only proper span, thing for debug build
        this.getSpans(0, length, BackgroundColorSpan::class.java).forEach {
            Log.v(TAG, "string has ${it.backgroundColor} ${getBorder(it)}")
        }

        Log.v(TAG, "ended ${associatedSpan::class.java} routine")

    }
}
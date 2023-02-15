package com.wordsnchars.text_editor.core

import android.text.Editable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
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
    //ahahah it's not gonna work with more than one span))) because here's common insertLength
    var insertLength = 1
    private var lengthBefore = 0
    private var delta = 0
    private lateinit var insideBorder: Border


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        triggered = true
        lengthBefore = s!!.length
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //detect if it was deletion on previous trigger of tw and
        if (backspacePressed) {
            insertLength = 0
            //huh i'm sorry? what is the fucking point of this action
            //on a back press we reset the map of span starts
            //actually it seems useless, because we reset it trough texteditor/selectionwatcher,
            // but i'm afraid to delete it with out a proper list of tests))
            viewModel.currentSpansStarts.clear()
        }
        backspacePressed = before > count

        delta = s.length - lengthBefore
        insertLength += delta

    }

    override fun afterTextChanged(s: Editable) {

        //highlight
        s.handleModifier(BackgroundColorSpan(viewModel.highlightColor.value))

    }

    private fun Editable.handleModifier(
        associatedSpan: Any,
    ) {

        Log.v(TAG, "starting ${associatedSpan::class.java} routine")

        //calculate border for span that is being set
        //this part should be commented for sure)))

        val predictedSpanStart =
            viewModel.currentSpansStarts[associatedSpan::class.java] ?: viewModel.cursorPosition
        insideBorder = Border(predictedSpanStart, predictedSpanStart + insertLength)

        viewModel.previouslySetSpans[associatedSpan::class.java].let {
            while (it?.isNotEmpty() == true) {
                with(it.last()) {
                    //return span from cache if it's out of new border
                    if (!(this.second inside insideBorder ||
                                //this crutch works only with deleting only one symbol
                                backspacePressed && viewModel.cursorPosition inside insideBorder)
                    ) {
                        this@handleModifier.setSpan(this.first, this.second)
                    }
                    viewModel.previouslySetSpans[associatedSpan::class.java]!!.remove(this)
                }
            }
        }

        this.createGap(associatedSpan::class.java, this@TextWatcherTextEditor.insideBorder)


        this.setSpan(associatedSpan, this@TextWatcherTextEditor.insideBorder)
        Log.v(TAG, "ended ${associatedSpan::class.java} routine")

        //checking that there are only proper span, thing for debug build
        this.getSpans(0, length, associatedSpan::class.java).forEach {
            Log.v(TAG, "string has $it ${getBorder(it)}")
        }


    }
}
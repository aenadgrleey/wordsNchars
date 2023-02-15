package com.wordsnchars.text_editor.utils

class Border(val start: Int, val end: Int) {
    init {
        //should implement some check for improper borders
        //it's not good to have several check in different places instead of having one in init block
        //in some cases already initializing with negative or "end < start" borders
    }
    fun hasImproperLength(): Boolean {
        return start >= end
    }

    fun hasZeroLength(): Boolean {
        return this.start == this.end
    }

    infix fun inside(border: Border): Boolean {
        if (this.hasImproperLength() || border.hasImproperLength()) return false
        return border.start <= this.start && this.end <= border.end
    }

    infix fun isOverlappedBy(border: Border): Boolean {
        return this.start inside border || this.end inside border
    }

    override fun toString(): String {
        return "Border($start, $end)"
    }
}

infix fun Int.inside(border: Border): Boolean {
    return border.start <= this && this <= border.end

}
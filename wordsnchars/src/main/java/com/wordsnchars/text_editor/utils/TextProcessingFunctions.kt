package com.wordsnchars.text_editor.utils

/**
 * class to store values of start and end of something. was built as util tool for span using
 */
class Border(val start: Int, val end: Int) {
    init {
        //should implement some check for improper borders
        //it's not good to have several check in different places instead of having one in init block
        //in some cases already initializing with negative or "end < start" borders
    }

    /**
     * checks if length is negative or zero
     */
    fun hasImproperLength(): Boolean {
        return start >= end
    }

    /**
     * checks if length is zero
     */
    fun hasZeroLength(): Boolean {
        return this.start == this.end
    }

    /**
     * checks if this border is fully inside another that was taken as integer
     */
    infix fun inside(border: Border): Boolean {
        if (this.hasImproperLength() || border.hasImproperLength()) return false
        return border.start <= this.start && this.end <= border.end
    }

    /**
     * checks if borders have common part
     */
    infix fun isOverlappedBy(border: Border): Boolean {
        if (this.hasImproperLength() || border.hasImproperLength()) return false
        return this.start inside border || this.end inside border
    }

    override fun toString(): String {
        return "Border($start, $end)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Border && other.start == this.start && other.end == this.end) return true
        return super.equals(other)
    }
}

/**
 * checks if position is inside given border
 */
infix fun Int.inside(border: Border): Boolean {
    return border.start <= this && this <= border.end

}
package com.wordsnchars.text_editor.utils

/**
 * class to store values of start and end of something. was built as util tool for span using
 */
class Border(val start: Int, val end: Int) {

    init {
        if (start > end) throw ImproperBordersException("Tried to initiate border with improper borders $start > $end")
    }

    /**
     * checks if length is negative or zero
     */
    fun hasZeroLength(): Boolean {
        return start == end
    }

    /**
     * checks if this border is fully inside another that was taken as integer
     */
    infix fun inside(border: Border): Boolean {
        if (this.hasZeroLength() || border.hasZeroLength()) return false
        return border.start <= this.start && this.end <= border.end
    }

    /**
     * checks if this border is fully outside (no)k another that was taken as integer
     */
    infix fun outside(border: Border): Boolean {
        return !(this inside border)
    }

    /**
     * checks if borders have common part
     */
    infix fun isOverlappedBy(border: Border): Boolean {
        if (this.hasZeroLength() || border.hasZeroLength()) return false
        return this.start inside border || this.end inside border || border.start inside this || border.end inside this
    }

    fun length(): Int {
        return end - start
    }

    override fun toString(): String {
        return "Border($start, $end)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Border && other.start == this.start && other.end == this.end) return true
        return super.equals(other)
    }

    operator fun plus(delta: Int): Border {
        return Border(this.start + delta, this.end + delta)
    }
}

/**
 * checks if position is inside given border
 */
infix fun Int.inside(border: Border): Boolean {
    return border.start <= this && this <= border.end

}

infix fun Int.outside(border: Border): Boolean {
    return !(this inside border)

}

class ImproperBordersException(override val message: String?) : Exception()
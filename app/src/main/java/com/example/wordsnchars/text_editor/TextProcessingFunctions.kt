package com.example.wordsnchars.text_editor



class Border(val start: Int, val end: Int) {
//    init {
//        if (start > end) throw Exception("tried to create incorrect border $this")
//    }
    fun hasImproperLength(): Boolean {
        return start >= end
    }

    fun hasZeroLength(): Boolean {
        return this.start == this.end
    }

    infix fun In(border: Border): Boolean {
        if (this.hasImproperLength() || border.hasImproperLength()) return false
        return border.start <= this.start && this.end <= border.end
    }

    infix fun isOverlayedBy(border: Border): Boolean {
        return this.start In border || this.end In border
    }

    override fun toString(): String {
        return "Border($start, $end)"
    }
}

infix fun Int.In(border: Border): Boolean {
    return border.start <= this && this <= border.end

}
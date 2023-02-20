package com.wordsnchars.text_editor.utils

import com.google.common.truth.Truth.assertThat

import org.junit.Test

internal class BorderTest {

    //actually i'm so fucking bored with this stupid shit. that is so boring that i don't really want to repeat it.
    //but turns out that i have to while working alone
    @Test
    fun `improper border with 2 negatives`() {
        val result = Border(0, -1).hasZeroLength()
        assertThat(result).isTrue()
    }

    @Test
    fun `improper border with 1 negative and 1 positive`() {
        var result = Border(5, -1).hasZeroLength()
        assertThat(result).isTrue()
    }

    @Test
    fun `improper border with 2 positive`() {
        val result = Border(10, 9).hasZeroLength()
        assertThat(result).isTrue()
    }

    @Test
    fun `inside`() {
        val result = Border(1, 9) inside Border(-1, 100)
        assertThat(result).isTrue()
    }

    @Test
    fun `inside with improper values`() {
        val result = Border(10, 9) inside Border(-5, -9)
        assertThat(result).isFalse()
    }

    @Test
    fun `inside with limit values`() {
        val result = Border(4, 9) inside Border(4, 9)
        assertThat(result).isTrue()
    }

    @Test
    fun `inside with this border contains given`() {
        val result = Border(1, 10) inside Border(4, 5)
        assertThat(result).isFalse()
    }

    @Test
    fun `inside that no-one doesn't contain anyone`() {
        val result = Border(1, 9) inside Border(10, 100)
        assertThat(result).isFalse()
    }

    @Test
    fun `inside with touching borders at end`() {
        val result = Border(1, 9) inside Border(9, 100)
        assertThat(result).isFalse()
    }

    @Test
    fun `not overlapped`() {
        val result = Border(1, 10) isOverlappedBy Border(11, 1000)
        assertThat(result).isFalse()
    }


    @Test
    fun `overlapped`() {
        val result = Border(1, 10) isOverlappedBy Border(-5, 100)
        assertThat(result).isTrue()
    }

    @Test
    fun `overlapped from right`() {
        val result = Border(1, 9) isOverlappedBy Border(5, 100)
        assertThat(result).isTrue()
    }

    @Test
    fun `overlapped from left`() {
        val result = Border(1, 9) isOverlappedBy Border(-5, 5)
        assertThat(result).isTrue()
    }

    @Test
    fun `overlapped equal`() {
        val result = Border(1, 9) isOverlappedBy Border(1, 9)
        assertThat(result).isTrue()
    }

    @Test
    fun `overlapped with two improper`() {
        val result = Border(0, -1) isOverlappedBy Border(-5, -10)
        assertThat(result).isFalse()
    }

}
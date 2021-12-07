package com.guness.ksolana.utils

import junit.framework.TestCase
import org.junit.Assert.assertArrayEquals
import org.junit.Test

/**
 * Created by guness on 6.12.2021 23:26
 */
class ShortVectorEncodingTest : TestCase() {

    @Test
    fun testEncodeLength() {
        assertArrayEquals(byteArrayOf(0), ShortVectorEncoding.encodeLength(0))
        assertArrayEquals(byteArrayOf(1), ShortVectorEncoding.encodeLength(1))
        assertArrayEquals(byteArrayOf(5), ShortVectorEncoding.encodeLength(5))
        assertArrayEquals(byteArrayOf(127), ShortVectorEncoding.encodeLength(127)) // 0x7f
        assertArrayEquals(byteArrayOf(-128, 1), ShortVectorEncoding.encodeLength(128)) // 0x80
        assertArrayEquals(byteArrayOf(-1, 1), ShortVectorEncoding.encodeLength(255)) // 0xff
        assertArrayEquals(byteArrayOf(-128, 2), ShortVectorEncoding.encodeLength(256)) // 0x100
        assertArrayEquals(byteArrayOf(-1, -1, 1), ShortVectorEncoding.encodeLength(32767)) // 0x7fff
        assertArrayEquals(
            byteArrayOf(-128, -128, -128, 1),
            ShortVectorEncoding.encodeLength(2097152)
        ) // 0x200000
    }
}
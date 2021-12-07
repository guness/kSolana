package com.guness.ksolana.utils

/**
 * Created by guness on 6.12.2021 15:57
 */
object ByteUtils {
    fun readBytes(src: ByteArray, offset: Int, length: Int): ByteArray {
        val b = ByteArray(length)
        System.arraycopy(src, offset, b, 0, length)
        return b
    }

    fun uint16ToByteArrayLE(value: Int, out: ByteArray, offset: Int) {
        for (i in 0..1) {
            out[offset + i] = (0xFF and (value shr (i * 8))).toByte()
        }
    }

    fun int64ToByteArrayLE(value: Long, out: ByteArray, offset: Int) {
        for (i in 0..7) {
            out[offset + i] = (0xFFL and (value shr (i * 8))).toByte()
        }
    }

    fun uint32ToByteArrayLE(value: Long, out: ByteArray, offset: Int) {
        for (i in 0..3) {
            out[offset + i] = (0xFFL and (value shr (i * 8))).toByte()
        }
    }
}
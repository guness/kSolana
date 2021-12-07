package com.guness.ksolana.utils

import com.guness.ksolana.utils.ByteUtils.uint16ToByteArrayLE

/**
 * Created by guness on 6.12.2021 18:25
 */
object ShortVectorEncoding {
    fun encodeLength(len: Int): ByteArray {
        val out = ByteArray(10)
        var remLen = len
        var cursor = 0
        while (true) {
            var elem = remLen and 0x7F
            remLen = remLen shr 7
            if (remLen == 0) {
                uint16ToByteArrayLE(elem, out, cursor)
                break
            } else {
                elem = elem or 0x80
                uint16ToByteArrayLE(elem, out, cursor)
                cursor += 1
            }
        }
        val bytes = ByteArray(cursor + 1)
        System.arraycopy(out, 0, bytes, 0, cursor + 1)
        return bytes
    }
}

package com.guness.ksolana.core

import com.guness.ksolana.programs.SystemProgram.transfer
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import org.komputing.kbase58.decodeBase58


/**
 * Created by guness on 6.12.2021 23:57
 */

class MessageTest {
    @Test
    fun serializeMessage() {
        val fromPublicKey = PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo")
        val toPublicKey = PublicKey("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5")
        val lamports = 3000L

        val signer = Account("4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs".decodeBase58())
        val message = Message()
        message.addInstruction(transfer(fromPublicKey, toPublicKey, lamports))
        message.setRecentBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
        message.setFeePayer(signer)
        assertArrayEquals(
            intArrayOf(
                1, 0, 1, 3, 6, 26, 217, 208, 83, 135, 21, 72, 83, 126, 222, 62, 38, 24, 73, 163,
                223, 183, 253, 2, 250, 188, 117, 178, 35, 200, 228, 106, 219, 133, 61, 12, 235, 122, 188, 208, 216, 117,
                235, 194, 109, 161, 177, 129, 163, 51, 155, 62, 242, 163, 22, 149, 187, 122, 189, 188, 103, 130, 115,
                188, 173, 205, 229, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 203, 226, 136, 193, 153, 148, 240, 50, 230, 98, 9, 79, 221, 179, 243, 174, 90, 67,
                104, 169, 6, 187, 165, 72, 36, 156, 19, 57, 132, 38, 69, 245, 1, 2, 2, 0, 1, 12, 2, 0, 0, 0, 184, 11, 0,
                0, 0, 0, 0, 0
            ), toUnsignedByteArray(message.serialize())
        )
    }

    fun toUnsignedByteArray(bytes: ByteArray): IntArray {
        val out = IntArray(bytes.size)
        for (i in bytes.indices) {
            out[i] = bytes[i].toInt() and 0xFF
        }
        return out
    }
}

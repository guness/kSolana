package com.guness.ksolana.programs

import com.guness.ksolana.core.PublicKey
import com.guness.ksolana.programs.SystemProgram.createAccount
import com.guness.ksolana.programs.SystemProgram.transfer
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.komputing.kbase58.encodeToBase58String

/**
 * Created by guness on 7.12.2021 00:57
 */
class SystemProgramTest {

    @Test
    fun transferInstruction() {
        val fromPublicKey = PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo")
        val toPublicKey = PublicKey("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5")
        val lamports = 3000
        val (programId, keys, data) = transfer(fromPublicKey, toPublicKey, lamports.toLong())
        assertEquals(SystemProgram.PROGRAM_ID, programId)
        assertEquals(2, keys.size)
        assertEquals(toPublicKey, keys[1].publicKey)
        assertArrayEquals(byteArrayOf(2, 0, 0, 0, -72, 11, 0, 0, 0, 0, 0, 0), data)
    }

    @Test
    fun createAccountInstruction() {
        val (_, _, data) = createAccount(
            SystemProgram.PROGRAM_ID,
            SystemProgram.PROGRAM_ID, 2039280, 165, SystemProgram.PROGRAM_ID
        )
        assertEquals(
            "11119os1e9qSs2u7TsThXqkBSRUo9x7kpbdqtNNbTeaxHGPdWbvoHsks9hpp6mb2ed1NeB",
            data.encodeToBase58String()
        )
    }
}

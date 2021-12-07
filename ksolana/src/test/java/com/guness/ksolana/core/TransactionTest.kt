package com.guness.ksolana.core

import com.guness.ksolana.programs.SystemProgram
import org.junit.Assert.assertEquals
import org.junit.Test
import org.komputing.kbase58.decodeBase58
import java.util.*

/**
 * Created by guness on 6.12.2021 23:28
 */
class TransactionTest {
    private val signer: Account = Account("4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs".decodeBase58())

    @Test
    fun signAndSerialize() {
        val fromPublicKey = PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo")
        val toPublicKey = PublicKey("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5")
        val lamports = 3000L

        val transaction = Transaction()
        transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublicKey, lamports))
        transaction.setRecentBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
        transaction.sign(signer)
        val serializedTransaction = transaction.serialize()

        assertEquals(
            "ASdDdWBaKXVRA+6flVFiZokic9gK0+r1JWgwGg/GJAkLSreYrGF4rbTCXNJvyut6K6hupJtm72GztLbWNmRF1Q4BAAEDBhrZ0FOHFUhTft4+JhhJo9+3/QL6vHWyI8jkatuFPQzrerzQ2HXrwm2hsYGjM5s+8qMWlbt6vbxngnO8rc3lqgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAy+KIwZmU8DLmYglP3bPzrlpDaKkGu6VIJJwTOYQmRfUBAgIAAQwCAAAAuAsAAAAAAAA=",
            Base64.getEncoder().encodeToString(serializedTransaction)
        )
    }
}
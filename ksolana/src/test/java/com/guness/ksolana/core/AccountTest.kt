package com.guness.ksolana.core

import org.junit.Assert.assertEquals
import org.junit.Test
import org.komputing.kbase58.decodeBase58

/**
 * Created by guness on 7.12.2021 17:20
 */
class AccountTest {

    @Test
    fun accountFromSecretKey() {
        val secretKey = "4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs".decodeBase58()
        assertEquals("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo", Account(secretKey).publicKey.toString())
        assertEquals(64, Account(secretKey).secretKey.size)
    }

    @Test
    fun generateNewAccount() {
        val account = Account()
        assertEquals(64, account.secretKey.size)
    }

    @Test
    fun fromJson() {
        val json =
            "[94,151,102,217,69,77,121,169,76,7,9,241,196,119,233,67,25,222,209,40,113,70,33,81,154,33,136,30,208,45,227,28,23,245,32,61,13,33,156,192,84,169,95,202,37,105,150,21,157,105,107,130,13,134,235,7,16,130,50,239,93,206,244,0]"
        val acc = Account.fromJson(json)
        assertEquals("2cXAj2TagK3t6rb2CGRwyhF6sTFJgLyzyDGSWBcGd8Go", acc.publicKey.toString())
    }
}
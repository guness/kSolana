package com.guness.ksolana.core

import com.iwebpp.crypto.TweetNaclFast
import org.komputing.kbase58.decodeBase58
import org.komputing.kbase58.encodeToBase58String
import java.nio.ByteBuffer

/**
 * Created by guness on 6.12.2021 15:46
 */
data class Account(val secretKey: ByteArray) {

    constructor(account: String) : this(account.decodeBase58())
    constructor() : this(TweetNaclFast.Signature.keyPair().secretKey)

    val publicKey: PublicKey = PublicKey(TweetNaclFast.Signature.keyPair_fromSecretKey(secretKey).publicKey)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (!secretKey.contentEquals(other.secretKey)) return false

        return true
    }

    override fun hashCode(): Int = secretKey.contentHashCode()

    override fun toString(): String = secretKey.encodeToBase58String()

    companion object {
        /**
         * Creates an [Account] object from a Sollet-exported JSON string (array)
         * @param json Sollet-exported JSON string (array)
         * @return [Account] built from Sollet-exported private key
         */
        fun fromJson(json: String): Account {
            return Account(convertJsonStringToByteArray(json))
        }

        /**
         * Convert's a Sollet-exported JSON string into a byte array usable for [Account] instantiation
         * @param characters Sollet-exported JSON string
         * @return byte array usable in [Account] instantiation
         */
        private fun convertJsonStringToByteArray(characters: String): ByteArray {
            // Create resulting byte array
            val buffer: ByteBuffer = ByteBuffer.allocate(64)

            // Convert json array into String array
            val sanitizedJson = characters.replace("\\[".toRegex(), "").replace("]".toRegex(), "")
            val chars = sanitizedJson.split(",").toTypedArray()

            // Convert each String character into byte and put it in the buffer
            chars.forEach { character ->
                buffer.put(character.toInt().toByte())
            }
            return buffer.array()
        }
    }
}
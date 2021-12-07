package com.guness.ksolana.core

import com.guness.ksolana.utils.ByteUtils
import org.komputing.kbase58.decodeBase58
import org.komputing.kbase58.encodeToBase58String

/**
 * Created by guness on 6.12.2021 15:47
 */
data class PublicKey(private val pubKey: ByteArray) {

    init {
        require(pubKey.size <= PUBLIC_KEY_LENGTH) { "Invalid public key input" }
    }

    constructor(pubKey: String) : this(pubKey.decodeBase58())

    fun toByteArray(): ByteArray {
        return pubKey
    }

    fun toBase58(): String {
        return pubKey.encodeToBase58String()
    }

    override fun toString(): String {
        return toBase58()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublicKey

        if (!pubKey.contentEquals(other.pubKey)) return false

        return true
    }

    override fun hashCode(): Int {
        return pubKey.contentHashCode()
    }

    companion object {
        const val PUBLIC_KEY_LENGTH = 32

        fun readPubKey(bytes: ByteArray, offset: Int): PublicKey {
            return PublicKey(ByteUtils.readBytes(bytes, offset, PUBLIC_KEY_LENGTH))
        }

        fun valueOf(publicKey: String): PublicKey {
            return PublicKey(publicKey)
        }
    }
}

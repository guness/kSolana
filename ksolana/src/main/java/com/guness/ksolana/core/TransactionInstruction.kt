package com.guness.ksolana.core

/**
 * Created by guness on 6.12.2021 19:04
 */
data class TransactionInstruction(
    val programId: PublicKey,
    val keys: List<AccountMeta>,
    val data: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TransactionInstruction

        if (programId != other.programId) return false
        if (keys != other.keys) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = programId.hashCode()
        result = 31 * result + keys.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}

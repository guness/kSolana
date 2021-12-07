package com.guness.ksolana.programs

import com.guness.ksolana.core.AccountMeta
import com.guness.ksolana.core.PublicKey
import com.guness.ksolana.core.TransactionInstruction


/**
 * Created by guness on 6.12.2021 23:30
 */
open class Program {
    /**
     * Returns a [TransactionInstruction] built from the specified values.
     * @param programId Solana program we are calling
     * @param keys AccountMeta keys
     * @param data byte array sent to Solana
     * @return [TransactionInstruction] object containing specified values
     */
    fun createTransactionInstruction(programId: PublicKey, keys: List<AccountMeta>, data: ByteArray): TransactionInstruction {
        return TransactionInstruction(programId, keys, data)
    }
}

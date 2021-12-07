package com.guness.ksolana.programs

import com.guness.ksolana.core.AccountMeta
import com.guness.ksolana.core.PublicKey
import com.guness.ksolana.core.TransactionInstruction
import com.guness.ksolana.utils.ByteUtils.int64ToByteArrayLE
import com.guness.ksolana.utils.ByteUtils.uint32ToByteArrayLE

/**
 * Created by guness on 6.12.2021 23:32
 */
object SystemProgram : Program() {

    private const val PROGRAM_INDEX_CREATE_ACCOUNT = 0L
    private const val PROGRAM_INDEX_TRANSFER = 2L

    val PROGRAM_ID: PublicKey = PublicKey("11111111111111111111111111111111")

    fun transfer(
        fromPublicKey: PublicKey,
        toPublicKey: PublicKey,
        lamports: Long
    ): TransactionInstruction {
        val keys = ArrayList<AccountMeta>()
        keys.add(AccountMeta(fromPublicKey, signer = true, writable = true))
        keys.add(AccountMeta(toPublicKey, signer = false, writable = true))

        // 4 byte instruction index + 8 bytes lamports
        val data = ByteArray(4 + 8)
        uint32ToByteArrayLE(PROGRAM_INDEX_TRANSFER, data, 0)
        int64ToByteArrayLE(lamports, data, 4)

        return createTransactionInstruction(PROGRAM_ID, keys, data)
    }

    fun createAccount(
        fromPublicKey: PublicKey,
        newAccountPublicKey: PublicKey,
        lamports: Long, space: Long,
        programId: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(fromPublicKey, signer = true, writable = true))
        keys.add(AccountMeta(newAccountPublicKey, signer = true, writable = true))

        val data = ByteArray(4 + 8 + 8 + 32)
        uint32ToByteArrayLE(PROGRAM_INDEX_CREATE_ACCOUNT, data, 0)
        int64ToByteArrayLE(lamports, data, 4)
        int64ToByteArrayLE(space, data, 12)
        System.arraycopy(programId.toByteArray(), 0, data, 20, 32)

        return createTransactionInstruction(PROGRAM_ID, keys, data)
    }

}

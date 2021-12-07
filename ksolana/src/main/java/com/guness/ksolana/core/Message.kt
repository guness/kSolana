package com.guness.ksolana.core

import com.guness.ksolana.utils.ShortVectorEncoding
import org.komputing.kbase58.decodeBase58
import java.nio.ByteBuffer

/**
 * Created by guness on 6.12.2021 18:46
 */
class Message {
    private var messageHeader: MessageHeader? = null
    private var recentBlockhash: String? = null
    private val accountKeys = AccountKeysList()
    private val instructions = mutableListOf<TransactionInstruction>()
    private var feePayer: Account? = null

    fun addInstruction(instruction: TransactionInstruction): Message {
        accountKeys.addAll(instruction.keys)
        accountKeys.add(AccountMeta(instruction.programId, signer = false, writable = false))
        instructions.add(instruction)
        return this
    }

    fun setRecentBlockHash(recentBlockhash: String?) {
        this.recentBlockhash = recentBlockhash
    }

    fun serialize(): ByteArray {
        requireNotNull(recentBlockhash) { "recent blockhash required" }
        require(instructions.size != 0) { "No instructions provided" }
        messageHeader = MessageHeader()
        val keysList = getAccountKeys()
        val accountKeysSize = keysList.size
        val accountAddressesLength: ByteArray = ShortVectorEncoding.encodeLength(accountKeysSize)
        var compiledInstructionsLength = 0
        val compiledInstructions: MutableList<CompiledInstruction> = ArrayList()
        for (instruction in instructions) {
            val keysSize: Int = instruction.keys.size
            val keyIndices = ByteArray(keysSize)
            for (i in 0 until keysSize) {
                keyIndices[i] = findAccountIndex(keysList, instruction.keys.get(i).publicKey).toByte()
            }
            val compiledInstruction = CompiledInstruction(
                programIdIndex = findAccountIndex(keysList, instruction.programId).toByte(),
                keyIndicesCount = ShortVectorEncoding.encodeLength(keysSize),
                keyIndices = keyIndices,
                dataLength = ShortVectorEncoding.encodeLength(instruction.data.size),
                data = instruction.data
            )

            compiledInstructions.add(compiledInstruction)
            compiledInstructionsLength += compiledInstruction.length
        }

        val instructionsLength = ShortVectorEncoding.encodeLength(compiledInstructions.size)
        val bufferSize = (MessageHeader.HEADER_LENGTH + RECENT_BLOCK_HASH_LENGTH + accountAddressesLength.size
                + accountKeysSize * PublicKey.PUBLIC_KEY_LENGTH + instructionsLength.size
                + compiledInstructionsLength)

        val out = ByteBuffer.allocate(bufferSize)
        val accountKeysBuff = ByteBuffer.allocate(accountKeysSize * PublicKey.PUBLIC_KEY_LENGTH)
        for (accountMeta in keysList) {
            accountKeysBuff.put(accountMeta.publicKey.toByteArray())
            if (accountMeta.signer) {
                messageHeader!!.numRequiredSignatures = messageHeader!!.numRequiredSignatures.inc()
                if (!accountMeta.writable) {
                    messageHeader!!.numReadonlySignedAccounts = messageHeader!!.numReadonlySignedAccounts.inc()
                }
            } else {
                if (!accountMeta.writable) {
                    messageHeader!!.numReadonlyUnsignedAccounts = messageHeader!!.numReadonlyUnsignedAccounts.inc()
                }
            }
        }

        out.put(messageHeader!!.toByteArray())
        out.put(accountAddressesLength)
        out.put(accountKeysBuff.array())
        out.put(recentBlockhash!!.decodeBase58())
        out.put(instructionsLength)

        for (compiledInstruction in compiledInstructions) {
            out.put(compiledInstruction.programIdIndex)
            out.put(compiledInstruction.keyIndicesCount)
            out.put(compiledInstruction.keyIndices)
            out.put(compiledInstruction.dataLength)
            out.put(compiledInstruction.data)
        }
        return out.array()
    }

    fun setFeePayer(feePayer: Account?) {
        this.feePayer = feePayer
    }

    private fun getAccountKeys(): List<AccountMeta> {
        val keysList = accountKeys.getList().toMutableList()
        val feePayerIndex = findAccountIndex(keysList, feePayer!!.publicKey)
        val newList: MutableList<AccountMeta> = ArrayList()
        val feePayerMeta = keysList[feePayerIndex]
        newList.add(AccountMeta(feePayerMeta.publicKey, signer = true, writable = true))
        keysList.removeAt(feePayerIndex)
        newList.addAll(keysList)
        return newList
    }

    private fun findAccountIndex(accountMetaList: List<AccountMeta>, key: PublicKey): Int {
        for (i in accountMetaList.indices) {
            if (accountMetaList[i].publicKey.equals(key)) {
                return i
            }
        }
        throw RuntimeException("unable to find account index")
    }

    companion object {
        private const val RECENT_BLOCK_HASH_LENGTH = 32
    }
}

private class MessageHeader(
    var numRequiredSignatures: Byte = 0,
    var numReadonlySignedAccounts: Byte = 0,
    var numReadonlyUnsignedAccounts: Byte = 0
) {

    fun toByteArray(): ByteArray {
        return byteArrayOf(numRequiredSignatures, numReadonlySignedAccounts, numReadonlyUnsignedAccounts)
    }

    companion object {
        const val HEADER_LENGTH = 3
    }
}

private class CompiledInstruction(
    val programIdIndex: Byte = 0,
    val keyIndicesCount: ByteArray,
    val keyIndices: ByteArray,
    val dataLength: ByteArray,
    val data: ByteArray
) {

    // 1 = programIdIndex length
    val length: Int = 1 + keyIndicesCount.size + keyIndices.size + dataLength.size + data.size
}

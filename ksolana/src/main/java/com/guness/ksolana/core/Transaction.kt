package com.guness.ksolana.core

import com.guness.ksolana.utils.ShortVectorEncoding
import com.iwebpp.crypto.TweetNaclFast
import org.komputing.kbase58.decodeBase58
import org.komputing.kbase58.encodeToBase58String
import java.nio.ByteBuffer

/**
 * Created by guness on 6.12.2021 19:29
 */
class Transaction {
    private val message = Message()
    private val signatures = mutableListOf<String>()
    private var serializedMessage: ByteArray? = null

    fun addInstruction(instruction: TransactionInstruction?): Transaction {
        message.addInstruction(instruction!!)
        return this
    }

    fun setRecentBlockHash(recentBlockhash: String?) {
        message.setRecentBlockHash(recentBlockhash)
    }

    fun sign(signer: Account) {
        sign(listOf(signer))
    }

    fun sign(signers: List<Account>) {
        require(signers.isNotEmpty()) { "No signers" }
        val feePayer = signers[0]
        message.setFeePayer(feePayer)
        serializedMessage = message.serialize()
        for (signer in signers) {
            val signatureProvider = TweetNaclFast.Signature(ByteArray(0), signer.secretKey)
            val signature = signatureProvider.detached(serializedMessage)
            signatures.add(signature.encodeToBase58String())
        }
    }

    fun serialize(): ByteArray {
        val signaturesSize = signatures.size
        val signaturesLength: ByteArray = ShortVectorEncoding.encodeLength(signaturesSize)
        val out: ByteBuffer = ByteBuffer.allocate(signaturesLength.size + signaturesSize * SIGNATURE_LENGTH + serializedMessage!!.size)
        out.put(signaturesLength)
        for (signature in signatures) {
            out.put(signature.decodeBase58())
        }
        out.put(serializedMessage!!)
        return out.array()
    }

    class Builder {

        private val transaction = Transaction()

        fun addInstruction(transactionInstruction: TransactionInstruction) = apply { transaction.addInstruction(transactionInstruction) }

        fun setRecentBlockHash(recentBlockHash: String) = apply { transaction.setRecentBlockHash(recentBlockHash) }

        fun setSigners(signers: List<Account>) = apply { transaction.sign(signers) }

        fun build(): Transaction {
            return transaction
        }
    }

    companion object {
        const val SIGNATURE_LENGTH = 64
    }
}
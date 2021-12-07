package com.guness.ksolana.rpc

import com.guness.ksolana.core.Account
import com.guness.ksolana.core.PublicKey
import com.guness.ksolana.core.Transaction
import com.guness.ksolana.rpc.types.RecentBlockhash
import com.guness.ksolana.rpc.types.RpcResultTypes
import com.guness.ksolana.rpc.types.config.Commitment
import com.guness.ksolana.rpc.types.config.RpcSendTransactionConfig
import kotlinx.serialization.builtins.serializer
import java.util.Base64.getEncoder

/**
 * Created by guness on 6.12.2021 21:47
 */
class RpcApi(private val client: RpcClient) {

    @Throws(RpcException::class)
    suspend fun getRecentBlockhash(commitment: Commitment? = null): String? {
        val params = mutableListOf<Any>()

        commitment?.let {
            params.add(mapOf("commitment" to it.value))
        }

        return client.call("getRecentBlockhash", params, RecentBlockhash.serializer()).value?.blockhash
    }

    @Throws(RpcException::class)
    suspend fun sendTransaction(transaction: Transaction, signer: Account, recentBlockHash: String? = null): String {
        return sendTransaction(transaction, listOf(signer), recentBlockHash)
    }

    @Throws(RpcException::class)
    suspend fun sendTransaction(transaction: Transaction, signers: List<Account>, recentBlockHash: String?): String {

        val blockhash = recentBlockHash ?: getRecentBlockhash()

        transaction.setRecentBlockHash(blockhash)
        transaction.sign(signers)

        val serializedTransaction = transaction.serialize()
        val base64Trx = getEncoder().encodeToString(serializedTransaction)

        val params = listOf(base64Trx, RpcSendTransactionConfig())

        return client.call("sendTransaction", params, String.serializer())
    }

    @Throws(RpcException::class)
    suspend fun getBalance(account: PublicKey, commitment: Commitment? = null): Long {

        val params = mutableListOf<Any>()
        params.add(account.toString())

        commitment?.let {
            params.add(mapOf("commitment" to it.value))
        }
        return client.call("getBalance", params, RpcResultTypes.ValueLong.serializer()).value
    }
}
package com.guness.ksolana.rpc.types

import kotlinx.serialization.Serializable


/**
 * Created by guness on 6.12.2021 21:49
 */
@Serializable
class RecentBlockhash(val value: Value? = null) : RpcResultObject() {

    @Serializable
    class FeeCalculator(val lamportsPerSignature: Long = 0)

    @Serializable
    class Value(
        val feeCalculator: FeeCalculator? = null,
        val blockhash: String? = null
    )
}

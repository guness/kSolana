package com.guness.ksolana.rpc.types

import com.guness.ksolana.utils.DynamicLookupSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Created by guness on 6.12.2021 20:48
 */
@Serializable
class RpcRequest constructor(
    val method: String,
    val params: @Serializable List<@Serializable(with = DynamicLookupSerializer::class) Any>? = null
) {
    @SerialName("jsonrpc")
    val version = "2.0"
    val id: String = UUID.randomUUID().toString()
}

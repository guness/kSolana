package com.guness.ksolana.utils

import com.guness.ksolana.rpc.types.RecentBlockhash
import com.guness.ksolana.rpc.types.RpcResultObject
import com.guness.ksolana.rpc.types.RpcResultTypes
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic

/**
 * Created by guness on 6.12.2021 20:54
 */
val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    serializersModule = SerializersModule { }
}
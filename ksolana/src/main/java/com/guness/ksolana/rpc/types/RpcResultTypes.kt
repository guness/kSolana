package com.guness.ksolana.rpc.types

import kotlinx.serialization.Serializable


/**
 * Created by guness on 6.12.2021 22:09
 */
class RpcResultTypes {

    @Serializable
    class ValueLong(val value: Long = 0) : RpcResultObject()
}
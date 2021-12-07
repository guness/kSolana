package com.guness.ksolana.utils

import kotlinx.serialization.ContextualSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer

/*
https://github.com/Kotlin/kotlinx.serialization/issues/1351#issuecomment-788913996
 */
object DynamicLookupSerializer : KSerializer<Any> {
    override val descriptor: SerialDescriptor = ContextualSerializer(Any::class, null, emptyArray()).descriptor

    @Suppress("UNCHECKED_CAST")
    override fun serialize(encoder: Encoder, value: Any) {
        val actualSerializer = encoder.serializersModule.getContextual(value::class) ?: value::class.serializer()
        encoder.encodeSerializableValue(actualSerializer as KSerializer<Any>, value)
    }

    override fun deserialize(decoder: Decoder): Any {
        error("Unsupported")
    }
}

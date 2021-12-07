package com.guness.ksolana.rpc.types.config

/**
 * Created by guness on 6.12.2021 21:12
 */
enum class Commitment(val value: String) {
    FINALIZED("finalized"),
    CONFIRMED("confirmed"),
    PROCESSED("processed"),
    SINGLE_GOSSIP("singleGossip"),
    SINGLE("single"),
    ROOT("root"),
    RECENT("recent"),
    MAX("max")
}

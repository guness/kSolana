package com.guness.ksolana.rpc

/**
 * Created by guness on 6.12.2021 19:44
 */
enum class Cluster(val endpoint: String) {
    DEVNET("https://api.devnet.solana.com"),
    TESTNET("https://api.testnet.solana.com"),
    MAINNET("https://api.mainnet-beta.solana.com");
}


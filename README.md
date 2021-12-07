# kSolana
[![](https://jitpack.io/v/guness/kSolana.svg)](https://jitpack.io/#guness/kSolana)

Solana blockchain client, ported from [SolanaJ](https://github.com/skynetcapital/solanaj) to Kotlin using coroutines.
kSolana is an API for integrating with Solana blockchain using the [Solana RPC API](https://docs.solana.com/apps/jsonrpc-api)

## Requirements
- Java 11+
- Android Api 26+ (Only if you are developing Android App/Lib)

## Dependencies
- OkHttp
- KotlinSerialization
- Kotlin Coroutines
- Base58 from [KBase58](https://github.com/komputing/KBase58) 
- TweetNaCl from [TweetNaCl-java](https://github.com/InstantWebP2P/tweetnacl-java)

## How to
1. Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2. Add the dependency

```gradle
dependencies {
    implementation 'com.github.guness:kSolana:-SNAPSHOT' 
}
```

## Example
##### Transfer lamports

```kotlin
val api = RpcApi(RpcClient(Cluster.TESTNET))
val fromPublicKey = PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo")
val toPublicKey = PublicKey("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5")
val lamports = 3000L

val signer = Account("4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs")

val transaction = Transaction()
transaction.addInstruction(transfer(fromPublicKey, toPublicKey, lamports))

val signature = api.sendTransaction(transaction, signer)
```

##### Get balance

```kotlin
val api = RpcApi(RpcClient(Cluster.TESTNET))

val balance = api.getBalance(PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo")) 
```

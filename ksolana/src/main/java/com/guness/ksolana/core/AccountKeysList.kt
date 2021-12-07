package com.guness.ksolana.core

/**
 * Created by guness on 6.12.2021 16:19
 */
class AccountKeysList {

    private val accounts = mutableMapOf<String, AccountMeta>()

    fun add(accountMeta: AccountMeta) {
        val key = accountMeta.publicKey.toString()
        if (accounts.containsKey(key)) {
            if (!accounts[key]!!.writable && accountMeta.writable) {
                accounts[key] = accountMeta
            }
        } else {
            accounts[key] = accountMeta
        }
    }

    fun addAll(metas: Collection<AccountMeta>) {
        for (meta in metas) {
            add(meta)
        }
    }

    fun getList(): List<AccountMeta> {
        return accounts.values.sortedWith(metaComparator)
    }

    companion object {
        private val metaComparator: Comparator<AccountMeta> = object : Comparator<AccountMeta> {
            override fun compare(am1: AccountMeta, am2: AccountMeta): Int {
                val cmpSigner = if (am1.signer == am2.signer) 0 else if (am1.signer) -1 else 1

                if (cmpSigner != 0) return cmpSigner

                val cmpWritable = if (am1.writable == am2.writable) 0 else if (am1.writable) -1 else 1

                if (cmpWritable != 0) return cmpWritable

                return cmpSigner.compareTo(cmpWritable)
            }
        }
    }
}

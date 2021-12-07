package com.guness.ksolana.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.guness.ksolana.core.Account
import com.guness.ksolana.core.PublicKey
import com.guness.ksolana.core.Transaction
import com.guness.ksolana.programs.SystemProgram.transfer
import com.guness.ksolana.rpc.Cluster
import com.guness.ksolana.rpc.RpcApi
import com.guness.ksolana.rpc.RpcClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val button: Button by lazy { findViewById(R.id.button) }
    private val balanceView: TextView by lazy { findViewById(R.id.balance) }

    private val api by lazy { RpcApi(RpcClient(Cluster.TESTNET)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadBalance()

        button.setOnClickListener { makeTransfer() }
    }

    private fun loadBalance() {
        lifecycleScope.launch {
            val balance = api.getBalance(PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo")) / 1000000000f
            balanceView.text = "$balance SOL"
        }
    }

    private fun makeTransfer() {
        lifecycleScope.launch {

            val fromPublicKey = PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo")
            val toPublicKey = PublicKey("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5")
            val lamports = 3000

            val signer = Account("4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs")

            val transaction = Transaction()
            transaction.addInstruction(transfer(fromPublicKey, toPublicKey, lamports.toLong()))

            val signature = api.sendTransaction(transaction, signer)
            Log.d("kSolana", "signature: $signature")
            delay(4000)
            loadBalance()
        }
    }
}
package com.blue.whales.drink.water.bluewhalesdrinkwater

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blue.whales.drink.water.bluewhalesdrinkwater.App.Companion.gameApp
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.ActivityGuideBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Guide : AppCompatActivity() {
    private lateinit var binding: ActivityGuideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback {
            finish()
        }
        startProgress()
        gameApp.waterJson
    }
    private fun startProgress() {
        lifecycleScope.launch {
            while (true){
                binding.sP.incrementProgressBy(1)
                delay(20)
                if (binding.sP.progress == 100) {
                    jumpToMain()
                    break
                }
            }
        }
    }

    private fun jumpToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
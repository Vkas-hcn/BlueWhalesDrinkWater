package com.blue.whales.drink.water.bluewhalesdrinkwater

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.ActivityGuideBinding
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.ActivitySettingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingUi : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback {
            finish()
        }
        startProgress()
    }
    private fun startProgress() {
        binding.imageView.setOnClickListener {
            finish()
        }
        binding.tvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${this@SettingUi.packageName}")
            try {
                startActivity(Intent.createChooser(intent, "Share via"))
            } catch (ex: Exception) {
                // Handle error
            }
        }
        binding.tvPp.setOnClickListener {
            val intent = Intent(Intent .ACTION_VIEW)
            intent.data = Uri.parse("https://play.google.com")
            startActivity(intent)
        }
    }


}
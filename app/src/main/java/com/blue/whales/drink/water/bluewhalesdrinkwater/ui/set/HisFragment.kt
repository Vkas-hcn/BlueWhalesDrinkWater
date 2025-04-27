package com.blue.whales.drink.water.bluewhalesdrinkwater.ui.set

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.FragmentSetBinding

class HisFragment : Fragment() {

    private var _binding: FragmentSetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(HisViewModel::class.java)

        _binding = FragmentSetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        startProgress()
        return root
    }
    private fun startProgress() {

        binding.tvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${activity?.packageName}")
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
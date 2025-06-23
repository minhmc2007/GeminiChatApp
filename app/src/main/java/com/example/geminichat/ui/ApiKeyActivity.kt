package com.example.geminichat.ui
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geminichat.databinding.ActivityApiKeyBinding
class ApiKeyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiKeyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiKeyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("api_key_prefs", Context.MODE_PRIVATE)
        val apiKey = sharedPreferences.getString("gemini_api_key", null)
        if (!apiKey.isNullOrEmpty()) {
            startChatActivity()
            return
        }
        binding.saveApiKeyButton.setOnClickListener {
            val enteredApiKey = binding.apiKeyEditText.text.toString().trim()
            if (enteredApiKey.isNotEmpty()) {
                with(sharedPreferences.edit()) {
                    putString("gemini_api_key", enteredApiKey)
                    apply()
                }
                startChatActivity()
            }
        }
    }
    private fun startChatActivity() {
        startActivity(Intent(this, ChatActivity::class.java))
        finish()
    }
}

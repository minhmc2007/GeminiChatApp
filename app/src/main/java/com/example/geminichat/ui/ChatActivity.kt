package com.example.geminichat.ui
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geminichat.databinding.ActivityChatBinding
import com.example.geminichat.viewmodel.ChatViewModel
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<Pair<String, String>>()
    private var apiKey: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("api_key_prefs", Context.MODE_PRIVATE)
        apiKey = sharedPreferences.getString("gemini_api_key", null)
        if (apiKey.isNullOrEmpty()) {
            Toast.makeText(this, "API Key not found. Please restart the app.", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        chatAdapter = ChatAdapter(messages)
        binding.chatRecyclerView.adapter = chatAdapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.sendButton.setOnClickListener {
            val userMessage = binding.messageEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                binding.sendButton.isEnabled = false
                messages.add(Pair(userMessage, "")) // Add a placeholder for model response
                chatAdapter.notifyItemInserted(messages.size - 1)
                binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                viewModel.sendMessage(apiKey!!, userMessage)
                binding.messageEditText.text?.clear()
            }
        }
        viewModel.chatResponse.observe(this) { response ->
            binding.progressBar.visibility = View.GONE
            binding.sendButton.isEnabled = true
            messages[messages.size - 1] = messages.last().copy(second = response ?: "No response")
            chatAdapter.notifyItemChanged(messages.size - 1)
        }
        viewModel.error.observe(this) { error ->
            binding.progressBar.visibility = View.GONE
            binding.sendButton.isEnabled = true
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            messages.removeLast()
            chatAdapter.notifyItemRemoved(messages.size)
        }
    }
}

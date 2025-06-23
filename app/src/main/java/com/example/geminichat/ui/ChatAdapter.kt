package com.example.geminichat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geminichat.R

class ChatAdapter(private val messages: MutableList<Pair<String, String>>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userTextView: TextView = view.findViewById(R.id.userTextView)
        val modelTextView: TextView = view.findViewById(R.id.modelTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val (userMessage, modelMessage) = messages[position]
        holder.userTextView.text = "You: $userMessage"
        holder.modelTextView.text = "Gemini: $modelMessage"
    }

    override fun getItemCount() = messages.size
}

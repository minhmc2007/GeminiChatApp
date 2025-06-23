package com.example.geminichat.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch
class ChatViewModel : ViewModel() {
    private val _chatResponse = MutableLiveData<String>()
    val chatResponse: LiveData<String> = _chatResponse
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    fun sendMessage(apiKey: String, message: String) {
        viewModelScope.launch {
            try {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-pro",
                    apiKey = apiKey
                )
                val response = generativeModel.generateContent(message)
                _chatResponse.postValue(response.text)
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }
}

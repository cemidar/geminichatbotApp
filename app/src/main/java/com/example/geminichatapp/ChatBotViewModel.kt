package com.example.geminichatapp

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geminichatapp.data.ChatBot
import com.example.geminichatapp.data.ChatBotData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatBotViewModel: ViewModel() {

    //ChatBotState flows
    private val _chatBotState = MutableStateFlow(ChatBotState())
    val chatBotState = _chatBotState.asStateFlow()

    fun onEvent(event: ChatBotUiEvent){
        when (event) {
            is ChatBotUiEvent.sendChatMsg -> {
                if (event.ChatMsg.isNotEmpty()) {
                    addChatMsg(event.ChatMsg, event.ChatImage)

                    //if user sends only message with or without an image
                    if (event.ChatImage != null) {
                        getResponseWithImage(event.ChatMsg, event.ChatImage)
                    } else {
                        getResponse(event.ChatMsg)
                    }
                }
            }
            is ChatBotUiEvent.updateChatMsg -> {
                _chatBotState.update {
                    it.copy(chatmsg = event.newChatMsg)
                }
            }

        }
    }

    private fun addChatMsg(chatmsg: String, chatImage: Bitmap?){
        _chatBotState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, ChatBot(chatmsg, chatImage, true))
                },
                //after sending a message this will clear the input field.
                chatmsg = "",
                chatImage = null
            )
        }
    }

    //Get Response from user (no image)
    private fun getResponse(chatmsg: String) {
        viewModelScope.launch {
            val chat = ChatBotData.getResponse(chatmsg)
            _chatBotState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply{
                        add(0, chat)
                    }
                )
            }
        }
    }

    //Get Response with an image from user
    private fun getResponseWithImage(chatmsg: String, chatImage: Bitmap) {
        viewModelScope.launch {
            val chat = ChatBotData.getResponseWithImage(chatmsg, chatImage)
            _chatBotState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }
}
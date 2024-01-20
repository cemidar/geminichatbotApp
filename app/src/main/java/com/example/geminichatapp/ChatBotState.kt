package com.example.geminichatapp

import android.graphics.Bitmap
import com.example.geminichatapp.data.ChatBot

data class ChatBotState(
    val chatList: MutableList<ChatBot> = mutableListOf(),
    val chatmsg: String = "",
    val chatImage: Bitmap? = null
)

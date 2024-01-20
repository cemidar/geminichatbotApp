package com.example.geminichatapp

import android.graphics.Bitmap

sealed class ChatBotUiEvent {
    data class updateChatMsg(val  newChatMsg: String): ChatBotUiEvent()
    data class sendChatMsg(val  ChatMsg: String, val ChatImage: Bitmap?): ChatBotUiEvent()
}
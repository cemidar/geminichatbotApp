package com.example.geminichatapp.data

import android.graphics.Bitmap

data class ChatBot(
    val chatmsg: String,
    val chatImage: Bitmap?,
    val isFromUser: Boolean
    )

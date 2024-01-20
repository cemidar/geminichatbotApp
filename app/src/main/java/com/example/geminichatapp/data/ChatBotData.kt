package com.example.geminichatapp.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatBotData {
    //ik ik
    val api_key = "AIzaSyCUza5mj5aFHwEiVCt9_eYY8Wd9X6W5ybk";

    //
    suspend fun getResponse(prompt: String): ChatBot {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = api_key
        )

        try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

            return ChatBot(
                chatmsg = response.text ?: "error from response",
                chatImage = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return ChatBot(
                chatmsg = e.message ?: "catched: error from response",
                chatImage = null,
                isFromUser = false
            )
        }
    }

    suspend fun getResponseWithImage(chatmsg: String, chatImage: Bitmap): ChatBot {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision", apiKey = api_key
        )

        try {
            val inputContent = content {
                image(chatImage)
                text(chatmsg)
            }

            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(inputContent)
            }

            return ChatBot(
                chatmsg = response.text ?: "error from response with img",
                chatImage = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return ChatBot(
                chatmsg = e.message ?: "catched: error from response with img",
                chatImage = null,
                isFromUser = false
            )
        }

    }


}
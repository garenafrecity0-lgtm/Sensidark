package com.example.data.ai

import java.util.UUID

enum class MessageSender {
    USER,
    ANOS_BOT
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

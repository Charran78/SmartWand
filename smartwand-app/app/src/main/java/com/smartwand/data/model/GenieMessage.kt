package com.smartwand.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Chat message entity.
 * 
 * Stores conversation history with Crutch Genie AI.
 */
@Entity(tableName = "genie_messages")
data class GenieMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /** Message content */
    val content: String,
    
    /** True if message is from user, false if from Genie */
    val isUser: Boolean,
    
    /** Timestamp of the message */
    val timestamp: Long = System.currentTimeMillis(),
    
    /** Conversation session ID (to group related messages) */
    val sessionId: String = ""
)

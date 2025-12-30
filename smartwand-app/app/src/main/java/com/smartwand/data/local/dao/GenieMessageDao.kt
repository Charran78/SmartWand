package com.smartwand.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smartwand.data.model.GenieMessage
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Genie chat messages.
 */
@Dao
interface GenieMessageDao {
    
    @Query("SELECT * FROM genie_messages WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getMessagesBySession(sessionId: String): Flow<List<GenieMessage>>
    
    @Query("SELECT * FROM genie_messages ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentMessages(limit: Int): Flow<List<GenieMessage>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: GenieMessage): Long
    
    @Query("DELETE FROM genie_messages WHERE sessionId = :sessionId")
    suspend fun deleteSession(sessionId: String)
    
    @Query("DELETE FROM genie_messages")
    suspend fun deleteAllMessages()
    
    @Query("SELECT DISTINCT sessionId FROM genie_messages ORDER BY timestamp DESC")
    fun getAllSessionIds(): Flow<List<String>>
}

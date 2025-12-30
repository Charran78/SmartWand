package com.smartwand.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smartwand.data.model.FallEvent
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Fall Events.
 */
@Dao
interface FallEventDao {
    
    @Query("SELECT * FROM fall_events ORDER BY timestamp DESC")
    fun getAllEvents(): Flow<List<FallEvent>>
    
    @Query("SELECT * FROM fall_events ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentEvents(limit: Int): Flow<List<FallEvent>>
    
    @Query("SELECT * FROM fall_events WHERE confirmedReal = 1 ORDER BY timestamp DESC")
    fun getConfirmedFalls(): Flow<List<FallEvent>>
    
    @Query("SELECT * FROM fall_events WHERE id = :id")
    suspend fun getEventById(id: Int): FallEvent?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: FallEvent): Long
    
    @Update
    suspend fun updateEvent(event: FallEvent)
    
    @Query("UPDATE fall_events SET confirmedReal = :confirmed WHERE id = :id")
    suspend fun confirmFall(id: Int, confirmed: Boolean)
    
    @Query("UPDATE fall_events SET notes = :notes WHERE id = :id")
    suspend fun addNotes(id: Int, notes: String)
    
    @Query("DELETE FROM fall_events WHERE id = :id")
    suspend fun deleteEventById(id: Int)
    
    @Query("SELECT COUNT(*) FROM fall_events WHERE confirmedReal = 1")
    suspend fun getConfirmedFallCount(): Int
    
    @Query("SELECT COUNT(*) FROM fall_events WHERE confirmedReal = 1 AND timestamp > :since")
    suspend fun getConfirmedFallCountSince(since: Long): Int
}

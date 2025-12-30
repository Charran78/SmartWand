package com.smartwand.data.repository

import com.smartwand.data.local.dao.FallEventDao
import com.smartwand.data.model.FallEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for Fall Events.
 * 
 * Manages fall detection history and related data.
 */
@Singleton
class FallEventRepository @Inject constructor(
    private val fallEventDao: FallEventDao
) {
    
    /** Get all fall events */
    fun getAllEvents(): Flow<List<FallEvent>> {
        return fallEventDao.getAllEvents()
    }
    
    /** Get most recent fall events */
    fun getRecentEvents(limit: Int = 10): Flow<List<FallEvent>> {
        return fallEventDao.getRecentEvents(limit)
    }
    
    /** Get events confirmed as real falls */
    fun getConfirmedFalls(): Flow<List<FallEvent>> {
        return fallEventDao.getConfirmedFalls()
    }
    
    /** Get a specific event by ID */
    suspend fun getEventById(id: Int): FallEvent? {
        return fallEventDao.getEventById(id)
    }
    
    /** Record a new fall event */
    suspend fun recordFallEvent(event: FallEvent): Long {
        return fallEventDao.insertEvent(event)
    }
    
    /** Mark a fall as confirmed real or false positive */
    suspend fun confirmFall(eventId: Int, wasRealFall: Boolean) {
        fallEventDao.confirmFall(eventId, wasRealFall)
    }
    
    /** Add notes to a fall event */
    suspend fun addNotes(eventId: Int, notes: String) {
        fallEventDao.addNotes(eventId, notes)
    }
    
    /** Get count of confirmed falls */
    suspend fun getConfirmedFallCount(): Int {
        return fallEventDao.getConfirmedFallCount()
    }
    
    /** Get count of falls in the last N days */
    suspend fun getFallCountInLastDays(days: Int): Int {
        val since = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L)
        return fallEventDao.getConfirmedFallCountSince(since)
    }
}

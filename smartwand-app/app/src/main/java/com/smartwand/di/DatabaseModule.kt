package com.smartwand.di

import android.content.Context
import androidx.room.Room
import com.smartwand.data.local.SmartWandDatabase
import com.smartwand.data.local.dao.EmergencyContactDao
import com.smartwand.data.local.dao.FallEventDao
import com.smartwand.data.local.dao.GenieMessageDao
import com.smartwand.data.local.dao.IoTDeviceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for database dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): SmartWandDatabase {
        return Room.databaseBuilder(
            context,
            SmartWandDatabase::class.java,
            SmartWandDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    fun provideEmergencyContactDao(database: SmartWandDatabase): EmergencyContactDao {
        return database.emergencyContactDao()
    }
    
    @Provides
    fun provideIoTDeviceDao(database: SmartWandDatabase): IoTDeviceDao {
        return database.iotDeviceDao()
    }
    
    @Provides
    fun provideFallEventDao(database: SmartWandDatabase): FallEventDao {
        return database.fallEventDao()
    }
    
    @Provides
    fun provideGenieMessageDao(database: SmartWandDatabase): GenieMessageDao {
        return database.genieMessageDao()
    }
}

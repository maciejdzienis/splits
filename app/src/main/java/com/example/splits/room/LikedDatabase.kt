package com.example.splits.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LikedItem::class], version = 2, exportSchema = false)
abstract class LikedDatabase : RoomDatabase() {
    abstract fun likedDao(): LikedDao
}
package com.example.splits.room

import androidx.room.*

@Dao
interface LikedIntervalDao {

    @Query("SELECT * FROM intervals_table")
    fun getAll(): List<LikedInterval>

    @Query("DELETE FROM intervals_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LikedInterval)

    @Delete
    fun delete(item: LikedInterval)
}
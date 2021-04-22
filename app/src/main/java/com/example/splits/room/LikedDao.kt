package com.example.splits.room

import androidx.room.*

@Dao
interface LikedDao {
    @Query("SELECT * FROM items_table")
    fun getAll(): List<LikedItem>

    @Query("DELETE FROM items_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LikedItem)

    @Delete
    fun delete(item: LikedItem)
}
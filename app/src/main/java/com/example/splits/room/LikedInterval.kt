package com.example.splits.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "intervals_table", indices = [Index(
    value = ["interval_value"],
    unique = true
)])
data class LikedInterval(
    @ColumnInfo(name = "interval_value") var intervalValue: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
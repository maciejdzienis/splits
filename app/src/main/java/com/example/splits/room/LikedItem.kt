package com.example.splits.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "items_table", indices = [Index(
    value = ["delay_value", "split_value"],
    unique = true
)])
data class LikedItem(
    @ColumnInfo(name = "delay_value") var delayValue: String,
    @ColumnInfo(name = "split_value") var splitValue: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
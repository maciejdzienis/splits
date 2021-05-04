package com.example.splits.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class LikedItemDaoTest {

    private lateinit var db: LikedDatabase
    private lateinit var  dao: LikedDao

    @Before
    fun instantiateDB() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LikedDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.likedItemDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun insertLikedItem() {
        val likedItem = LikedItem("5", "1.5")
        dao.insert(likedItem)

        val likedItems = dao.getAll()

        assertThat(likedItems).contains(likedItem)
    }

    @Test
    fun deleteAllLikedItems() {
        val likedItem = LikedItem("5", "1.5")
        dao.insert(likedItem)
        dao.deleteAll()

        val likedItems = dao.getAll()

        assertThat(likedItems).doesNotContain(likedItem)
    }
}
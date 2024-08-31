package com.yeyint.movie_collection.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.SearchHistoryModel

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchKey: SearchHistoryModel)

    @Query("Select * From searchHistoryTable")
    suspend fun getAll() : List<SearchHistoryModel>
}
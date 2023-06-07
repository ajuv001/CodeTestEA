package com.test.musicfestival.data.repository

import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.util.Resource
import kotlinx.coroutines.flow.Flow

interface FestivalRepoSource {
    suspend fun getFestivals(): Flow<Resource<List<RecordLabel>>>

}
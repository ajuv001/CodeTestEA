package com.test.musicfestival.data.remote

import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.util.Resource


interface FestivalDataSource {
    suspend fun getFestivals(): Resource<List<RecordLabel>>
}

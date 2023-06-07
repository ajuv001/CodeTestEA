package com.test.musicfestival.data.repository

import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.data.remote.FestivalsDataImpl
import com.test.musicfestival.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FestivalRepository @Inject constructor(private val remoteDataSource: FestivalsDataImpl, private val dispatcher: CoroutineContext): FestivalRepoSource {
    override suspend fun getFestivals(): Flow<Resource<List<RecordLabel>>> {
        return flow {
            emit(remoteDataSource.getFestivals())
        }.flowOn(dispatcher)
    }
}
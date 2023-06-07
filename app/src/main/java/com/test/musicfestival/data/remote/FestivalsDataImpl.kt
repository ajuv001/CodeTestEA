package com.test.musicfestival.data.remote

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.test.musicfestival.data.model.Festival
import com.test.musicfestival.data.model.FestivalsResponse
import com.test.musicfestival.data.model.MusicBand
import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.data.remote.webservice.WebService
import com.test.musicfestival.util.ErrorType
import com.test.musicfestival.util.NetworkUtil
import com.test.musicfestival.util.Resource
import java.net.SocketTimeoutException
import javax.inject.Inject

class FestivalsDataImpl @Inject constructor(
    private val webService: WebService,
    private val networkUtil: NetworkUtil
): FestivalDataSource {
    override suspend fun getFestivals(): Resource<List<RecordLabel>>{
            try {
                if(networkUtil.isConnected()) {
                    val task = webService.getFestivals()

                    if (task.isSuccessful) {
                        task.body()?.let {
                            if (it.isEmpty() || it.startsWith("\"\"")) {
                                return Resource.Error(errorType = ErrorType.EMPTY_DATA)
                            } else {
                                val responseData =
                                    Gson().fromJson(it, FestivalsResponse::class.java)
                                return Resource.Success(data = formatData(responseData))
                            }
                        } ?: return Resource.Error(errorType = ErrorType.EMPTY_DATA)
                    } else {
                        return if (task.code() == 429)
                            Resource.Error(errorType = ErrorType.THROTTLING)
                        else
                            Resource.Error()
                    }
                }else{
                    return Resource.Error(errorType = ErrorType.NO_INTERNET)
                }
            } catch (e: SocketTimeoutException) {
                return Resource.Error(errorType = ErrorType.TIME_OUT)
            } catch (e: Exception) {
                return Resource.Error(message = e.localizedMessage)
            }
    }

    fun formatData(data: FestivalsResponse): List<RecordLabel>{
        val formattedData: MutableList<RecordLabel> = mutableListOf()
        data.forEach { festival ->
            festival.bands.forEach { band ->
                if(!formattedData.any { it.recordLabel == band.recordLabel }){
                    val label = band.recordLabel?.let { RecordLabel(it, mutableListOf(MusicBand(band.name, mutableListOf(festival.name?.let { Festival(it)})))) }
                    if (label != null) {
                        formattedData.add(label)
                        formattedData.sortWith(compareBy { it.recordLabel })
                    }
                }else{
                    if(formattedData.any { it.musicBands?.any { musicBand -> musicBand.name == band.name } == true }){
                        formattedData.first { it.recordLabel == band.recordLabel }
                            .musicBands?.firstOrNull{ musicBand-> musicBand.name == band.name}
                            ?.festivals?.add(festival.name?.let {  Festival(festival.name) })
                        formattedData.first { it.recordLabel == band.recordLabel }
                            .musicBands?.firstOrNull{ musicBand-> musicBand.name == band.name}
                            ?.festivals?.sortWith(compareBy { it?.name })
                    }else{
                        formattedData.first { it.recordLabel == band.recordLabel }.musicBands?.add(MusicBand(band.name, mutableListOf(festival.name?.let {  Festival(festival.name)})))
                        formattedData.first { it.recordLabel == band.recordLabel }.musicBands?.sortWith(compareBy { it.name })
                    }
                }
            }
        }
        Log.i("Transformed data ", Gson().toJson(formattedData))
        return formattedData
    }
}

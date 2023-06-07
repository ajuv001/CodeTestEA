package com.test.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.musicfestival.data.model.FestivalsResponse
import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.util.ErrorType
import com.test.musicfestival.util.Resource

class TestDataGenerator {

    private val rawJsonResponse = "[{\"name\":\"Trainerella\",\"bands\":[{\"name\":\"YOUKRANE\",\"recordLabel\":\"Anti Records\"},{\"name\":\"Adrian Venti\",\"recordLabel\":\"Monocracy Records\"},{\"name\":\"Manish Ditch\",\"recordLabel\":\"ACR\"},{\"name\":\"Wild Antelope\",\"recordLabel\":\"Still Bottom Records\"}]},{\"bands\":[{\"name\":\"Propeller\",\"recordLabel\":\"Pacific Records\"},{\"name\":\"Critter Girls\",\"recordLabel\":\"ACR\"}]},{\"name\":\"Small Night In\",\"bands\":[{\"name\":\"Yanke East\",\"recordLabel\":\"MEDIOCRE Music\"},{\"name\":\"Green Mild Cold Capsicum\",\"recordLabel\":\"Marner Sis. Recording\"},{\"name\":\"Wild Antelope\",\"recordLabel\":\"Marner Sis. Recording\"},{\"name\":\"Squint-281\",\"recordLabel\":\"Outerscope\"},{\"name\":\"The Black Dashes\",\"recordLabel\":\"Fourth Woman Records\"}]}]"
    private val transformedJsonData = "[{\"recordLabel\":\"ACR\",\"musicBands\":[{\"name\":\"Critter Girls\",\"festivals\":[null]},{\"name\":\"Manish Ditch\",\"festivals\":[{\"name\":\"Trainerella\"}]}]},{\"recordLabel\":\"Anti Records\",\"musicBands\":[{\"name\":\"YOUKRANE\",\"festivals\":[{\"name\":\"Trainerella\"}]}]},{\"recordLabel\":\"Fourth Woman Records\",\"musicBands\":[{\"name\":\"The Black Dashes\",\"festivals\":[{\"name\":\"Small Night In\"}]}]},{\"recordLabel\":\"MEDIOCRE Music\",\"musicBands\":[{\"name\":\"Yanke East\",\"festivals\":[{\"name\":\"Small Night In\"}]}]},{\"recordLabel\":\"Marner Sis. Recording\",\"musicBands\":[{\"name\":\"Green Mild Cold Capsicum\",\"festivals\":[{\"name\":\"Small Night In\"}]}]},{\"recordLabel\":\"Monocracy Records\",\"musicBands\":[{\"name\":\"Adrian Venti\",\"festivals\":[{\"name\":\"Trainerella\"}]}]},{\"recordLabel\":\"Outerscope\",\"musicBands\":[{\"name\":\"Squint-281\",\"festivals\":[{\"name\":\"Small Night In\"}]}]},{\"recordLabel\":\"Pacific Records\",\"musicBands\":[{\"name\":\"Propeller\",\"festivals\":[null]}]},{\"recordLabel\":\"Still Bottom Records\",\"musicBands\":[{\"name\":\"Wild Antelope\",\"festivals\":[{\"name\":\"Trainerella\"}]}]}]"

    private var festivals: List<RecordLabel>
    private var rawFestivals: FestivalsResponse
    init {
        val typeToken = object : TypeToken<List<RecordLabel>>() {}.type
        festivals = Gson().fromJson(transformedJsonData, typeToken)
        rawFestivals = Gson().fromJson(rawJsonResponse, FestivalsResponse::class.java)
    }

    fun generateFestivals(): List<RecordLabel>{
        return festivals
    }

    fun generateEmptyFestivalList(): List<RecordLabel>{
        return emptyList()
    }
    fun generateEmptyDataError(): Resource<List<RecordLabel>> {
        return Resource.Error(ErrorType.EMPTY_DATA)
    }

    fun generateThrottlingError(): Resource<List<RecordLabel>> {
        return Resource.Error(ErrorType.THROTTLING)
    }

    fun generateNoInternetError(): Resource<List<RecordLabel>> {
        return Resource.Error(ErrorType.NO_INTERNET)
    }

    fun generateFestivalApiResponse(): FestivalsResponse {
        return rawFestivals
    }
}
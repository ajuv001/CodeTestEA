package com.test.musicfestival.data.model

data class RecordLabel(
    val recordLabel: String,
    var musicBands: MutableList<MusicBand>? = null
)

data class MusicBand(
    val name: String,
    val festivals: MutableList<Festival?>?= null
)

data class Festival(
    val name: String
)
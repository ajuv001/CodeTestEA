package com.test.musicfestival.data.model

class FestivalsResponse : ArrayList<MusicFestival>()

data class MusicFestival(
    val bands: List<Band>,
    val name: String?
)

data class Band(
    val name: String,
    val recordLabel: String?
)
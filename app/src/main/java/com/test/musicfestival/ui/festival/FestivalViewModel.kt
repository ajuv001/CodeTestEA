package com.test.musicfestival.ui.festival

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.data.repository.FestivalRepository
import com.test.musicfestival.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val festivalsRepo: FestivalRepository
) : ViewModel() {
    val festivalData : MutableLiveData<Resource<List<RecordLabel>>> = MutableLiveData()

    fun getFestivalData() {
        festivalData.postValue(Resource.Loading())
        viewModelScope.launch {
            festivalsRepo.getFestivals().collect{
                festivalData.postValue(it)
            }
        }
    }
}

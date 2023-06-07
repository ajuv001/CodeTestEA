package com.test.musicfestival

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.data.remote.FestivalsDataImpl
import com.test.musicfestival.data.repository.FestivalRepository
import com.test.musicfestival.ui.festival.FestivalViewModel
import com.test.musicfestival.util.NetworkUtil
import com.test.musicfestival.util.Resource
import com.test.utils.TestDataGenerator
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FestivalViewModelTest {

    lateinit var festivalViewModel: FestivalViewModel

    lateinit var testDataGenerator: TestDataGenerator

    var dispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var repository: FestivalRepository

    @Mock
    lateinit var festivalsDataImpl: FestivalsDataImpl

    @Mock
    lateinit var networkUtil: NetworkUtil

    @Mock
    private lateinit var observer: Observer<Any>

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        testDataGenerator = TestDataGenerator()
        festivalViewModel = FestivalViewModel(repository)
    }

    @Test
    fun getFestivals(){

        runBlocking {
            val testData = testDataGenerator.generateFestivals()
            val flow = flow {
                emit(Resource.Loading())
                emit(Resource.Success(testData))
            }
            Mockito.`when`(repository.getFestivals())
                .thenReturn(flow)


            festivalViewModel.getFestivalData()
            festivalViewModel.festivalData.observeForever(observer)

            val isEmptyList = festivalViewModel.festivalData.value?.data.isNullOrEmpty()
            assertEquals(testData, festivalViewModel.festivalData.value?.data)
            assertEquals(false,isEmptyList)
        }
    }

    @Test
    fun emptyFestivalList(){

        runBlocking {
            val testData = testDataGenerator.generateEmptyFestivalList()
            val flow = flow {
                emit(Resource.Loading())
                emit(Resource.Success(emptyList<RecordLabel>()))
            }

            Mockito.`when`(repository.getFestivals())
                .thenReturn(flow)


            festivalViewModel.getFestivalData()

            val isEmptyList = festivalViewModel.festivalData.value?.data.isNullOrEmpty()
            assertEquals(testData, festivalViewModel.festivalData.value?.data)
            assertEquals(true,isEmptyList)
        }
    }

    @Test
    fun `when empty string response instead of json`(){

        runBlocking {
            val testData = testDataGenerator.generateEmptyDataError()
            val flow = flow {
                emit(testData)
            }

            Mockito.`when`(repository.getFestivals())
                .thenReturn(flow)

            festivalViewModel.getFestivalData()

            when(festivalViewModel.festivalData.value){
                is Resource.Error ->{
                    assertEquals(testData.message, festivalViewModel.festivalData.value?.message)
                }
            }

        }
    }

    @Test
    fun `when throttling error`(){

        runBlocking {
            val testData = testDataGenerator.generateThrottlingError()
            val flow = flow {
                emit(testData)
            }

            Mockito.`when`(repository.getFestivals())
                .thenReturn(flow)


            festivalViewModel.getFestivalData()

            when(festivalViewModel.festivalData.value){
                is Resource.Error ->{
                    assertEquals(testData.message, festivalViewModel.festivalData.value?.message)
                }
            }

        }
    }

    @Test
    fun `when no internet`(){

        runBlocking {
            val testData = testDataGenerator.generateNoInternetError()
            val flow = flow {
                emit(testData)
            }

            Mockito.`when`(repository.getFestivals())
                .thenReturn(flow)


            festivalViewModel.getFestivalData()

            when(festivalViewModel.festivalData.value){
                is Resource.Error ->{
                    assertEquals(testData.message, festivalViewModel.festivalData.value?.message)
                }
            }

        }
    }

    @Test
    fun transformDataTest(){
        val testInput = testDataGenerator.generateFestivalApiResponse()
        val testOutput = testDataGenerator.generateFestivals()

        Mockito.`when`(festivalsDataImpl.formatData(testInput)).thenReturn(testOutput)

        assertEquals(testOutput, festivalsDataImpl.formatData(testInput))
    }

    @Test
    fun `when no network connection`(){
        Mockito.`when`(networkUtil.isConnected()).thenReturn(false)

        assertFalse(networkUtil.isConnected())
    }

    @Test
    fun `when network connected`(){
        Mockito.`when`(networkUtil.isConnected()).thenReturn(true)

        assertTrue(networkUtil.isConnected())
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}
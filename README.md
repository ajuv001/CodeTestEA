# Overview

This Android project is developed using Kotlin language. It is developed using MVVM architecture. 

The app displays music festival data in following manner. At the top most level recordlabel will be displayed, then below that list of bands associated to the recordlabel and the list of festivals under each brand.

      
 # Libraries used
 
 Following Jetpack components used in this project ViewModel, LiveData, DataBinding
 
 Retrofit - for consuming API
 Coroutines - to call the API 
 Mockito amd Junit - for unit testing
 Hilt - dependency injection
 
 # ViewModels
 
 FestivalViewModel - Call the API to load the data provides it back to UI through LiveData
 
 # Repository
 
 FestivalRepository and FestivalDataImpl classes responsible for calling the API and transform the data to the particular manner.
 
 # Models
 
 FestivalResponse to hold the API response
 RecordLabel to hold tranformed data
 
 # Unit Test
 
 FestivalViewModelTest 
 TestDataGenerator - to generate test data
 
 # Other
 
 NetworkUtil - to check the internet connecivity
 Resource - state manager
 
 # Assumptions
 
 1. If any of the value is empty or field itself is missing in the API response, an empty row will be shown in the App UI
 2. A band is associated to a single record label
 

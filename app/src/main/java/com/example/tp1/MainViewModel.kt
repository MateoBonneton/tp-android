package com.example.tp1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    val movies = MutableStateFlow<List<Movie>>(listOf())
    val series = MutableStateFlow<List<Serie>>(listOf())
    val persons = MutableStateFlow<List<Person>>(listOf())

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    val api = retrofit.create(TmbdApi::class.java)

    fun getMovies() {
        viewModelScope.launch {
            movies.value = api.lastMovie(api_key = "793fd013f280118c3d4deff4ce476e4d").results
        }
    }

    fun getSeries() {
        viewModelScope.launch {
            series.value = api.lastSerie(api_key = "793fd013f280118c3d4deff4ce476e4d").results
        }
    }
    fun getPersons() {
        viewModelScope.launch {
            persons.value = api.lastPerson(api_key = "793fd013f280118c3d4deff4ce476e4d").results
        }
    }

    fun fetchPlayList(): Playlist {
        val
    }

}
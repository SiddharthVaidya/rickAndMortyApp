package com.example.rickandmortyapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.models.Result
import com.example.rickandmortyapp.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(private val repository: CharacterRepository): ViewModel() {


    init {
        viewModelScope.launch(Dispatchers.IO) {
                repository.getCharacters()


        }
    }
    val characters : LiveData<List<Result>>
    get() = repository.characters
}
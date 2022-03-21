package com.example.rickandmortyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmortyapp.api.ApiInterface
import com.example.rickandmortyapp.models.Result

class CharacterRepository(private val charService: ApiInterface) {

    private val characterLiveData = MutableLiveData<List<Result>>()
    val characters: LiveData<List<Result>>
        get() = characterLiveData

    suspend fun getCharacters() {
        val result = charService.getData()
        if (result.isSuccessful) {
            val items = result.body()
            if (items!=null){
                for(i in 1 until 826)
                characterLiveData.postValue(items!!)
            }
        }
    }
}
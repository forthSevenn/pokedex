package com.firstapp.pokedex.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firstapp.pokedex.api.ApiConfig
import com.firstapp.pokedex.model.pokemon.Pokemon
import com.firstapp.pokedex.model.pokemon.PokemonResponse
import com.firstapp.pokedex.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel() {
    private val pokemonRepo : PokemonRepository = PokemonRepository(application)
    val pokemonList = MutableLiveData<ArrayList<Pokemon>>()
    private var offset = 0
    private var limit = 20

    fun showPokemon (){
        ApiConfig.getApiService()
            .getPokemon(offset,limit)
            .enqueue(object : Callback<PokemonResponse>{
                override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
                ) {
                    if (response.isSuccessful){
                        pokemonList.postValue(response.body()?.results)
                    }
                }

                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun insertPokemonToDB(pokemon : ArrayList<Pokemon>){
        CoroutineScope(Dispatchers.IO).launch {
            pokemonRepo.insert(pokemon)
        }
    }

    fun getMorePokemon(){
        offset+=20
        showPokemon()
    }

    fun getPokemonOrdered(isAsc:Boolean) : LiveData<List<Pokemon>> = pokemonRepo.getOrderedPokemon(isAsc)

    fun searchPokemon(name : String) : LiveData<List<Pokemon>> = pokemonRepo.get(name)

    fun getAll(): LiveData<List<Pokemon>> = pokemonRepo.getAll()

    fun getPokemonList(): LiveData<ArrayList<Pokemon>>{
        return pokemonList
    }
}
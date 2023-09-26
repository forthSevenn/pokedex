package com.firstapp.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firstapp.pokedex.api.ApiConfig
import com.firstapp.pokedex.model.pokemondetail.abilities.Ability
import com.firstapp.pokedex.model.pokemondetail.PokemonDetailResponse
import com.firstapp.pokedex.model.pokemondetail.Sprite
import com.firstapp.pokedex.model.pokemondetail.abilities.Abilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel(){
    var sprites = MutableLiveData<Sprite>()
    var abilities = MutableLiveData<ArrayList<Abilities>>()

    fun getDetail(name:String){
        ApiConfig.getApiService()
            .getPokemonDetail(name)
            .enqueue(object : Callback<PokemonDetailResponse>{
                override fun onResponse(
                    call: Call<PokemonDetailResponse>,
                    response: Response<PokemonDetailResponse>,
                ) {
                    if (response.isSuccessful) {
                        sprites.postValue(response.body()?.sprites)
                        abilities.postValue(response.body()?.abilities)
                        Log.d("TestingViewModel",response.body()?.abilities.toString())
                    }
                }

                override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getSprites() : LiveData<Sprite> {
        return sprites
    }

    fun getAbilities() : LiveData<ArrayList<Abilities>> {
        return abilities
    }
}
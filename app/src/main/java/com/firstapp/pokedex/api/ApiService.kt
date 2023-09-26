package com.firstapp.pokedex.api

import com.firstapp.pokedex.model.pokemon.PokemonResponse
import com.firstapp.pokedex.model.pokemondetail.PokemonDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    fun getPokemon(
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ): Call<PokemonResponse>

    @GET("pokemon/{pokemonName}")
    fun getPokemonDetail(
        @Path("pokemonName") PokemonName : String
    ): Call<PokemonDetailResponse>
}
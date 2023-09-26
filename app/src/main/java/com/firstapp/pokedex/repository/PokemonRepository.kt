package com.firstapp.pokedex.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.firstapp.pokedex.database.PokemonDao
import com.firstapp.pokedex.database.PokemonDatabase
import com.firstapp.pokedex.model.pokemon.Pokemon
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PokemonRepository (application: Application) {
    private val pokeDao: PokemonDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = PokemonDatabase.getDatabase(application)
        pokeDao = database.pokemonDao()
    }

    fun insert (pokemon: ArrayList<Pokemon>) {
        executorService.execute { pokeDao.insertPokemon(pokemon) }
    }

    fun get (name : String) : LiveData<List<Pokemon>> = pokeDao.searchPokemon(name)

    fun getAll() : LiveData<List<Pokemon>> = pokeDao.getAllPokemon()

    fun getOrderedPokemon(isAsc:Boolean) : LiveData<List<Pokemon>> = pokeDao.getOrderedPokemon(isAsc)

}
package com.firstapp.pokedex.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firstapp.pokedex.model.pokemon.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPokemon(pokemon: ArrayList<Pokemon>)

    @Query("Select distinct id,name From pokemon where name like '%' || :name || '%'")
    fun searchPokemon(name: String): LiveData<List<Pokemon>>

    @Query("Select distinct id,name from pokemon")
    fun getAllPokemon(): LiveData<List<Pokemon>>

    @Query("Select distinct id,name from pokemon order by " +
            "Case when :isAsc = 1 then name end asc," +
            "Case when :isAsc = 0 then name end desc")
    fun getOrderedPokemon(isAsc : Boolean?): LiveData<List<Pokemon>>
}
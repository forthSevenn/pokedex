package com.firstapp.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.firstapp.pokedex.model.pokemon.Pokemon

@Database(
    entities = [Pokemon::class],
    version = 1,
    exportSchema= false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao() : PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java, "pokemon_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
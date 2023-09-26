package com.firstapp.pokedex.model.pokemondetail

import com.firstapp.pokedex.model.pokemondetail.abilities.Abilities

data class PokemonDetailResponse (
    val sprites: Sprite,
    val abilities : ArrayList<Abilities>
)

package com.firstapp.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.pokedex.databinding.PokemonListBinding
import com.firstapp.pokedex.model.pokemon.Pokemon

class RvAdapter : RecyclerView.Adapter<RvAdapter.ListViewHolder>() {

    private val listOfPokemon = ArrayList<Pokemon>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setListOfPokemon (listPokemon: ArrayList<Pokemon>?){
        listOfPokemon?.clear()
        if (listPokemon != null) {
            listOfPokemon.addAll(listPokemon)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(val binding: PokemonListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon: Pokemon){
            binding.apply {
                pokemonName.text = pokemon.name?.replaceFirstChar { it.uppercase() }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = PokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listOfPokemon[position])
        holder.binding.pokemonDetail.setOnClickListener { onItemClickCallback?.onItemClicked(listOfPokemon[position]) }
    }

    override fun getItemCount(): Int =listOfPokemon.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Pokemon)
    }


}
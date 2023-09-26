package com.firstapp.pokedex.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.pokedex.databinding.AbilitiesListBinding
import com.firstapp.pokedex.model.pokemondetail.abilities.Abilities

class RvDetailAdapter : RecyclerView.Adapter<RvDetailAdapter.ListViewHolder>(){

    private val listOfAbility = ArrayList<Abilities>()

    fun setListOfAbility (listAbility : ArrayList<Abilities>?){
        listOfAbility.clear()
        if (listAbility!=null){
            listOfAbility.addAll(listAbility)
        }
        notifyDataSetChanged()

    }

    inner class ListViewHolder (val binding: AbilitiesListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(abilities: Abilities){
            binding.apply {
                abilityName.text = abilities.ability.name?.replaceFirstChar { it.uppercase() }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = AbilitiesListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listOfAbility[position])
    }

    override fun getItemCount(): Int = listOfAbility.size

}
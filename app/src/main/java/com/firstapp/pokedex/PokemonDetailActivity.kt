package com.firstapp.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firstapp.pokedex.adapter.RvAdapter
import com.firstapp.pokedex.adapter.RvDetailAdapter
import com.firstapp.pokedex.databinding.ActivityPokemonDetailBinding
import com.firstapp.pokedex.viewmodel.DetailViewModel

class PokemonDetailActivity : AppCompatActivity() {

    companion object {
        const val POKEMON_NAME = "pokemon_name"
    }

    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var rvDetailAdapter: RvDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvDetailAdapter = RvDetailAdapter()
        rvDetailAdapter.notifyDataSetChanged()

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val pokemonName = intent.getStringExtra(POKEMON_NAME)

        binding.apply {
            rvAbility.layoutManager = LinearLayoutManager(this@PokemonDetailActivity)
            rvAbility.setHasFixedSize(true)
            rvAbility.adapter = rvDetailAdapter

            pokemonDetailName.text = pokemonName?.replaceFirstChar { it.uppercase() }
            detailViewModel.getDetail(pokemonName.toString())
        }

        detailViewModel.getAbilities().observe(this){
            rvDetailAdapter.setListOfAbility(it)
        }

        detailViewModel.getSprites().observe(this){
            binding.apply {
                Glide.with(this@PokemonDetailActivity)
                    .load(it.front_default)
                    .centerCrop()
                    .into(pokemonSprite)
            }
        }
    }
}
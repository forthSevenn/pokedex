package com.firstapp.pokedex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.pokedex.adapter.RvAdapter
import com.firstapp.pokedex.databinding.ActivityMainBinding
import com.firstapp.pokedex.model.pokemon.Pokemon
import com.firstapp.pokedex.viewmodel.MainViewModel
import com.firstapp.pokedex.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var rvPokemonAdapter : RvAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvPokemonAdapter = RvAdapter()
        rvPokemonAdapter.notifyDataSetChanged()

        val factory = ViewModelFactory.getInstance(application)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        binding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mainViewModel.getMorePokemon()
                }
            }
        })

        binding.apply {
            rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
            rvPokemon.adapter = rvPokemonAdapter

            rvPokemon.addItemDecoration(
                DividerItemDecoration(
                    rvPokemon.context,
                    DividerItemDecoration.VERTICAL
                )
            )

            mainViewModel.showPokemon()

        }

        mainViewModel.getPokemonList().observe(this){
            mainViewModel.insertPokemonToDB(it)
        }

        mainViewModel.getAll().observe(this){
            if (it!=null) {
                fetchPokemon(it)
            }
        }

        binding.searchBtn.setOnClickListener {
            mainViewModel.searchPokemon(binding.searchEdt.text.toString()).observe(this){
                if (it!=null) {
                    fetchPokemon(it)
                }
            }
        }

        binding.ascendingBtn.setOnClickListener{
            mainViewModel.getPokemonOrdered(true).observe(this){
                fetchPokemon(it)
            }
        }

        binding.descendingBtn.setOnClickListener {
            mainViewModel.getPokemonOrdered(false).observe(this){
                fetchPokemon(it)
            }
        }
    }

    private fun fetchPokemon (listPokemon : List<Pokemon>) {
        val allPokemon: ArrayList<Pokemon> = bindList(listPokemon)
        rvPokemonAdapter.setListOfPokemon(allPokemon)
        rvPokemonAdapter.setOnItemClickCallback(object : RvAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Pokemon) {
                val pokemonDetailIntent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
                pokemonDetailIntent.putExtra(PokemonDetailActivity.POKEMON_NAME,data.name)
                startActivity(pokemonDetailIntent)
            }

        })
    }

    private fun bindList (searchPokemon : List<Pokemon>) : ArrayList<Pokemon>{
        val listPokemon = ArrayList<Pokemon>()
        for (pokemon in searchPokemon){
            val pokeBind = Pokemon (
                pokemon.id,
                pokemon.name
            )
            listPokemon.add(pokeBind)
        }
        return listPokemon
    }
}
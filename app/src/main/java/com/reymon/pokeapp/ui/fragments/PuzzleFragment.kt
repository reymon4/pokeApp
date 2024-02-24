package com.reymon.pokeapp.ui.fragments

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.reymon.pokeapp.R
import com.reymon.pokeapp.databinding.FragmentFavoritesBinding
import com.reymon.pokeapp.databinding.FragmentPuzzleBinding
import com.reymon.pokeapp.logic.network.entities.PokemonLG
import com.reymon.pokeapp.logic.network.poke.GetPokemonsKtor
import com.reymon.pokeapp.ui.activities.HomeActivity
import com.reymon.pokeapp.ui.viewmodels.MainViewModel
import com.reymon.pokeapp.ui.viewmodels.PokeApiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random


class PuzzleFragment : Fragment() {
    private val mainViewModel: PokeApiViewModel by viewModels()
    private lateinit var binding: FragmentPuzzleBinding
    private lateinit var poke: GetPokemonsKtor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPuzzleBinding.inflate(inflater, container, false)
        binding.txtWinner.visibility = View.GONE
        poke = GetPokemonsKtor()
        binding.animationView.visibility = View.GONE
        lifecycleScope.launch(Dispatchers.IO) {

            puzzle()

        }
        return binding.root
    }

    private suspend fun puzzle() {

        val aux = poke.invokeList(4)

        val winn: PokemonLG = aux.get(Random.nextInt(0,4))
        Log.d("win",winn.name.toString() )
        binding.imgPuzzle.load(winn.sprites?.other?.home?.front_default.toString())
        binding.btnOpt1.text = aux.get(3).name.toString()
        Log.d("1",winn.name.toString() )
        binding.btnOpt2.text = aux.get(2).name.toString()
        binding.btnOpt3.text = aux.get(1).name.toString()
        binding.btnOpt4.text = aux.get(0).name.toString()

        binding.btnOpt4.setOnClickListener {
            if (binding.btnOpt4.text.toString() == winn.name || binding.btnOpt3.text.toString() == winn.name || binding.btnOpt2.text.toString() == winn.name || binding.btnOpt1.text.toString() == winn.name) {
                binding.txtWinner.visibility = View.VISIBLE
                binding.btnOpt3.visibility = View.GONE
                binding.btnOpt2.visibility = View.GONE
                binding.btnOpt1.visibility = View.GONE
                binding.btnOpt4.visibility = View.GONE
            }
        }

    }
}




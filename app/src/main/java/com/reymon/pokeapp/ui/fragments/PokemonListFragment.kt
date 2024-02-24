package com.reymon.pokeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.reymon.pokeapp.R
import com.reymon.pokeapp.logic.network.entities.PokemonLG
import com.reymon.pokeapp.ui.adapters.PokeAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.reymon.pokeapp.databinding.FragmentPokemonListBinding
import com.reymon.pokeapp.ui.viewmodels.PokeApiViewModel
import kotlinx.coroutines.withContext


class PokemonListFragment : Fragment() {

    private lateinit var binding: FragmentPokemonListBinding
    private var listItem:MutableList<PokemonLG> = ArrayList()
    private val adapter = PokeAdapter()
    private val viewModel: PokeApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.bind(inflater.inflate(
            R.layout.fragment_pokemon_list, container, false
        ))
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager=
            LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        loadDataRecyclerView()

    }

     fun loadDataRecyclerView(){
        lifecycleScope.launch (Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE

            val resp = withContext(Dispatchers.IO){
                PokeApiViewModel().getPokemonListKtorForId()
            }
            if(resp!=null){
                viewModel.getPokemonListKtorForId()
            }else{
                Snackbar.make(
                    requireActivity(),
                    binding.rvUsers,
                    "Not load",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }

            binding.progressBar.visibility = View.GONE
        }
    }



//    private fun deleteUsersDiff(position: Int){
//        usersList.removeAt(position)
//        userDiffAdapter.submitList(usersList.toList())
//
//    }
//
//
//

}
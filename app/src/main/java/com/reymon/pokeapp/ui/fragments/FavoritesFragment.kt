package com.reymon.pokeapp.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.reymon.pokeapp.databinding.FragmentFavoritesBinding
import com.reymon.pokeapp.databinding.FragmentLogInBinding
import com.reymon.pokeapp.ui.viewmodels.MainViewModel


class FavoritesFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoritesBinding.inflate(inflater, container, false)



        return binding.root
    }

}
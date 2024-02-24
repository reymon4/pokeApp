package com.reymon.pokeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.reymon.pokeapp.R
import com.reymon.pokeapp.databinding.PokeItemsBinding
import com.reymon.pokeapp.logic.network.entities.PokemonLG


import com.bumptech.glide.Glide

class PokeAdapter(
    //private var onDeleteItem: (Int) -> Unit,
//    private var onSelectItem: (PokemonLG) -> Unit
) : ListAdapter<PokemonLG, PokeAdapter.PokeVH>(DiffUtilPokeCallback) {


    class PokeVH(view: View) : RecyclerView.ViewHolder(view) {
        private var binding: PokeItemsBinding = PokeItemsBinding.bind(view)

        fun render(
            item: PokemonLG,
            //onDeleteItem: (Int) -> Unit,
            //onSelectItem: (PokemonLG) -> Unit
        ) {

            Glide.with(binding.root)
                .asGif()
                .load(item.sprites?.other?.showdown?.front_default)
                .into(binding.pokeImg)

            binding.pokeExperience.text = "Base Experience: " + item.base_experience
            binding.pokeHeight.text = "Height: " + item.height
            binding.pokeName.text = item.name.toString().uppercase()
            binding.pokeWeight.text = "Weight: " + item.weight


        }
    }

        //OJO CON EL PRIVATE
        private object DiffUtilPokeCallback : DiffUtil.ItemCallback<PokemonLG>() {
            override fun areItemsTheSame(oldItem: PokemonLG, newItem: PokemonLG): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PokemonLG, newItem: PokemonLG): Boolean {
                return oldItem == newItem
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeVH {
            val inflater = LayoutInflater.from(parent.context)
            return PokeVH(inflater.inflate(R.layout.poke_items, parent, false))
        }

        override fun onBindViewHolder(holder: PokeVH, position: Int) {
            holder.render(getItem(position))
        }
    }

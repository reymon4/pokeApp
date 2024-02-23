package com.reymon.pokeapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.reymon.pokeapp.R
import com.reymon.pokeapp.databinding.ActivityHomeBinding
import com.reymon.pokeapp.ui.adapters.PokeAdapter
import com.reymon.pokeapp.ui.viewmodels.PokeApiViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val adapter=PokeAdapter()
    private val viewModel:PokeApiViewModel by viewModels()
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initObservers()
        initRecyclerView()

        dialog = AlertDialog.Builder(this)
            .setMessage(getString(R.string.carga_datos))
            .setTitle(getString(R.string.title_dialog))
            .setPositiveButton(getString(R.string.aceptar)) { _, _ ->
                viewModel.getPokemonKtorForId()
            }
            .setNegativeButton(getString(R.string.cancelar)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun initObservers() {

        viewModel.listItem.observe(this) {
            binding.animationView.visibility = View.VISIBLE
            adapter.submitList(it)
            binding.animationView.visibility = View.GONE
        }

        viewModel.error.observe(this) {
            adapter.submitList(emptyList())
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        binding.rvPoke.adapter = adapter
        binding.rvPoke.layoutManager =
            LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
    }

    private fun initListeners() {
        binding.swiperv.setOnRefreshListener {
            viewModel.getPokemonKtorForId()
            binding.swiperv.isRefreshing = false
        }
    }
}
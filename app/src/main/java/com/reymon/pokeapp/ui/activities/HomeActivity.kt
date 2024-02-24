package com.reymon.pokeapp.ui.activities

import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.reymon.pokeapp.R
import com.reymon.pokeapp.databinding.ActivityHomeBinding
import com.reymon.pokeapp.logic.network.entities.PokemonLG
import com.reymon.pokeapp.ui.adapters.PokeAdapter
import com.reymon.pokeapp.ui.fragments.FavoritesFragment
import com.reymon.pokeapp.ui.fragments.PokemonListFragment
import com.reymon.pokeapp.ui.fragments.PuzzleFragment
import com.reymon.pokeapp.ui.viewmodels.MainViewModel
import com.reymon.pokeapp.ui.viewmodels.PokeApiViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val adapter = PokeAdapter()
    private val viewModel: PokeApiViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgLogo.visibility = View.GONE
        binding.imgSearch.visibility = View.GONE
        binding.imgProfile.visibility = View.GONE
        binding.txtWelcome.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE
        initObservers()

        initListeners()

        initRecyclerView()

        swipeRecyclerView()
        //
        //viewModel.getPokemonListKtorForId()

    }

    private fun initObservers() {
        viewModel.getPokemonListKtorForId()
        viewModel.listItem.observe(this) {

            binding.animationView.visibility = View.VISIBLE
            adapter.submitList(it)
        binding.imgLogo.visibility = View.VISIBLE
        binding.imgSearch.visibility = View.VISIBLE
        binding.imgProfile.visibility = View.VISIBLE
        binding.txtWelcome.visibility = View.VISIBLE
        binding.bottomNavigation.visibility = View.VISIBLE
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
            viewModel.getPokemonListKtorForId()
            binding.swiperv.isRefreshing = false
        }
        binding.imgProfile.setOnClickListener{
            mainViewModel.logOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.it_fav -> {
                    binding.rvPoke.visibility=View.GONE
                    binding.txtWelcome.text="Look at your Pokémons!"
                    binding.frmContainer.visibility=View.VISIBLE
                    replaceFragment(FavoritesFragment())
                    true
                }

                R.id.it_puzzle -> {
                    binding.rvPoke.visibility=View.GONE
                    binding.txtWelcome.text="Who is this Pokémon?"
                    binding.frmContainer.visibility=View.VISIBLE
//                    binding.animationView.visibility = View.GONE
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, PuzzleFragment())
                    transaction.commit()
                    true
                }
                R.id.it_home -> {
                    binding.txtWelcome.text="Discover your favorite Pokémon!"
                    binding.rvPoke.visibility=View.VISIBLE
                    binding.frmContainer.visibility=View.GONE
//                    binding.animationView.visibility = View.GONE

                    true
                }  R.id.it_watch -> {
                //binding.rvPoke.visibility=View.VISIBLE
//                    binding.animationView.visibility = View.GONE

                true
            }
                else -> {

                    false
                }
            }
        }
    }

    private fun swipeRecyclerView() {
        val itemTouch =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    // this method is called
                    // when the item is moved.
                    return false
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                    )
                        .addBackgroundColor(
                            ContextCompat.getColor(
                                this@HomeActivity,
                                R.color.navy_blue
                            )
                        )
                        .addActionIcon(R.drawable.like_red_icon)
                        .setIconHorizontalMargin(30)
                        .addCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 8)
                        .addPadding(TypedValue.COMPLEX_UNIT_DIP, 10F, 5F, 10F)
                        .create()
                        .decorate()

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val list = adapter.currentList.toMutableList()
                    list.removeAt(position)
                    adapter.submitList(list)

                }

            })
        itemTouch.attachToRecyclerView(binding.rvPoke)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.frmContainer.id, fragment)
        transaction.commit()
    }
}
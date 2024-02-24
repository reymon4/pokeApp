package com.reymon.pokeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.reymon.pokeapp.databinding.FragmentSignUpBinding
import com.reymon.pokeapp.ui.activities.HomeActivity
import com.reymon.pokeapp.ui.viewmodels.MainViewModel


class SignUpFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.btnSignUp.setOnClickListener {
            createNewUsers(
                binding.txtUser.text.toString(),
                binding.txtPassword.text.toString()
            )
        }

        return binding.root

    }

    private fun createNewUsers(user: String, password: String) {
        mainViewModel.createNewUserWithEmailAndPassword(user, password)
        mainViewModel.user.observe(viewLifecycleOwner){
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }
        mainViewModel.error.observe(viewLifecycleOwner){
            Snackbar.make(
                binding.txtUser,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }


    }
}

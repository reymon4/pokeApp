package com.reymon.pokeapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.reymon.pokeapp.R
import com.reymon.pokeapp.databinding.FragmentLogInBinding
import com.reymon.pokeapp.databinding.FragmentSignUpBinding
import com.reymon.pokeapp.ui.viewmodels.LogAndSignSharedViewModel


class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: LogAndSignSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(LogAndSignSharedViewModel::class.java)
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.btnSignUp.setOnClickListener {
            createNewUsers(
                viewModel.auth,
                binding.txtUser.text.toString(),
                binding.txtPassword.text.toString()
            )
        }

        return binding.root

    }

    private fun createNewUsers(auth: FirebaseAuth, user: String, password: String) {
        Log.d("TAG", "user $user, pass $password")
        auth.createUserWithEmailAndPassword(
            user, password
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Snackbar.make(
                        binding.txtUser, "Created user!",
                        Snackbar.LENGTH_LONG
                    ).show()

                    binding.txtUser.text?.clear()
                    binding.txtPassword.text?.clear()

                } else {
                    Snackbar.make(
                        binding.txtUser,
                        task.exception!!.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.d("TAG", task.exception!!.stackTraceToString())
                }
            }
    }
}

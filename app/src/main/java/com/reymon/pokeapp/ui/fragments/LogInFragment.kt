package com.reymon.pokeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.reymon.pokeapp.databinding.FragmentLogInBinding
import com.reymon.pokeapp.ui.activities.MarvelActivity
import com.reymon.pokeapp.ui.viewmodels.LogAndSignSharedViewModel


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var viewModel:LogAndSignSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(LogAndSignSharedViewModel::class.java)
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        binding.btnLogIn.setOnClickListener{
            signInUsers(viewModel.auth,binding.txtUser.toString(),binding.txtPassword.toString())
        }

        return binding.root
    }

    private fun signInUsers(auth: FirebaseAuth,email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    //Con este user puedo hacer lo que desee: enviar a una api, db, etc
                    val user = auth.currentUser
                    startActivity(Intent(requireContext(), MarvelActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Snackbar.make(
                        binding.root,
                        "Account not found. Register first!",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}
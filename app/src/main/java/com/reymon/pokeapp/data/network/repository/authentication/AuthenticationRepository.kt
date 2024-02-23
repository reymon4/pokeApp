//package com.reymon.pokeapp.data.network.repository.authentication
//
//import android.content.Intent
//import android.util.Log
//import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//import com.reymon.pokeapp.ui.activities.HomeActivity
//
//class AuthenticationRepository{
//
//    private val auth= Firebase.auth
//    private fun createNewUsers(auth: FirebaseAuth, user: String, password: String) {
//        Log.d("TAG", "user $user, pass $password")
//        auth.createUserWithEmailAndPassword(
//            user, password
//        )
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("TAG", "createUserWithEmail:success")
//                    val user = auth.currentUser
//
//                } else {
//                    Snackbar.make(
//                        binding.txtUser,
//                        task.exception!!.message.toString(),
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                    Log.d("TAG", task.exception!!.stackTraceToString())
//                }
//            }
//    }
//
//    private fun signInUsers(auth: FirebaseAuth,email: String, password: String) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener() { task ->
//                if (task.isSuccessful) {
//                    //Con este user puedo hacer lo que desee: enviar a una api, db, etc
//                    val user = auth.currentUser
//                    startActivity(Intent(requireContext(), HomeActivity::class.java))
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("TAG", "signInWithEmail:failure", task.exception)
//                    Snackbar.make(
//                        binding.root,
//                        "Account not found. Register first!",
//                        Snackbar.LENGTH_SHORT,
//                    ).show()
//                }
//            }
//    }
//}
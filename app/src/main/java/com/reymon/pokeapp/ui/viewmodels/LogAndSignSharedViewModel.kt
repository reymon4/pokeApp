package com.reymon.pokeapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogAndSignSharedViewModel : ViewModel() {
    val auth = Firebase.auth


}
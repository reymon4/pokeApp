package com.reymon.pokeapp.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.reymon.pokeapp.data.network.entities.users.UserDB
import com.reymon.pokeapp.logic.network.usercases.GetUserByID
import com.reymon.pokeapp.logic.network.usercases.LogInUserWithEmailAndPassword
import com.reymon.pokeapp.logic.network.usercases.LogOutUser
import com.reymon.pokeapp.logic.network.usercases.SaveUserInDB
import com.reymon.pokeapp.logic.network.usercases.SignUpUserWithEmailAndPassword
import com.reymon.pokeapp.ui.activities.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val user get() = _user

    //Solo el viewModel actualizara los datos del user
    private val _user = MutableLiveData<UserDB>()

    val error get() = _error
    private val _error = MutableLiveData<String>()
    val resultCheckBiometric = MutableLiveData<Int>()
    fun checkBiometric(context: Context) {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_SUCCESS)

            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE)

            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE)

            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED)

            }
        }
    }

    fun signInUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val us = LogInUserWithEmailAndPassword().invoke(email, password)
            if (us != null) {
                _user.postValue(us!!)
            } else {
                _error.postValue("Account not found. Register first!")
            }

        }
    }

    fun createNewUserWithEmailAndPassword(user: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val us = SignUpUserWithEmailAndPassword().invoke(user, password)
            if (us != null) {
                //CAmbiar el name con otro editText
                val newUs =SaveUserInDB().invoke(us.id, us.email, "User")
                _user.postValue(newUs!!)
            } else {
                _error.postValue("Error. Try Again!")
            }

        }
    }

     fun logOut() {
         Log.d("VVM","invoke UserCAse:LogOutUser!")
            LogOutUser().invoke()

    }

}
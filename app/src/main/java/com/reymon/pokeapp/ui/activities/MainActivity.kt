package com.reymon.pokeapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View

import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.reymon.pokeapp.R
import com.reymon.pokeapp.databinding.ActivityMainBinding
import com.reymon.pokeapp.ui.fragments.LogInFragment
import com.reymon.pokeapp.ui.fragments.SignUpFragment
import com.reymon.pokeapp.ui.viewmodels.LogAndSignSharedViewModel
import com.reymon.pokeapp.ui.viewmodels.MainViewModel
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Permite ejecutar aplicaciones en segundo plano por hilos
    private lateinit var executor: Executor

    //Permite manejar los eventos del biometrico
    private lateinit var biometricPrompt: BiometricPrompt

    //Cuadro de dialogo en pantalla
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    //ViewModels
    private val mainViewModel: MainViewModel by viewModels()
    private val logAndSignSharedViewModel: LogAndSignSharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        initObservables()
        AuthenticationDialog()
        //mainViewModel.checkBiometric(this)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = logAndSignSharedViewModel.auth.currentUser
        if (currentUser != null) {
            binding.txtDontHaveAccount.visibility = View.GONE
            binding.txtSignUp.visibility = View.GONE
            binding.txtLogIn.visibility = View.GONE
            binding.txtLogIn.visibility = View.VISIBLE
            binding.imgHuella.visibility = View.VISIBLE

        } else {
            binding.txtLogIn.visibility = View.GONE
            binding.imgHuella.visibility = View.GONE

        }
    }

    //Crear una pantalla para el login y signup
    private fun initListener() {

        binding.imgHuella.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
        binding.txtSignUp.setOnClickListener {
            binding.txtDontHaveAccount.visibility = View.GONE
            binding.txtSignUp.visibility = View.GONE
            binding.btnLogIn.visibility = View.GONE
            replaceFragment(SignUpFragment())

        }
        binding.btnLogIn.setOnClickListener {
            binding.txtDontHaveAccount.visibility = View.GONE
            binding.txtSignUp.visibility = View.GONE
            binding.btnLogIn.visibility = View.GONE
            replaceFragment(LogInFragment())


        }


    }


    private fun AuthenticationDialog() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    startActivity(Intent(this@MainActivity, MarvelActivity::class.java))
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.e("FingerPrint", "Authentication failed!")
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e("FingerPrint", "Authentication error!")
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            //.setNegativeButtonText("Use account password")
            //DEfino si quiero usar la huella o la credencial del dispositivo para ingresar
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            //Con esta línea defino el texto para cancelar la operación porque solo puedo ingresar con huella
            //SOLO PODEMOS EJECUTAR CUANDO PERMITIMOS EL BIOMETRIC STRONG O CREDENTIAL
            .setNegativeButtonText("Cancel").build()


    }

    private fun initObservables() {
        mainViewModel.resultCheckBiometric.observe(this) { code ->
            when (code) {
                BiometricManager.BIOMETRIC_SUCCESS -> {

                    Snackbar.make(
                        this,
                        binding.txtSignUp,
                        "Log In successfully!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.")

                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Snackbar.make(
                        this,
                        binding.txtSignUp,
                        "Your device don't have fingerprint sensor!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.e("MY_APP_TAG", "No biometric features available on this device.")
                }

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Snackbar.make(
                        this,
                        binding.txtSignUp,
                        "Fingerprint sensor no available!",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Snackbar.make(this, binding.txtSignUp, "Error!", Snackbar.LENGTH_LONG).show()
                    // Prompts the user to create credentials that your app accepts.
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        )
                    }
                    startActivityForResult(enrollIntent, 100)
                }


            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.frmContainer.id, fragment)
        transaction.commit()
    }
}


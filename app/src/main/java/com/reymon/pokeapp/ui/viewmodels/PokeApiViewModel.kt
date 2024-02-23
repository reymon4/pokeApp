package com.reymon.pokeapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reymon.pokeapp.logic.entities.PokemonLG
import com.reymon.pokeapp.logic.usercases.poke.GetPokemonsKtor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeApiViewModel: ViewModel() {

    val listItem=MutableLiveData<List<PokemonLG>>()
    val error = MutableLiveData<String>()



    fun getPokemonKtorForId(){
        viewModelScope.launch(Dispatchers.IO){
            //Definir el número máximo de elementos a mostrar
            val response = GetPokemonsKtor().invokeList(20)
            if(response.isNotEmpty()){
                listItem.postValue(response)
            }else{
                error.postValue("Error al llamar al API!")
            }

        }

    }

}
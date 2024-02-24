package com.reymon.pokeapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reymon.pokeapp.logic.network.entities.PokemonLG
import com.reymon.pokeapp.logic.network.poke.GetPokemonsKtor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeApiViewModel: ViewModel() {

    val listItem=MutableLiveData<List<PokemonLG>>()
    val error = MutableLiveData<String>()
    val item=MutableLiveData<PokemonLG>()



    fun getPokemonListKtorForId(){
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
    fun getPokemonListKtorForPuzzle(){
        viewModelScope.launch(Dispatchers.IO){
            //Definir el número máximo de elementos a mostrar
            val response = GetPokemonsKtor().invokeList(5)
            if(response.isNotEmpty()){
                listItem.postValue(response)
            }else{
                error.postValue("Error al llamar al API!")
            }

        }

    }
    fun getPokemonForId(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            //Definir el número máximo de elementos a mostrar
            val response = GetPokemonsKtor().invokeForId(id)
            if(response!=null){
                item.postValue(response)
            }else{
                error.postValue("Error al llamar al API!")
            }

        }

    }
    fun getPokemonForName(id:String){
        viewModelScope.launch(Dispatchers.IO){
            //Definir el número máximo de elementos a mostrar
            val response = GetPokemonsKtor().invokeForName(id)
            if(response!=null){
                item.postValue(response)
            }else{
                error.postValue("Error al llamar al API!")
            }

        }

    }

}
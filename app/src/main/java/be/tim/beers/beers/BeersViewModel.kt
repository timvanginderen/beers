package be.tim.beers.beers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.tim.beers.data.Beer
import be.tim.beers.data.BeerRepository

class BeersViewModel(private val repository: BeerRepository) : ViewModel() {

    private val _beers = MutableLiveData<List<Beer>>().apply { value = emptyList() }
    val museums: LiveData<List<Beer>> = _beers

    fun loadBeers() {

    }
}
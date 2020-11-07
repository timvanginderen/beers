package be.tim.beers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.tim.beers.beers.BeersViewModel
import be.tim.beers.data.BeerRepository

class ViewModelFactory(private val repository: BeerRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BeersViewModel(repository) as T
    }
}
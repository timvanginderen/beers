package be.tim.beers.di

import androidx.lifecycle.ViewModelProvider
import be.tim.beers.ViewModelFactory
import be.tim.beers.data.BeerDataSource
import be.tim.beers.data.BeerRepository
import be.tim.beers.data.remote.ApiClient
import be.tim.beers.data.remote.BeerRemoteDataSource

object Injection {

    private val beerDataSource: BeerDataSource = BeerRemoteDataSource(ApiClient)
    private val beerRepository = BeerRepository(beerDataSource)
    private val beerViewModelFactory = ViewModelFactory(beerRepository)

    fun providerRepository(): BeerDataSource {
        return beerDataSource
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return beerViewModelFactory
    }
}
package be.tim.beers.data

interface BeerDataSource {
    fun retrieveBeers(callback: OperationCallback<Beer>)
    fun cancel()
}
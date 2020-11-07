package be.tim.beers.data

class BeerRepository(private val beerDataSource: BeerDataSource) {

    fun fetchBeers(callback: OperationCallback<Beer>) {
        beerDataSource.retrieveBeers(callback)
    }

    fun cancel() {
        beerDataSource.cancel()
    }
}
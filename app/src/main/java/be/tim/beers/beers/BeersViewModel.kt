package be.tim.beers.beers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.tim.beers.data.Beer
import be.tim.beers.data.BeerRepository
import be.tim.beers.data.OperationCallback

class BeersViewModel(private val repository: BeerRepository) : ViewModel() {

    private val TAG = BeersViewModel::class.qualifiedName

    private val _beers = MutableLiveData<List<Beer>>().apply { value = emptyList() }
    val beers: LiveData<List<Beer>> = _beers

    fun loadBeers() {
//        _isViewLoading.value = true
        repository.fetchBeers(object : OperationCallback<Beer> {
            override fun onError(error: String?) {
                Log.d(TAG, "loadBeers error: $error")
//                _isViewLoading.value = false
//                _onMessageError.value = error
            }

            override fun onSuccess(data: List<Beer>?) {
                Log.d(TAG, "loadBeers success")

//                _isViewLoading.value = false
                if (data.isNullOrEmpty()) {
//                    _isEmptyList.value = true

                } else {
                    _beers.value = data
                }
            }
        })
    }
}
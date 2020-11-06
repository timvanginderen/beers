package be.tim.beers.beerdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import be.tim.beers.BeersDetailFragmentArgs
import be.tim.beers.R

class BeerDetailFragment : Fragment() {

    private val TAG = BeerDetailFragment::class.qualifiedName

    val args: BeersDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.beerdetail_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated with beerId: ${args.beerId}")

    }

}
package be.tim.beers.beers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.tim.beers.R
import be.tim.beers.data.Beer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class BeersAdapter(private val beers: List<Beer>) : RecyclerView.Adapter<BeersAdapter.ViewHolder>() {

    private val TAG = BeersAdapter::class.qualifiedName

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val tvBeerName = itemView.findViewById<TextView>(R.id.tv_beer_name)
        val tvBreweryName = itemView.findViewById<TextView>(R.id.tv_brewery_name)
        var rbBeer = itemView.findViewById<RatingBar>(R.id.rb_beer)
        val ivBeer = itemView.findViewById<ImageView>(R.id.iv_beer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.beer_item, parent, false)

        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val beer: Beer = beers.get(position)

        viewHolder.tvBeerName.text = beer.name
        viewHolder.tvBreweryName.text = beer.brewery.name

        if (beer.rating != null) {
            viewHolder.rbBeer.visibility = View.VISIBLE
            viewHolder.rbBeer.rating = beer.rating!!.toFloat()
        } else {
            viewHolder.rbBeer.visibility = View.GONE
        }

        Picasso.get().load(beer.thumbImageUrl).into(viewHolder.ivBeer, object : Callback {
            override fun onSuccess() { }

            override fun onError(e: Exception?) {
                Log.d(TAG, "Picasso load error for ${beer.thumbImageUrl}")
                // TODO: 06/11/2020 load placeholder
            }
        })
    }

    override fun getItemCount(): Int {
        return beers.size
    }
}
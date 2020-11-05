package be.tim.beers.beers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.tim.beers.R
import be.tim.beers.data.Beer

class BeersAdapter(private val beers: List<Beer>) : RecyclerView.Adapter<BeersAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val tvBeerName = itemView.findViewById<TextView>(R.id.tv_beer_name)
        val tvBreweryName = itemView.findViewById<TextView>(R.id.tv_brewery_name)
        val rating = itemView.findViewById<RatingBar>(R.id.rating)
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

        viewHolder.tvBeerName.setText(beer.name)
        viewHolder.tvBreweryName.setText(beer.brewery.name)

        viewHolder.rating.visibility = if (beer.rating == null) View.VISIBLE else View.GONE
        if (beer.rating != null) viewHolder.rating.rating = beer.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return beers.size
    }
}
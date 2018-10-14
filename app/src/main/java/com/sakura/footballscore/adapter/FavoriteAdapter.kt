package com.sakura.footballscore.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.R
import com.sakura.footballscore.model.Favorite
import kotlinx.android.synthetic.main.item_list.view.*
import java.text.SimpleDateFormat

class FavoriteAdapter (private val context : Context, private  val events : List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(events[position],listener)
    }

    override fun getItemCount(): Int = events.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val tvDateMatch = view.tvDateMatch
        private val tvHomeScore = view.tvHomeScore
        private val tvAwayScore = view.tvAwayScore
        private val tvHomeName = view.tvHomeTeam
        private val tvAwayName = view.tvAwayTeam


        fun bindData(event : Favorite,listener: (Favorite) -> Unit) {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date = dateFormat.parse(event.strDate)
            dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy")
            tvDateMatch.text = dateFormat.format(date)
            tvHomeName.text = event.strHomeTeam
            tvHomeScore.text = event.strHomeScore ?: " "
            tvAwayName.text = event.strAwayTeam
            tvAwayScore.text = event.strAwayScore ?: " "

            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}
package com.sakura.footballscore.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.R
import kotlinx.android.synthetic.main.item_list.view.*

class HomeAdapter (private val context : Context, private  val events : List<EventsItem>)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(events[position])
    }

    override fun getItemCount(): Int = events.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val tvDateMatch = view.tvDateMatch
        private val tvHomeScore = view.tvHomeScore
        private val tvAwayScore = view.tvAwayScore
        private val tvHomeName = view.tvHomeTeam
        private val tvAwayName = view.tvAwayTeam


        fun bindData(event : EventsItem) {
            tvDateMatch.text = event.dateEvent ?: "-"
            tvHomeName.text = event.strHomeTeam
            tvHomeScore.text = event.intHomeScore ?: " "
            tvAwayName.text = event.strAwayTeam
            tvAwayScore.text = event.intAwayScore ?: " "
        }
    }
}
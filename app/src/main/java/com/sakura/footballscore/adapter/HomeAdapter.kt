package com.sakura.footballscore.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.R
import kotlinx.android.synthetic.main.item_list.view.*
import java.text.SimpleDateFormat

class HomeAdapter (private val context : Context, private  val events : List<EventsItem>,private val listener: (EventsItem) -> Unit)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){


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


        fun bindData(event : EventsItem,listener: (EventsItem) -> Unit) {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date = dateFormat.parse(event.dateEvent)
            dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy")
            tvDateMatch.text = dateFormat.format(date)
            tvHomeName.text = event.strHomeTeam
            tvHomeScore.text = event.intHomeScore ?: " "
            tvAwayName.text = event.strAwayTeam
            tvAwayScore.text = event.intAwayScore ?: " "

            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}
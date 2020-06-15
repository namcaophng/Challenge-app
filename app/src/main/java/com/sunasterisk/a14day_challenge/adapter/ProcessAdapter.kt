package com.sunasterisk.a14day_challenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.a14day_challenge.R
import kotlinx.android.synthetic.main.recycler_view_process.view.*

class ProcessAdapter(private val myDataSet: List<String>) :
    RecyclerView.Adapter<ProcessAdapter.ProcessViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_process, parent, false)

        return ProcessViewHolder(view)
    }

    override fun getItemCount() = myDataSet.size

    override fun onBindViewHolder(holder: ProcessViewHolder, position: Int) {
        holder.bindData(myDataSet[position])
        if (position == itemCount - 1) {
            holder.bindLastData()
        }
    }

    class ProcessViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(day: String) {
            view.textDay.text = day
        }

        fun bindLastData() {
            view.imageTick.setBackgroundResource(R.drawable.ic_check_box_grey)
        }
    }
}

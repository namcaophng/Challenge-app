package com.sunasterisk.a14day_challenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.data.model.DetailExercise
import kotlinx.android.synthetic.main.recycler_view_list_exercises.view.*

class DetailExercisesAdapter(
    private val exercises: List<DetailExercise>,
    private val onExerciseClick: (String) -> Unit
) : RecyclerView.Adapter<DetailExercisesAdapter.ListExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_list_exercises, parent, false)

        return ListExerciseViewHolder(view, onExerciseClick)
    }

    override fun getItemCount() = exercises.size

    override fun onBindViewHolder(holder: ListExerciseViewHolder, position: Int) {
        holder.bindData(exercises[position])
    }

    class ListExerciseViewHolder(
        val view: View,
        val onExerciseClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindData(detailExercise: DetailExercise) {
            view.imageTitle.setBackgroundResource(detailExercise.image)
            view.textTitle.text = detailExercise.title
            view.textSubTitle.text = detailExercise.subTitle

            val checkboxResource = if (detailExercise.isDone) {
                R.drawable.ic_check_box_pastel_green
            } else {
                R.drawable.ic_check_box_grey
            }

            view.imageCheckbox.setBackgroundResource(checkboxResource)
        }

        override fun onClick(v: View?) {
            onExerciseClick(view.textTitle.text.toString())
        }
    }
}

package com.example.testtask.feature_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.domain.Course

class CoursesAdapter(private val onFavoriteClick: (Course) -> Unit) :
    RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>() {

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(course: Course, onFavoriteClick: (Course) -> Unit) {
            itemView.findViewById<TextView>(R.id.tvTitle).text = course.title
            itemView.findViewById<TextView>(R.id.tvPrice).text = course.price
            itemView.findViewById<TextView>(R.id.tvSubtitle).text = course.description
            itemView.findViewById<TextView>(R.id.tvRating).text = course.rate
            itemView.findViewById<TextView>(R.id.tvDate).text = course.publishDate

            val btnFavorite = itemView.findViewById<ImageButton>(R.id.btnFavorite)

            if (course.isFavorite) {
                btnFavorite.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                btnFavorite.setImageResource(R.drawable.ic_favorite_border)
            }

            btnFavorite.setOnClickListener {
                onFavoriteClick(course)
            }

            val imageView = itemView.findViewById<ImageView>(R.id.imgCourse)
            if (course.id % 2 == 0) {
                imageView.setImageResource(R.drawable.test_background_card)
            } else {
                imageView.setImageResource(R.drawable.test_background_card2)
            }
        }
    }

    private val items = mutableListOf<Course>()

    fun submitList(list: List<Course>) {
        val diffCallback = CoursesDiffCallback(
            oldList = items,
            newList = list
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)

        return CourseViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(items[position], onFavoriteClick)
    }

    private class CoursesDiffCallback(
        private val oldList: List<Course>,
        private val newList: List<Course>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

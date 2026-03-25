package com.example.testtask.feature_favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.domain.Course

class FavoritesCoursesAdapter(
    private val onFavoriteClick: (Course) -> Unit
) : RecyclerView.Adapter<FavoritesCoursesAdapter.FavoriteCourseViewHolder>() {

    private val items = mutableListOf<Course>()

    class FavoriteCourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(course: Course, onFavoriteClick: (Course) -> Unit) {

            itemView.findViewById<TextView>(R.id.tvTitle).text = course.title
            itemView.findViewById<TextView>(R.id.tvPrice).text = course.price
            itemView.findViewById<TextView>(R.id.tvSubtitle).text = course.description
            itemView.findViewById<TextView>(R.id.tvRating).text = course.rate
            itemView.findViewById<TextView>(R.id.tvDate).text = course.publishDate

            val btnFavorite = itemView.findViewById<ImageButton>(R.id.btnFavorite)

            btnFavorite.setImageResource(R.drawable.ic_favorite_filled)

            btnFavorite.setOnClickListener {
                onFavoriteClick(course)
            }

            val imgCourse = itemView.findViewById<ImageView>(R.id.imgCourse)

            if (course.id % 2 == 0) {
                imgCourse.setImageResource(R.drawable.test_background_card)
            } else {
                imgCourse.setImageResource(R.drawable.test_background_card2)
            }
        }
    }

    fun submitList(list: List<Course>) {
        val diffCallback = FavoritesCoursesDiffCallback(
            oldList = items,
            newList = list
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)

        return FavoriteCourseViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavoriteCourseViewHolder, position: Int) {
        holder.bind(items[position], onFavoriteClick)
    }

    private class FavoritesCoursesDiffCallback(
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

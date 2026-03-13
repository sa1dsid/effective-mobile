import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.domain.Course
import com.example.testtask.feature_home.R

class CoursesAdapter : RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>() {

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(course: Course) {
            itemView.findViewById<TextView>(R.id.tvTitle).text = course.title
            itemView.findViewById<TextView>(R.id.tvPrice).text = course.price
            itemView.findViewById<TextView>(R.id.tvSubtitle).text = course.description
            itemView.findViewById<TextView>(R.id.tvRating).text = course.rate
            itemView.findViewById<TextView>(R.id.tvDate).text = course.publishDate

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
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)

        return CourseViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
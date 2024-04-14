import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.testtaskavito.R
import com.example.testtaskavito.domain.Review
import com.example.testtaskavito.presentation.secondScreen.ReviewViewHolder

class ReviewsAdapter : PagingDataAdapter<Review, ReviewViewHolder>(ReviewDiffItemCallback) {
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)

        holder.name.text = item?.author ?: "нет данных"
        holder.review.text = item?.review ?: "нет данных"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(
            R.layout.comments_item,
            parent,
            false
        )

        return ReviewViewHolder(view)
    }
}


//Одинаковые объекты
object ReviewDiffItemCallback : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Review,
        newItem: Review
    ): Boolean {
        return oldItem == newItem
    }
}


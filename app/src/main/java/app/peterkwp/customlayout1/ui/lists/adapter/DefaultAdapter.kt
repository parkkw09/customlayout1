package app.peterkwp.customlayout1.ui.lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.api.ModelDocuments
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_image.view.*

class DefaultAdapter(private val func: (ModelDocuments) -> Unit)
: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<ModelDocuments> = mutableListOf()

    fun addAllData(list: List<ModelDocuments>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        items.clear()
        notifyDataSetChanged()
    }

    fun size() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageHolder(parent)

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].let { documents ->

            with(holder.itemView) {
                Glide.with(context)
                    .load(documents.imageUrl)
                    .apply(RequestOptions().error(R.drawable.ic_launcher_foreground))
                    .into(ivItem)

                setOnClickListener { func.invoke(documents) }
                ivItemCount.text = position.toString()
            }
        }
    }

    class ImageHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    )
}
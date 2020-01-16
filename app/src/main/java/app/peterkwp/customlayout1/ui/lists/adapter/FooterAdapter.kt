package app.peterkwp.customlayout1.ui.lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.api.ModelDocuments
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_image.view.*

class FooterAdapter(private val func: (ModelDocuments) -> Unit)
: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<ModelDocuments> = mutableListOf()
    var noMoreFooter = false

    fun addAllData(list: List<ModelDocuments>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        items.clear()
        notifyDataSetChanged()
    }

    fun size() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            App.FOOTER -> ImageFooterHolder(parent)
            else ->  ImageHolder(parent)
        }
    }

    override fun getItemCount(): Int {
        val size = items.count()
        return if(!noMoreFooter && size != 0) size + 1 else size
    }

    override fun getItemViewType(position: Int): Int {
        val size = items.count()
        if (!noMoreFooter && size != 0) {
            return when (position) {
                size -> App.FOOTER
                else -> App.ITEM
            }
        }
        return App.ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            App.ITEM -> {
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
        }
    }

    class ImageHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    )

    class ImageFooterHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_image_footer, parent, false)
    )
}
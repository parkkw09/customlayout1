package app.peterkwp.customlayout1.ui.lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.api.ModelDocuments
import app.peterkwp.customlayout1.ui.parts.FooterHolder
import app.peterkwp.customlayout1.ui.parts.WebHolder

class NestedAdapter(private val func: (ModelDocuments) -> Unit)
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
            App.FOOTER -> {
                FooterHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false))
            }
            else ->  {
                WebHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_web, parent, false))
            }
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
                    if (holder is WebHolder) {
                        holder.run {
                            ivItem.loadUrl(documents.url)
                            itemView.setOnClickListener { func.invoke(documents) }
                            ivItemCount.text = position.toString()
                        }
                    }
                }
            }
        }
    }
}
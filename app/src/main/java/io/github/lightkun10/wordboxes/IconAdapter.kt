package io.github.lightkun10.wordboxes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_grid_icon.view.*

class IconAdapter : RecyclerView.Adapter<IconAdapter.GridViewHolder>() {

    private val mData = ArrayList<Icon>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<Icon>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_icon, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    // From class to viewHolder
    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(icon: Icon) {
            with(itemView) {
                Glide.with(itemView.context)
                     .load(icon.iconImg)
                     .into(img_item_photo)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(icon) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Icon)
    }
}
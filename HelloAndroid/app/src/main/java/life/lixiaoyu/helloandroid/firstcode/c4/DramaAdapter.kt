package life.lixiaoyu.helloandroid.firstcode.c4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import life.lixiaoyu.helloandroid.R

class DramaAdapter(dataList: List<Drama>): RecyclerView.Adapter<DramaAdapter.DramaViewHolder>() {

    private val dataList: MutableList<Drama> = mutableListOf()

    init {
        this.dataList.addAll(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DramaViewHolder {
        val view = when (viewType) {
            2 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_drama_second, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_drama_vertical, parent, false)
        }
        val holder = DramaViewHolder(view)
//        holder.itemView.setOnClickListener {
//            Toast.makeText(parent.context, "你点击了第${holder.bindingAdapterPosition}项", Toast.LENGTH_SHORT).show()
//        }
//        holder.cover.setOnClickListener {
//            Toast.makeText(parent.context, "你点击了第${holder.bindingAdapterPosition}项的封面", Toast.LENGTH_SHORT).show()
//        }
        return holder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 2 || position == 4) {
            2
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: DramaViewHolder, position: Int) {
        val drama = dataList[position]
//        if (drama.coverUrl.isNotEmpty()) {
//            Glide.with(holder.itemView.context)
//                .load(drama.coverUrl)
//                .into(holder.cover)
//        }
        holder.title.text = drama.title
        holder.desc.text = drama.desc
    }

    fun setNewData(newList: List<Drama>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun addItem(position: Int, drama: Drama) {
        if (position >= 0 && position < dataList.size) {
            dataList.add(position, drama)
            notifyItemInserted(position)
        }
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < dataList.size) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class DramaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.cover)
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)

        init {
            itemView.setOnClickListener {
                Toast.makeText(it.context, "你点击了第${bindingAdapterPosition}项", Toast.LENGTH_SHORT).show()
            }
            cover.setOnClickListener {
                Toast.makeText(it.context, "你点击了第${bindingAdapterPosition}项的封面", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class Drama(
    val coverUrl: String = "",
    val title: String = "",
    val desc: String = "",
)
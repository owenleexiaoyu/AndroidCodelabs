package life.lixiaoyu.jetpackpractice.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import life.lixiaoyu.jetpackpractice.R
import life.lixiaoyu.jetpackpractice.lifecycle.LifecycleDemoActivity
import life.lixiaoyu.jetpackpractice.lifecycle2.Lifecycle2DemoActivity

data class Item(
    val title: String,
    val onClick: ((view: View) -> Unit)? = null
)
private val dataList: List<Item> = listOf(
    Item("LifeCycle") {
        it.context.startActivity(Intent(it.context, LifecycleDemoActivity::class.java))
    },
    Item("LifeCycle2") {
        it.context.startActivity(Intent(it.context, Lifecycle2DemoActivity::class.java))
    },
    Item("LiveData") {
        it.context.startActivity(Intent(it.context, LifecycleDemoActivity::class.java))
    }
)

class MainAdapter: RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_main_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView

    private var data: Item? = null

    init {
        title = itemView.findViewById(R.id.cell_title)
        itemView.setOnClickListener {
            this.data?.onClick?.invoke(it)
        }
    }

    fun bind(data: Item) {
        this.data = data
        title.text = data.title
    }
}
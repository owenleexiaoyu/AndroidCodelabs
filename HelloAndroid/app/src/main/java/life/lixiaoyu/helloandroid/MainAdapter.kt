package life.lixiaoyu.helloandroid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import life.lixiaoyu.helloandroid.firstcode.c1.ASGeneratedActivity
import life.lixiaoyu.helloandroid.chapter12.ui.MaterialDesignActivity
import life.lixiaoyu.helloandroid.firstcode.c3.FirstActivity
import life.lixiaoyu.helloandroid.firstcode.c4.WidgetsActivity
import life.lixiaoyu.helloandroid.coroutines.CoroutinesActivity
import life.lixiaoyu.helloandroid.network.retrofit.RetrofitDemoActivity

data class Item(
    val title: String,
    val onClick: ((view: View) -> Unit)? = null
)
private val dataList: List<Item> = listOf(
    Item("Chapter1: 第一行代码") {
        it.context.startActivity(Intent(it.context, ASGeneratedActivity::class.java))
    },
    Item("Chapter3: Activity 介绍") {
        it.context.startActivity(Intent(it.context, FirstActivity::class.java))
    },
    Item("Chapter4: 基础控件与布局") {
        it.context.startActivity(Intent(it.context, WidgetsActivity::class.java))
    },
    Item("Chapter12: Material Design 组件") {
        it.context.startActivity(Intent(it.context, MaterialDesignActivity::class.java))
    },
    Item("Retrofit network request") {
        it.context.startActivity(Intent(it.context, RetrofitDemoActivity::class.java))
    },
    Item("Kotlin coroutines") {
        it.context.startActivity(Intent(it.context, CoroutinesActivity::class.java))
    },
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
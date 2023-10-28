package life.lixiaoyu.helloandroid.chapter12.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import life.lixiaoyu.helloandroid.chapter12.entity.Fruit
import com.bumptech.glide.Glide
import life.lixiaoyu.helloandroid.R
import life.lixiaoyu.helloandroid.chapter12.ui.FruitDetailActivity

class FruitAdapter(val context: Context, val fruitList: List<Fruit>):
    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fruitImg: ImageView = itemView.findViewById(R.id.fruitImg)
        val fruitText: TextView = itemView.findViewById(R.id.fruitText)
        init {
            itemView.setOnClickListener {
                val fruit = fruitList[adapterPosition]
                val intent = Intent(context, FruitDetailActivity::class.java).apply {
                    putExtra(FruitDetailActivity.FRUIT_NAME, fruit.name)
                    putExtra(FruitDetailActivity.FRUIT_IMG_ID, fruit.resId)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cell_fruit, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        Glide.with(context).load(fruit.resId).into(holder.fruitImg)
        holder.fruitText.text = fruit.name
    }

}
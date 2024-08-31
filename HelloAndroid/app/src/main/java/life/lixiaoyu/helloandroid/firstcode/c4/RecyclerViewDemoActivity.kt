package life.lixiaoyu.helloandroid.firstcode.c4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import life.lixiaoyu.helloandroid.R
import java.lang.StringBuilder
import kotlin.random.Random

class RecyclerViewDemoActivity: AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_demo)
        recyclerView = findViewById(R.id.recycler_view)
        val dramaList = buildDramaList()
        val dramaAdapter = DramaAdapter(dramaList)
        recyclerView.adapter = dramaAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//        recyclerView.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
//        recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
    }

    private fun buildDramaList(): List<Drama> {
        val dramaList = mutableListOf<Drama>()
        dramaList.add(Drama(title = "老友记", desc = buildDramaDesc(), coverUrl = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2186920269.webp"))
        dramaList.add(Drama(title = "闪电侠", desc = buildDramaDesc(), coverUrl = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2197587663.webp"))
        dramaList.add(Drama(title = "绿箭侠", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "超女", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "明日传奇", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "神盾局特工", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "怪奇物语", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "权利的游戏", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "夜班医生", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "行尸走肉", desc = buildDramaDesc()))
        dramaList.add(Drama(title = "夜魔侠", desc = buildDramaDesc()))
        return dramaList
    }

    private fun buildDramaDesc(): String {
//        val randomRepeat = Random.nextInt(6) + 1
        val randomRepeat = 1
        val random = Random.nextInt(10) + 1
        val stringBuilder = StringBuilder()
        repeat(randomRepeat) {
            stringBuilder.append("更新至${random}集")
        }
        return stringBuilder.toString()
    }
}
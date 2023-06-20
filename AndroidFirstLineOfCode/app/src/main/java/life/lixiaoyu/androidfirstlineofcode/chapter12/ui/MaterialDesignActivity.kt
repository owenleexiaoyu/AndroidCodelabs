package life.lixiaoyu.androidfirstlineofcode.chapter12.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import life.lixiaoyu.androidfirstlineofcode.chapter12.adapter.FruitAdapter
import life.lixiaoyu.androidfirstlineofcode.chapter12.entity.Fruit
import life.lixiaoyu.androidfirstlineofcode.chapter12.utils.makeToast
import life.lixiaoyu.androidfirstlineofcode.R
import life.lixiaoyu.androidfirstlineofcode.chapter12.utils.showSnackBar
import life.lixiaoyu.androidfirstlineofcode.databinding.ActivityMaterialDesignBinding
import kotlin.concurrent.thread

class MaterialDesignActivity : AppCompatActivity() {

    val fruits = mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango)
    )

    private lateinit var binding: ActivityMaterialDesignBinding

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialDesignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainLayout.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_home)
        }
        binding.navView.setNavigationItemSelectedListener {
            this.makeToast("Click Cell")
            binding.drawerLayout.closeDrawers()
            true
        }

        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        val adapter = FruitAdapter(this, fruitList)
        binding.mainLayout.recyclerView.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
        }

        binding.mainLayout.refreshLayout.let {
            it.setColorSchemeResources(R.color.colorPrimary)
            it.setOnRefreshListener {
                refreshFruits(adapter)
            }
        }
        binding.mainLayout.faBtn.setOnClickListener {
            it.showSnackBar("Click FAB", "Agree") {
                it.context.makeToast("Agreed")
            }
            binding.mainLayout.recyclerView.smoothScrollToPosition(30)
        }
    }

    /**
     * 下拉刷新
     */
    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                binding.mainLayout.refreshLayout.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.md_main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> makeToast("Click Search Menu")
            R.id.delete -> makeToast("Click Delete Menu")
            R.id.more -> makeToast("Click More Menu")
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    /**
     * 初始化数据源
     */
    private fun initFruits(){
        fruitList.clear()
        repeat(100) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

}
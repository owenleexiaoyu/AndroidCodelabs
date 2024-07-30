package life.lixiaoyu.helloandroid.network.volley

import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader

class BitmapCache : ImageLoader.ImageCache {

    private val cache: LruCache<String, Bitmap> = object: LruCache<String, Bitmap>(MAX_SIZE) {
        override fun sizeOf(key: String?, value: Bitmap?): Int {
            return if (value == null) 0 else value.rowBytes * value.height
        }
    }

    override fun getBitmap(url: String?): Bitmap? {
        if (url == null) return null
        return cache.get(url)
    }

    override fun putBitmap(url: String?, bitmap: Bitmap?) {
        if (url != null && bitmap != null) {
            cache.put(url, bitmap)
        }
    }

    companion object {
        const val MAX_SIZE = 10 * 1024 * 1024
    }
}
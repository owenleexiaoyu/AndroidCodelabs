package life.lixiaoyu.helloandroid.network.okhttp

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class PickImageContract: ActivityResultContract<Unit, Bitmap?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
        if (resultCode == RESULT_OK && intent?.data != null) {
            return getBitmapFromUri(intent.data!!)
        }
        return null
    }

    private fun getBitmapFromUri(data: Uri): Bitmap? {
        TODO("Not yet implemented")
    }
}
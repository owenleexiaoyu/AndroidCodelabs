package life.lixiaoyu.jetpackpractice.viewmodel2

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AdViewModel(application: Application): AndroidViewModel(application) {

    var millisInFuture: Long = 5000

}
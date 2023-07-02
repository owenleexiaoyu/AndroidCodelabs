package life.lixiaoyu.jetpackpractice.livedata2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AdViewModelWithLiveData(application: Application): AndroidViewModel(application) {

    var millisInFuture: Long = 5000

    private val timingResult: MutableLiveData<Int> = MutableLiveData()

    fun getTimingResult(): LiveData<Int> = timingResult

    fun setTimingResult(timingResult: Int) {
        this.timingResult.value = timingResult
    }

}
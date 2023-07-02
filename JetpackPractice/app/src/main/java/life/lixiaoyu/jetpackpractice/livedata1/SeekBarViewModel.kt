package life.lixiaoyu.jetpackpractice.livedata1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeekBarViewModel: ViewModel() {
    // 进度，0~100 之间的值
    val progress: MutableLiveData<Int> = MutableLiveData(0)
}
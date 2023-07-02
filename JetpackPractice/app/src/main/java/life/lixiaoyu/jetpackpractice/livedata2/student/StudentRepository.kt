package life.lixiaoyu.jetpackpractice.livedata2.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StudentRepository {

    fun getStudentScore(id: Int): LiveData<Int> {
        val scoreLiveData = MutableLiveData<Int>()
        if (id == 1) {
            scoreLiveData.value = 90
        } else {
            scoreLiveData.value = 80
        }
        return scoreLiveData
    }
}
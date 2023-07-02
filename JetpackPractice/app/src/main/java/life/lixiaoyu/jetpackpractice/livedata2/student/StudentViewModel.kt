package life.lixiaoyu.jetpackpractice.livedata2.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap

class StudentViewModel: ViewModel() {

    private val studentLiveData: MutableLiveData<Student> = MutableLiveData()

    init {
        studentLiveData.value = Student("Owen", 1, 98)
    }

    val scoreLiveData: LiveData<Int> = studentLiveData.map {
        it.score
    }

    // 要搜索学生分数的学生 id
    private val searchStudentId: MutableLiveData<Int> = MutableLiveData()

    fun setSearchStudentId(id: Int) {
        searchStudentId.value = id
    }

    val searchStudentScore: LiveData<Int> = searchStudentId.switchMap {
        StudentRepository().getStudentScore(it)
    }


}